package br.com.gpmendes7.file.exporter.contract;

import br.com.gpmendes7.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PersonExporter {

    Resource exportPeople(List<PersonDTO> people) throws Exception;
    Resource exportPerson(PersonDTO person) throws Exception;

}
