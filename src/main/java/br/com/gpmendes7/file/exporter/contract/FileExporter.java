package br.com.gpmendes7.file.exporter.contract;

import br.com.gpmendes7.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;

}
