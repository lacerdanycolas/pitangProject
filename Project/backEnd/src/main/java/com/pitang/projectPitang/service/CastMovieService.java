package com.pitang.projectPitang.service;

import com.pitang.projectPitang.models.Cast;
import com.pitang.projectPitang.models.CastTv;
import com.pitang.projectPitang.models.Movie;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.repository.CastRepository;
import com.pitang.projectPitang.repository.MovieRepository;
import com.pitang.projectPitang.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CastMovieService {

  @Autowired
  private CastRepository castRepository;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MovieRepository movieRepository;

  public Optional<Cast> getCast(Integer id){
    Optional<Cast> cast = this.castRepository.findById(id);
    return cast;
  }

  public void saveCast(Cast cast, Integer id, List<Person> listPerson){
    List<Person> listResult = new ArrayList<>();
    for(Person person: listPerson){
      List<Cast> listCast = person.getCast();

      if(listCast == null){
        listCast = new ArrayList<>();
      }

      listCast.add(cast);
      person.setCast(listCast);
      listResult.add(person);
    }
    this.personRepository.saveAll(listResult);
    Optional<Movie> movie = this.movieRepository.findById(id);
    movie.get().setCast(cast);
    this.movieRepository.save(movie.get());

  }

  public void deleteCast(Integer id){
     try{
       List<Person> listPerson = this.movieRepository.findById(id).get().getCast().getPersons();
       if(listPerson!= null){
         Optional<Cast> cast = this.castRepository.findById(id);
         List<Person> listResult = new ArrayList<>();
         for(Person person: listPerson){
           person.getCast().remove(cast.get());
           listResult.add(person);
         }
         this.personRepository.saveAll(listResult);
       }
     }catch(Exception e){

     }

  }

}
