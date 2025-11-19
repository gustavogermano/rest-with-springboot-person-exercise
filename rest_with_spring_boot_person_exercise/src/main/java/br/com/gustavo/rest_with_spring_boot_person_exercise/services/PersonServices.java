package br.com.gustavo.rest_with_spring_boot_person_exercise.services;

import br.com.gustavo.rest_with_spring_boot_person_exercise.exception.ResourceNotFoundException;
import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Person;
import br.com.gustavo.rest_with_spring_boot_person_exercise.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;



    public List<Person> findAll(){
        logger.info("Finding All People");

        return repository.findAll();
    }

    public Person findById(Long id){
        logger.info("Finding one Person");

        return repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));
    }

    public Person create(Person person) {
        logger.info("Creating one Person");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person");

        Person entity = repository.findById(person.getId())
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id){
        logger.info("Deleting one Person");

        Person entity = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Not records found for this Id."));
        repository.delete(entity);
    }
}
