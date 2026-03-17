package br.com.gpmendes7.file.exporter.factory;

import br.com.gpmendes7.exception.BadRequestException;
import br.com.gpmendes7.file.exporter.MediaTypes;
import br.com.gpmendes7.file.exporter.contract.FileExporter;
import br.com.gpmendes7.file.exporter.impl.CsvExporter;
import br.com.gpmendes7.file.exporter.impl.XlsxExporter;
import br.com.gpmendes7.file.importer.contract.FileImporter;
import br.com.gpmendes7.file.importer.impl.CsvImporter;
import br.com.gpmendes7.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception {
        if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporter.class);
            // return new XlsxImporter();
        } else if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
            // return new CsvImporter();
        } else {
            throw new BadRequestException("Invalid File Format!");
        }
    }
}
