package br.com.gpmendes7.file.importer.contract;

import br.com.gpmendes7.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;

}
