package com.example.backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.backend.service.CommandesService;

import com.example.backend.model.Commandes;
import com.example.backend.model.CommandesOffres;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PdfQrCodeService {

    @Autowired
    private CommandesService commandesService;

    // Fonction qui génère le PDF contnenant le code QR
    public byte[] genererPdf(Long idCommande) throws Exception {
        // Récupérer la commande et les détails de l'utilisateur à partir de l'ID de la commande
        Optional<Commandes> commandeOpt = commandesService.recupererCommandeParIdCommande(idCommande);
        if (commandeOpt.isEmpty()) {
            throw new Exception("Commande non trouvée avec l'ID : " + idCommande);
        }

        Commandes commande = commandeOpt.get();
        String cleInscription = commande.getUtilisateur().getCleInscription();
        String nomUtilisateur = commande.getUtilisateur().getPrenom() + " " + commande.getUtilisateur().getNom();

        // Récupérer les détails des offres et la clé de commande depuis CommandesOffres
        List<CommandesOffres> commandesOffres = commande.getCommandesOffres();
        List<String> nomsOffres = commandesOffres.stream()
                .map(co -> co.getOffre().getNom())
                .collect(Collectors.toList());

        // Générer un QR code pour chaque offre
        List<byte[]> qrCodeImages = commandesOffres.stream()
            .map(co -> {
                try {
                    String cleCommande = co.getCleCommande();
                    String qrContent = cleInscription + cleCommande;
                    return genererCodeQr(qrContent, 250);
                } catch (Exception e) {
                    throw new RuntimeException("Erreur lors de la génération du QR code", e);
                }
            })
            .collect(Collectors.toList());

        return creerPdf(nomUtilisateur, nomsOffres, qrCodeImages);
    }

    // Fonction qui génère un code QR grâce à une API externe
    private byte[] genererCodeQr(String data, int size) throws Exception {
        String encodedData = URLEncoder.encode(data, StandardCharsets.UTF_8);
        String qrCodeUrl = String.format("https://api.qrserver.com/v1/create-qr-code/?data=%s&size=%dx%d", encodedData, size, size);

        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(qrCodeUrl)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    // Fonction créer un PDF grâce à PDFBox
    private byte[] creerPdf(String nomUtilisateur, List<String> nomsOffres, List<byte[]> qrCodeImages) throws Exception {
        try (PDDocument document = new PDDocument()) {

            // Créer une nouvelle page pour chaque offre et QR code
            for (int i = 0; i < nomsOffres.size(); i++) {
                String nomOffre = nomsOffres.get(i);
                byte[] qrCodeImage = qrCodeImages.get(i);

                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("Nom : " + nomUtilisateur);
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 720);
                contentStream.showText("Offre : " + nomOffre);
                contentStream.endText();

                PDImageXObject qrImage = PDImageXObject.createFromByteArray(document, qrCodeImage, "QR Code " + i);
                contentStream.drawImage(qrImage, 100, 400);

                contentStream.close();
            }

            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            document.save(pdfStream);
            return pdfStream.toByteArray();
        }
    }
}