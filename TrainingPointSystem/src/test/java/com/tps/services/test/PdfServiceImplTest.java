package com.tps.services.test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.tps.dto.TableData;
import com.tps.services.impl.PdfServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PdfServiceImplTest {

    @InjectMocks
    private PdfServiceImpl pdfService;

    @Test
    void createPdf() {
        TableData tableData = new TableData();
        tableData.setTitle("Test Title");
        tableData.setHeaders(Arrays.asList("Header1", "Header2", "Header3"));
        tableData.setRows(Arrays.asList(
                Arrays.asList("Cell1", "Cell2", "Cell3"),
                Arrays.asList("Cell4", "Cell5", "Cell6")
        ));

        // When
        ByteArrayOutputStream pdfOutputStream = null;
        try {
            pdfOutputStream = pdfService.createPdf(tableData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Then
        ByteArrayInputStream pdfInputStream = new ByteArrayInputStream(pdfOutputStream.toByteArray());
        PdfDocument pdfDocument = null;
        try {
            pdfDocument = new PdfDocument(new PdfReader(pdfInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Verify title
        String title = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(1)).trim();
        assertTrue(title.contains("Test Title"));

        // Verify headers
        String headers = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(1)).trim();
        assertTrue(headers.contains("Header1"));
        assertTrue(headers.contains("Header2"));
        assertTrue(headers.contains("Header3"));

        // Verify cell data
        String cellData = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(1)).trim();
        assertTrue(cellData.contains("Cell1"));
        assertTrue(cellData.contains("Cell2"));
        assertTrue(cellData.contains("Cell3"));
        assertTrue(cellData.contains("Cell4"));
        assertTrue(cellData.contains("Cell5"));
        assertTrue(cellData.contains("Cell6"));

        pdfDocument.close();
    }
}