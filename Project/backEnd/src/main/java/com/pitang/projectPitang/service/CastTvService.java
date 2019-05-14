package com.pitang.projectPitang.service;


import com.pitang.projectPitang.models.CastTv;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.repository.CastRepository;
import com.pitang.projectPitang.repository.CastTvRepository;
import com.pitang.projectPitang.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CastTvService {

  @Autowired
  private CastTvRepository castTvRepository;

  @Autowired
  private PersonRepository personRepository;

  public Optional<CastTv> getCast(Integer id){
    Optional<CastTv> castTv = this.castTvRepository.findById(id);
    return castTv;
  }

  public void deleteCast(Integer id){
    try{
      List<Person> listPerson = this.castTvRepository.findById(id).get().getPersons();
      if(listPerson!= null){
        Optional<CastTv> cast = this.castTvRepository.findById(id);
        List<Person> listResult = new ArrayList<>();
        for(Person person: listPerson){
          person.getCastTv().remove(cast.get());
          listResult.add(person);
        }
        this.personRepository.saveAll(listResult);
      }
    }catch(Exception e){

    }

  }




}
