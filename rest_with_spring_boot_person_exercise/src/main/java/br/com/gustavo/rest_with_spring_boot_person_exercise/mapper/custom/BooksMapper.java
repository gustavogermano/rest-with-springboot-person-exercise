package br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.custom;

import br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v1.BooksDTO;
import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Books;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BooksMapper {

    public Books convertDTOtoEntity(BooksDTO books){
        Books entity = new Books();

        entity.setId(books.getId());
        entity.setAuthor(books.getAuthor());
        entity.setLaunchDate(new Date());
        entity.setPrice(books.getPrice());
        entity.setTitle(books.getTitle());

        return entity;
    }
}
