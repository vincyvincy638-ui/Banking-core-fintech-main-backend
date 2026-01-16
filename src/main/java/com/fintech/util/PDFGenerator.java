package com.fintech.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class PDFGenerator {

    public static byte[] generateMonthlyStatement(List<Map<String, String>> transactions) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // Title
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Monthly Statement", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        // Table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 3, 5, 3 });

        // Header
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Date", headFont));
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Description", headFont));
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Amount", headFont));
        table.addCell(hcell);

        // Data
        for (Map<String, String> txn : transactions) {
            table.addCell(txn.get("date"));
            table.addCell(txn.get("description"));
            table.addCell(txn.get("amount"));
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}
