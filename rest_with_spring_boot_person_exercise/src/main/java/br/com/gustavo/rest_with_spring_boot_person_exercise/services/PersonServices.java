package br.com.gustavo.rest_with_spring_boot_person_exercise.services;

import br.com.gustavo.rest_with_spring_boot_person_exercise.controllers.PersonController;
import br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v1.PersonDTO;
import br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v2.PersonDTOV2;
import br.com.gustavo.rest_with_spring_boot_person_exercise.exception.RequiredObjectIsNullException;
import br.com.gustavo.rest_with_spring_boot_person_exercise.exception.ResourceNotFoundException;
import static br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.ObjectMapper.parseListObjects;
import static br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.ObjectMapper.parseObject;

import br.com.gustavo.rest_with_spring_boot_person_exercise.mapper.custom.PersonMapper;
import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Person;
import br.com.gustavo.rest_with_spring_boot_person_exercise.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;


@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;



    public List<PersonDTO> findAll(){
        logger.info("Finding All People");

        var persons =  parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);

        return persons;
    }

    public PersonDTO findById(Long id){
        logger.info("Finding one Person");

        var entity =  repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person");

        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {

        logger.info("Creating one Person V2");

        var entity = converter.convertDTOtoEntity(person);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person");

        Person entity = repository.findById(person.getId())
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Person");

        Person entity = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));
        repository.delete(entity);
    }

    private void addHateoasLinks( PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
