package br.com.gustavo.rest_with_spring_boot_person_exercise.repository;

import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
