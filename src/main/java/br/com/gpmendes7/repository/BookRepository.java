package br.com.gpmendes7.repository;

import br.com.gpmendes7.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
