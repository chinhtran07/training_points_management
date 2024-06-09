package com.tps.services;

import com.tps.dto.TableData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface PdfService {
    ByteArrayOutputStream createPdf(TableData tableData) throws IOException;
}
