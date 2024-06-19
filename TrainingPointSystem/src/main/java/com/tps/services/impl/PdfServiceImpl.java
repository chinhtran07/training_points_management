package com.tps.services.impl;

import com.google.common.eventbus.DeadEvent;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.tps.dto.TableData;
import com.tps.services.PdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class PdfServiceImpl implements PdfService {
    @Override
    public ByteArrayOutputStream createPdf(TableData tableData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(outputStream);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        String fontPath = Objects.requireNonNull(getClass().getClassLoader().getResource("fonts/ArialUnicodeMSRegular/ArialUnicodeMSRegular.ttf")).getPath();
        PdfFont font = PdfFontFactory.createFont(fontPath, "Identity-H");

        document.add(new Paragraph(tableData.getTitle())
                .setFont(font)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20));

        Table table = new Table(UnitValue.createPercentArray(tableData.getHeaders().size())).useAllAvailableWidth();
        Color textHeaderColor = new DeviceGray(0.5f);


        for (String header : tableData.getHeaders()) {
            table.addHeaderCell(new Cell().add(new Paragraph(header))
                    .setFont(font)
                    .setFontColor(textHeaderColor));
        }

        for (List<String> row : tableData.getRows()) {
            for (String cell : row) {
                table.addCell(new Cell().add(new Paragraph(cell)).setFont(font));
            }
        }

        document.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));

        document.close();
        return outputStream;

    }
}
