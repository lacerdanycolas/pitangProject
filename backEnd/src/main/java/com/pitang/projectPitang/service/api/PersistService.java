package com.pitang.projectPitang.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.models.*;
import com.pitang.projectPitang.models.dtosAPI.*;
import com.pitang.projectPitang.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersistService {

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private CastRepository castRepository;

  @Autowired
  private CastTvRepository castTvRepository;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private TvSerieRepository tvSerieRepository;

  @Autowired
  private PersonRepository personRepository;


  public PersistService(){

  }


  public void persistMovieDTO(List<MovieDTO> list){

    Movie movie = new Movie();
    for (MovieDTO movieDT:list) {
      movie.setId(movieDT.getId());
      movie.setOriginal_language(movieDT.getOriginal_language());
      movie.setOverview(movieDT.getOverview());
      movie.setRelease_date(movieDT.getRelease_date());
      movie.setTitle(movieDT.getTitle());

    }

  }

    public void persistGenreDTO(List<GenreDTO> list){

    List<Genre> listResult = new ArrayList<>();

    for (GenreDTO genreDTO:list) {
      Genre genre = new Genre();
      genre.setName(genreDTO.getName());
      genre.setId(genreDTO.getId());
      listResult.add(genre);
    }

    this.genreRepository.saveAll(listResult);

  }

  public void persistCastAndPeopleAndMovie(List<PersonDTO> peoplelist,
                                   List<CastDTO> listCastDto, List<MovieEntityDTO> listMovieDto){

      List<Cast> listResult = new ArrayList<>();

      for(CastDTO castdto:listCastDto){
        Cast cast = new Cast();
        List<Person> personList = new ArrayList<>();
        for(PersonCastDTO personCastDTO: castdto.getCast()){
             for(PersonDTO personDTO: peoplelist){
               if(personDTO.getId().equals(personCastDTO.getId())){
                 Person person = new Person();
                 person.setPlace_of_birth(personDTO.getPlace_of_birth());
                 person.setId(personDTO.getId());
                 person.setGender(personDTO.getGender());
                 person.setName(personDTO.getName());
                 personList.add(person);
               }

             }
            }

        cast.setId(castdto.getId());
        cast.setPersons(personList);
        listResult.add(cast);
        }

        for(Cast cast:listResult){
            for(MovieEntityDTO movieEntityDTO:listMovieDto){
              if(movieEntityDTO.getId().equals(cast.getId())){
                  Movie movie = new Movie();
                  movie.setTitle(movieEntityDTO.getTitle());
                  movie.setRelease_date(movieEntityDTO.getRelease_date());
                  movie.setOverview(movieEntityDTO.getOverview());
                  movie.setOriginal_language(movieEntityDTO.getOriginal_language());
                  movie.setId(movieEntityDTO.getId());
                  movie.setRuntime(movieEntityDTO.getRuntime());
                  List<Integer> genres = new ArrayList<>();
                  for(GenreDTO genreDTO:movieEntityDTO.getGenres()){
                      genres.add(genreDTO.getId());
                  }
                  movie.setGenres_id(genres);
                  cast.setMovie(movie);
              }
            }
        }

      this.castRepository.saveAll(listResult);
    }

    public void persistCastTvAndPeopleAndSerie(List<CastTv> listCastTv){
      this.castTvRepository.saveAll(listCastTv);
    }

    public void updateMoviePoster(ObjectMapper mapper, RestTemplate restTemplate){

      List<Movie> listMoviesAll = this.movieRepository.findAll();
      List<Movie> listResult = new ArrayList<>();

      for(Movie movie: listMoviesAll){
        String jsonTv = restTemplate.getForObject("https://api.themoviedb.org/3/search/movie?" +
          "api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR&" +
          "query="+movie.getTitle()+"&page=1&include_adult=false",String.class);
        try {
          JsonNode movieDetails = mapper.readTree(jsonTv);
          for (JsonNode jsonNode1: movieDetails.get("results")) {
              if(jsonNode1.get("title").textValue().equalsIgnoreCase(movie.getTitle())){
                  movie.setBackdrop_path(jsonNode1.get("backdrop_path").textValue());
                  listResult.add(movie);
                  break;
              }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      this.movieRepository.saveAll(listResult);

    }

    public void updateTvSeries(ObjectMapper mapper, RestTemplate restTemplate){

     List<TvSerie> listSeriesAll = this.tvSerieRepository.findAll();
      List<TvSerie> listResult = new ArrayList<>();

      for(TvSerie tvSerie: listSeriesAll){
        String jsonTv = restTemplate.getForObject("https://api.themoviedb.org/3/search/tv?" +
          "api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR&" +
          "query="+tvSerie.getName()+"&page=1&include_adult=false",String.class);
        try {
          JsonNode tvSerieDetails = mapper.readTree(jsonTv);
          for (JsonNode jsonNode1: tvSerieDetails.get("results")) {
            if(jsonNode1.get("name").textValue().equalsIgnoreCase(tvSerie.getName())){
              tvSerie.setBackdrop_path(jsonNode1.get("backdrop_path").textValue());
              listResult.add(tvSerie);
              break;
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      this.tvSerieRepository.saveAll(listResult);

    }

    public void updatePersons(ObjectMapper mapper, RestTemplate restTemplate){

      List<Person> listPersonAll = this.personRepository.findAll();
      List<Person> listResult = new ArrayList<>();

      for(Person person: listPersonAll){
        String jsonTv = restTemplate.getForObject("https://api.themoviedb.org/3/search/person?" +
          "api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR&" +
          "query="+person.getName()+"&page=1&include_adult=false",String.class);

        try {
          Thread.sleep(100);
          JsonNode tvSerieDetails = mapper.readTree(jsonTv);
          for (JsonNode jsonNode1: tvSerieDetails.get("results")) {
            if(jsonNode1.get("name").textValue().equalsIgnoreCase(person.getName())){
             person.setProfile_path(jsonNode1.get("profile_path").textValue());
              listResult.add(person);
              break;
            }
          }
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }

      this.personRepository.saveAll(listResult);

    }



}
