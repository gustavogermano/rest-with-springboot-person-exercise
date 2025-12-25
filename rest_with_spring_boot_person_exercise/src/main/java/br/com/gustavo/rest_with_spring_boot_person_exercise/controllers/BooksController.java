package br.com.gustavo.rest_with_spring_boot_person_exercise.controllers;

import br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v1.BooksDTO;
import br.com.gustavo.rest_with_spring_boot_person_exercise.services.BooksServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BooksController implements br.com.gustavo.rest_with_spring_boot_person_exercise.controllers.docs.BooksControllerDocs {

    @Autowired
    private BooksServices service;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public List<BooksDTO> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public BooksDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public BooksDTO create(@RequestBody BooksDTO books){
        return service.create(books);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public BooksDTO update(@RequestBody BooksDTO books){
        return service.update(books);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
