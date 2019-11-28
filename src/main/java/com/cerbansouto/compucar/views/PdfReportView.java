package com.cerbansouto.compucar.views;

import com.cerbansouto.compucar.model.Service;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("pdfReportView")
public class PdfReportView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=serviceReport.pdf");
        List<Service> services = (List<Service>)model.get("services");

        Document doc = new Document();
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        PdfPTable table = new PdfPTable(6);

        // Add table header
        Stream.of("Code", "Date", "Service time", "Cost", "Events", "Diagnoses")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

        // Add data rows
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        services.forEach(s -> {
            table.addCell(s.getCode());
            table.addCell(format.format(s.getDate()));
            table.addCell(String.format("%d", s.getServiceTime()));
            table.addCell(String.format("$%.2f", s.getCost()));
            table.addCell(String.join(",",
                    s.getEvents().stream().map(e -> e.getName()).collect(Collectors.toList())));
            table.addCell(String.join(",",
                    s.getDiagnoses().stream().map(d -> d.getEventName()).collect(Collectors.toList())));
        });

        doc.add(table);
        doc.close();
    }
}
