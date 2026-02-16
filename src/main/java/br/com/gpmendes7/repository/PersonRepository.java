package br.com.gpmendes7.repository;

import br.com.gpmendes7.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
