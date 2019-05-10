package com.pitang.projectPitang.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.models.CastTv;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.models.TvSerie;
import com.pitang.projectPitang.models.dtosAPI.*;
import com.pitang.projectPitang.service.api.PersistService;
import com.pitang.projectPitang.utils.apiObject.MovieData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
public class ExecuteTasks {

  @Autowired
  PersistService persistService;

  Map<String,Integer> credits_people = new HashMap<String, Integer>();


  @PostConstruct
  private void init() {

    /*
    RestTemplate restTemplate = new RestTemplate();
    String jsonMovie = restTemplate.getForObject(
      "https://api.themoviedb.org/3/discover/movie?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR", String.class);
    String jsonGenre = restTemplate.getForObject(
      "https://api.themoviedb.org/3/genre/movie/list?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR",String.class);



    MovieFactory movieFactory = new MovieFactory();
    ObjectMapper mapper = new ObjectMapper();
    MovieData movieData = new MovieData();
    GenreData genreData = new GenreData();
    PersonDTO personDTO = new PersonDTO();
    List<MovieEntityDTO> listMovieDetails = new ArrayList<>();
    List<PersonDTO> listPersonDto = new ArrayList<>();
    List<CastDTO> listCastDto = new ArrayList<>();
    try {
      movieData = mapper.readValue(jsonMovie, MovieData.class);
      listMovieDetails = detailsMovies(mapper,restTemplate,movieData);
      listCastDto = personCastDetails(mapper,restTemplate,movieData);
    //  genreData = mapper.readValue(jsonGenre,GenreData.class);
      listPersonDto = personList(mapper,restTemplate);
      this.persistService.persistCastAndPeopleAndMovie(listPersonDto,listCastDto,listMovieDetails);


    } catch (IOException e) {
      e.printStackTrace();
    }
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode tvData = discoverTv(restTemplate,mapper);
    List<JsonNode> listTvs = returnTvDetails(tvData,restTemplate,mapper);
    List<CastTv> castTvList  = castTvsList(listTvs,restTemplate,mapper);
    this.persistService.persistCastTvAndPeopleAndSerie(castTvList);*/
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();
    //this.persistService.updateMoviePoster(mapper,restTemplate);
   // this.persistService.updateTvSeries(mapper,restTemplate);
   // this.persistService.updatePersons(mapper,restTemplate);

  }

