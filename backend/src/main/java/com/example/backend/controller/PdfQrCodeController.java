package com.example.backend.controller;

import com.example.backend.security.ValidationToken;
import com.example.backend.service.PdfQrCodeService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

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
    
    @Autowired
    private ValidationToken validationToken;

    @GetMapping("generer/{idCommande}")
    public ResponseEntity<ByteArrayResource> genererPdf(@PathVariable Long idCommande, @RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletResponse response) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);
        try {
            UUID uuidToken = UUID.fromString(token);
            if (!validationToken.verifierTokenDansBdd(uuidToken)) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
        }

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