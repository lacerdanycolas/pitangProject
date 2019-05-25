package com.pitang.projectPitang.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.error.ResourceNotFoundException;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.models.dto.PersonEntityDTO;
import com.pitang.projectPitang.repository.PersonRepository;
import com.pitang.projectPitang.utils.specifications.MovieSpecification;
import com.pitang.projectPitang.utils.specifications.PersonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
@CrossOrigin("http://localhost:4200")
public class PersonController {

  @Autowired
  private PersonRepository personRepository;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(Pageable pageable) {
    Page<Person> pagePerson = this.personRepository.findAll(pageable);
    return new ResponseEntity<>(pagePerson, HttpStatus.OK);
  }

  @GetMapping("/filter/")
  public ResponseEntity<?> getMovieFilter(Pageable pageable,
                                          @RequestParam(required = false) String name){

    Specification<Person> specification = PersonSpecification.searchPerson(name);

    Page<Person> pagePerson = this.personRepository.findAll(Specification.where(specification),pageable);
    return new ResponseEntity<>(pagePerson, HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPessoaById(@PathVariable Integer id){
    return new ResponseEntity<>(this.personRepository.findById(id).get(),HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> createPerson(@Valid @RequestBody PersonEntityDTO personEntityDTO){
    Person person = this.personRepository.save(personEntityDTO.transformaParaObjeto());
    return new ResponseEntity<>(person,HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> updatePerson(@Valid @RequestBody PersonEntityDTO personEntityDTO,@PathVariable Integer id){

      try{
        this.personRepository.findById(id).get();
      }catch (Exception e){
        throw new ResourceNotFoundException("Não existe uma pessoa com esse ID:" + id.toString());
      }


    ObjectMapper objectMapper = new ObjectMapper();
    Person person = objectMapper.convertValue(personEntityDTO, Person.class);
    person.setId(id);
    this.personRepository.save(person);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> deletePerson (@PathVariable Integer id){

    try{
      this.personRepository.delete(this.personRepository.findById(id).get());
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      throw new ResourceNotFoundException("Não existe uma pessoa com esse ID:" + id.toString());
    }


  }






}
