package com.example.Lab.People;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class PersonController {

    private final PersonRepository repository;


    PersonController(PersonRepository repository){
        this.repository = repository;
    }

    @GetMapping(path = "/people",produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Person>> all(){




        List<Resource<Person>> persons = repository.findAll().stream()
                .map(employee -> new Resource<>(employee,
                        linkTo(methodOn(PersonController.class).one(employee.getId())).withSelfRel(),
                        linkTo(methodOn(PersonController.class).all()).withRel("Persons")))
                .collect(Collectors.toList());

        return new Resources<>(persons,
                linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @PostMapping(path = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person newEmployee(@RequestBody Person newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping(path = "/people/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Person> one(@PathVariable Long id) {

        if (id == 777) {
            Person person = new Person(777L,"Lol","LoLov");

            return new Resource<>(person,
                    linkTo(methodOn(PersonController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(PersonController.class).all()).withRel("Persons"));

        };

        if (id == 111) {
            Person person = new Person(111L,"Kek","Kekov");

            return new Resource<>(person,
                    linkTo(methodOn(PersonController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(PersonController.class).all()).withRel("Persons"));

        };

        Person Person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));


        return new Resource<>(Person,
                linkTo(methodOn(PersonController.class).one(id)).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("Persons"));
    }

    @PutMapping("/people/{id}")
    public Person replacePerson(@RequestBody Person newPerson ,@PathVariable Long id){

        if (id == 777){
            Person person = new Person(777L, "TheName","TheLastName");
            return person;
        } else if (id == 111){
            Person person = new Person(111L, "TheName","TheLastName");
            return person;
        }

        return repository.findById(id)
                .map(Person -> {
                    Person.setName(newPerson.getName());
                    Person.setLastName(newPerson.getLastName());
                    return repository.save(Person);
                }).orElseGet(() -> {
                    newPerson.setId(id);
                    return repository.save(newPerson);
                });
    }

    @DeleteMapping("/people/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }



}