  public List<MovieEntityDTO> detailsMovies(ObjectMapper mapper, RestTemplate restTemplate, MovieData movieData){

    List<MovieEntityDTO> listResult = new ArrayList<>();

    for(MovieDTO mDTO: movieData.getResults()){
      String url = "https://api.themoviedb.org/3/movie/"+ mDTO.getId()+
        "?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR";
      String jsonMovieDetails = restTemplate.getForObject(url,String.class);
      MovieEntityDTO movieEntityDTO = new MovieEntityDTO();
      try {
        movieEntityDTO = mapper.readValue(jsonMovieDetails,MovieEntityDTO.class);
        listResult.add(movieEntityDTO);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return  listResult;

  }

  public List<CastDTO> personCastDetails(ObjectMapper mapper, RestTemplate restTemplate, MovieData movieData){

    List<CastDTO> listResult = new ArrayList<>();
    for(MovieDTO mDTO: movieData.getResults()){
      String url = "https://api.themoviedb.org/3/movie/"+ mDTO.getId()+ "/credits"+
        "?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR";
      String jsonCreditsDetais = restTemplate.getForObject(url,String.class);
      CastDTO castDTO = new CastDTO();
      try {
       castDTO = mapper.readValue(jsonCreditsDetais,CastDTO.class);
        for (PersonCastDTO person:castDTO.getCast()){
          credits_people.put(person.getName(),person.getId());
          if(credits_people.keySet().size() > 10)
            continue;
        }
       listResult.add(castDTO);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return  listResult;
  }

  public List<PersonDTO> personList(ObjectMapper mapper, RestTemplate restTemplate){

    List<PersonDTO> listResult = new ArrayList<>();
    Set<String> chaves = credits_people.keySet();
    int i = 0;
    for(String chave : chaves){
      if(i==300){
        int x = 0;
        break;
      }
      Integer idPeople = credits_people.get(chave);
      String url = "https://api.themoviedb.org/3/person/"+idPeople+
        "?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR";
      String jsonPerson = restTemplate.getForObject(url,String.class);
      PersonDTO personDTO = new PersonDTO();
      try {
        personDTO = mapper.readValue(jsonPerson,PersonDTO.class);
        listResult.add(personDTO);
        i++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return listResult;
  }

  public JsonNode discoverTv(RestTemplate restTemplate, ObjectMapper mapper){
    String jsonTv = restTemplate.getForObject("https://api.themoviedb.org/3/discover/tv?" +
      "api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR",String.class);
    try {
      JsonNode tvData = mapper.readTree(jsonTv);
      return tvData;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<JsonNode> returnTvDetails(JsonNode jsonNode, RestTemplate restTemplate, ObjectMapper mapper){

    List<JsonNode> listTvDetails = new ArrayList<>();
    for(JsonNode jsonNode1: jsonNode.get("results")){
      String jsonTv = restTemplate.getForObject("https://api.themoviedb.org/3/tv/"+jsonNode1.get("id").asInt()+
        "?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR",String.class);
      try {
        Thread.sleep(250);
        JsonNode tv = mapper.readTree(jsonTv);
        listTvDetails.add(tv);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return  listTvDetails;

  }

  public List<CastTv> castTvsList(List<JsonNode> listJsonNode, RestTemplate restTemplate, ObjectMapper mapper){
    List<CastTv> listCasttv = new ArrayList<>();
    listCasttv = returnListCastTvMounted(listJsonNode, restTemplate, mapper, listCasttv);
    return listCasttv;
  }

  public List<CastTv> returnListCastTvMounted(List<JsonNode> listJsonNode, RestTemplate restTemplate, ObjectMapper mapper,
                                              List<CastTv> listCasttv) {
    for(JsonNode jsonNode:listJsonNode){

      String castTvJson = restTemplate.getForObject("https://api.themoviedb.org/3/tv/"+jsonNode.get("id").asInt()+
        "/credits"+"?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR",String.class);
      try {
        List<Person> listPersons = new ArrayList<>();
        JsonNode castTvNode = mapper.readTree(castTvJson);
        CastTv castTv = new CastTv();
        castTv.setId(jsonNode.get("id").asInt());

        Thread.sleep(400);

        for(JsonNode nodePerson: castTvNode.get("cast")){

          String url = restTemplate.getForObject("https://api.themoviedb.org/3/person/"+nodePerson.get("id").asInt()+
            "?api_key=8c88c3d8f1562bc40e6de278d635bfaa&language=pt-BR",String.class);
          JsonNode personJson = mapper.readTree(url);
          Person person = new Person();
          person.setName(personJson.get("name").textValue());
          Gender gender = returnGender(personJson.get("gender").asInt());
          person.setGender(gender);
          person.setId(personJson.get("id").asInt());
          person.setPlace_of_birth(personJson.get("place_of_birth").textValue());
          listPersons.add(person);
        }
        castTv.setPersons(listPersons);

        Thread.sleep(400);

        TvSerie tvSerie = new TvSerie();
        tvSerie.setEpisode_run_time(jsonNode.get("episode_run_time").get(0).asInt());
        tvSerie.setFirst_air_date(jsonNode.get("first_air_date").textValue());
        tvSerie.setGenres_id(Arrays.asList(jsonNode.get("genres").asInt()));
        tvSerie.setId(castTv.getId());
        tvSerie.setName(jsonNode.get("name").textValue());
        tvSerie.setOriginal_countries(Arrays.asList(jsonNode.get("origin_country").textValue()));
        tvSerie.setOverview(jsonNode.get("overview").toString());
        tvSerie.setOriginal_language(jsonNode.get("original_language").textValue());

        castTv.setTvSerie(tvSerie);

        listCasttv.add(castTv);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return listCasttv;
  }



  public Gender returnGender(Integer codigo){
    if(codigo == 1)
      return Gender.FEMALE;
    else if(codigo == 2)
      return Gender.MALE;
    else
      return Gender.NOT_DEFINED;
  }

}

