package com.tps.controllers;

import com.tps.dto.TableData;
import com.tps.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
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
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("report.pdf").build());

        // Return PDF bytes as ResponseEntity
        return new ResponseEntity<>(pdfByteArray, headers, HttpStatus.CREATED);
    }
}