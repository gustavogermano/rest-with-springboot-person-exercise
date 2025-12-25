package br.com.gustavo.rest_with_spring_boot_person_exercise.repository;

import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Long> {
}
