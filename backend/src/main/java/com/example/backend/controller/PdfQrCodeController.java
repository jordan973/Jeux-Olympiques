package com.example.backend.controller;

import com.example.backend.service.PdfQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "http://localhost:3000")
public class PdfQrCodeController {

    @Autowired
    private PdfQrCodeService pdfQrCodeService;

    @GetMapping("generer/{idCommande}")
    public ResponseEntity<ByteArrayResource> genererPdf(@PathVariable Long idCommande) {
        try {
            byte[] pdfContent = pdfQrCodeService.genererPdf(idCommande);

            ByteArrayResource ressource = new ByteArrayResource(pdfContent);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=billet.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(ressource);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}