package br.com.gustavo.rest_with_spring_boot_person_exercise.services;

import br.com.gustavo.rest_with_spring_boot_person_exercise.controllers.BooksController;
import br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v1.BooksDTO;
import br.com.gustavo.rest_with_spring_boot_person_exercise.exception.RequiredObjectIsNullException;
import br.com.gustavo.rest_with_spring_boot_person_exercise.exception.ResourceNotFoundException;
import br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.custom.BooksMapper;
import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Books;
import br.com.gustavo.rest_with_spring_boot_person_exercise.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.ObjectMapper.parseListObjects;
import static br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class BooksServices {

    private Logger logger = LoggerFactory.getLogger(BooksServices.class.getName());

    @Autowired
    BooksRepository repository;

    @Autowired
    BooksMapper converter;



    public List<BooksDTO> findAll(){
        logger.info("Finding All Books");

        var books =  parseListObjects(repository.findAll(), BooksDTO.class);
        books.forEach(this::addHateoasLinks);

        return books;
    }

    public BooksDTO findById(Long id){
        logger.info("Finding one Book");

        var entity =  repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));
        var dto = parseObject(entity, BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BooksDTO create(BooksDTO books) {

        if(books == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Book");

        var entity = parseObject(books, Books.class);

        var dto = parseObject(repository.save(entity), BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BooksDTO update(BooksDTO books) {

        if(books == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Book");

        Books entity = repository.findById(books.getId())
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));

        entity.setAuthor(books.getAuthor());
        entity.setLaunchDate(books.getLaunchDate());
        entity.setPrice(books.getPrice());
        entity.setTitle(books.getTitle());

        var dto = parseObject(repository.save(entity), BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Book");

        Books entity = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));
        repository.delete(entity);
    }

    private void addHateoasLinks( BooksDTO dto) {
        dto.add(linkTo(methodOn(BooksController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BooksController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BooksController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BooksController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BooksController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
