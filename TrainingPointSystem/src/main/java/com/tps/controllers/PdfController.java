package com.tps.controllers;

import com.tps.dto.TableData;
import com.tps.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@CrossOrigin
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generatePdf")
    public ResponseEntity<byte[]> createPdf(@RequestBody TableData tableData) throws IOException {
        ByteArrayOutputStream pdfBytes = pdfService.createPdf(tableData);

        // Convert ByteArrayOutputStream to byte array
        byte[] pdfByteArray = pdfBytes.toByteArray();

        // Set headers for PDF response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return PDF bytes as ResponseEntity
        return ResponseEntity.ok().headers(headers).body(pdfByteArray);
    }
}
