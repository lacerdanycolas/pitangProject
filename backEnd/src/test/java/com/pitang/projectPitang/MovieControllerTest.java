package com.pitang.projectPitang;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.controllers.MovieController;
import com.pitang.projectPitang.models.Cast;
import com.pitang.projectPitang.models.Movie;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.models.dto.MovieEntityDTO;
import com.pitang.projectPitang.models.dto.PersonEntityDTO;
import com.pitang.projectPitang.repository.MovieRepository;
import com.pitang.projectPitang.service.CastMovieService;
import com.pitang.projectPitang.utils.Gender;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectPitangApplication.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class MovieControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MovieController movieController;

  @MockBean
  private MovieRepository movieRepository;

  @MockBean
  private CastMovieService castMovieService;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(movieController)
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .setViewResolvers(new ViewResolver() {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
          return new MappingJackson2JsonView();
        }
      })
      .build();
  }

  public Movie returnMovie(){
    Movie movie = new Movie();
    movie.setBackdrop_path("/teste.jpg");
    movie.setGenres_id(Arrays.asList(1,2));
    movie.setRuntime(70);
    movie.setOriginal_language("pt");
    movie.setId(1);
    movie.setRelease_date("2019-05-25");
    movie.setTitle("Teste Movie");
    movie.setOverview("Sinopse testando o movie");

    return movie;
  }

  public Person returnPerson(){
    Person person = new Person();
    person.setName("Name Teste");
    person.setGender(Gender.MALE);
    person.setPlace_of_birth("Recife, Pernambuco, Brasil");
    person.setId(1);
    person.setProfile_path("/profileteste.jpg");
    return person;
  }

  public List<Person> returnListPerson(){
    Person person = new Person();
    person.setName("Name Teste");
    person.setGender(Gender.MALE);
    person.setPlace_of_birth("Recife, Pernambuco, Brasil");
    person.setId(1);
    person.setProfile_path("/profileteste.jpg");

    Person person2 = new Person();
    person2.setName("Name Teste Dois");
    person2.setGender(Gender.FEMALE);
    person2.setPlace_of_birth("Sao Paulo, Sao Paulo, Brasil");
    person2.setId(2);
    person2.setProfile_path("/profiletesteDois.jpg");

    List<Person> list = new ArrayList<>();
    list.add(person);
    list.add(person2);
    return list;
  }

  public Cast returnCast(){

    Cast cast = new Cast();
    cast.setPersons(this.returnListPerson());
    cast.setId(1);
    cast.setMovie(this.returnMovie());
    return cast;
  }

  @Test
  public void givenMovie_whenGetMovie_thenReturnJsonArray() throws Exception{

    Movie movie = this.returnMovie();
    Page<Movie> moviePage = new PageImpl<>(Arrays.asList(movie));

    when(this.movieRepository.findAll(ArgumentMatchers.isA(Pageable.class)))
      .thenReturn(moviePage);

    mvc.perform(get("/movies")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content", hasSize(1)))
      .andExpect(jsonPath("$.content[0].title").value(movie.getTitle()))
      .andExpect(jsonPath("$.content[0].id").value(movie.getId()))
      .andExpect(jsonPath("$.content[0].backdrop_path").value(movie.
        getBackdrop_path()))
      .andExpect(jsonPath("$.content[0].overview").value(movie.getOverview()))
      .andExpect(jsonPath("$.content[0].original_language").value(movie.
        getOriginal_language()))
      .andExpect(jsonPath("$.content[0].runtime").value(movie.getRuntime()))
      .andExpect(jsonPath("$.content[0].genres_id",hasSize(2)))
      .andExpect(jsonPath("$.content[0].genres_id",
        Matchers.containsInAnyOrder(1,2)));

  }

  @Test
  public void  givenOneMovie_whenGetAMovie_thenReturnsJsonArray() throws Exception{

    Movie movie = this.returnMovie();

    Optional<Movie> optionalMovie = Optional.of(movie);

    when(this.movieRepository.findById(anyInt())).thenReturn(optionalMovie);


    mvc.perform(get("/movies/"+movie.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.title").value(movie.getTitle()))
      .andExpect(jsonPath("$.id").value(movie.getId()))
      .andExpect(jsonPath("$.backdrop_path").value(movie.getBackdrop_path()))
      .andExpect(jsonPath("$.overview").value(movie.getOverview()))
      .andExpect(jsonPath("$.original_language").value(movie.getOriginal_language()))
      .andExpect(jsonPath("$.runtime").value(movie.getRuntime()))
      .andExpect(jsonPath("$.genres_id",hasSize(2)))
      .andExpect(jsonPath("$.genres_id", Matchers.containsInAnyOrder(1,2)));

  }

  @Test
  public void getMovieByFilter_whenPassTitleYearLanguage_thenReturnMovie() throws Exception{

    Movie movie = this.returnMovie();
    Page<Movie> moviePage = new PageImpl<>(Arrays.asList(movie));

    when(this.movieRepository.findAll(any(Specification.class),ArgumentMatchers.isA(Pageable.class))).thenReturn(moviePage);

    mvc.perform(get("/movies/filter/")
      .accept(MediaType.APPLICATION_JSON)
      .param("title",movie.getTitle())
      .param("year",movie.getRelease_date())
      .param("language", movie.getOriginal_language()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content", hasSize(1)))
      .andExpect(jsonPath("$.content[0].title").value(movie.getTitle()))
      .andExpect(jsonPath("$.content[0].id").value(movie.getId()))
      .andExpect(jsonPath("$.content[0].backdrop_path").value(movie.getBackdrop_path()))
      .andExpect(jsonPath("$.content[0].overview").value(movie.getOverview()))
      .andExpect(jsonPath("$.content[0].original_language").value(movie.getOriginal_language()))
      .andExpect(jsonPath("$.content[0].runtime").value(movie.getRuntime()))
      .andExpect(jsonPath("$.content[0].genres_id",hasSize(2)))
      .andExpect(jsonPath("$.content[0].genres_id", Matchers.containsInAnyOrder(1,2)));
  }

  @Test
  public void saveMovie_whenPassBody_thenReturnCreated() throws  Exception{

    Movie movie = this.returnMovie();
    ObjectMapper objectMapper = new ObjectMapper();
    MovieEntityDTO movieEntityDTO = objectMapper.convertValue(movie, MovieEntityDTO.class);
    String movieString = objectMapper.writeValueAsString(movieEntityDTO);

    when(this.movieRepository.save(any())).thenReturn(movie);

    mvc.perform(post("/movies")
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json")
      .content(movieString))

      .andDo(print())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.title").value(movie.getTitle()))
      .andExpect(jsonPath("$.backdrop_path").value(movie.getBackdrop_path()))
      .andExpect(jsonPath("$.overview").value(movie.getOverview()))
      .andExpect(jsonPath("$.original_language").value(movie.getOriginal_language()))
      .andExpect(jsonPath("$.runtime").value(movie.getRuntime()))
      .andExpect(jsonPath("$.genres_id",hasSize(2)))
      .andExpect(jsonPath("$.genres_id", Matchers.containsInAnyOrder(1,2)));

  }

  @Test
  public void updateMovie_whenPassUpdateObject_thenReturnOk() throws Exception{

    Movie movie = this.returnMovie();
    Optional<Movie> optionalMovie = Optional.of(movie);
    ObjectMapper objectMapper = new ObjectMapper();
    MovieEntityDTO movieEntityDTO = objectMapper.convertValue(movie, MovieEntityDTO.class);
    String movieString = objectMapper.writeValueAsString(movieEntityDTO);

    when(this.movieRepository.findById(anyInt())).thenReturn(optionalMovie);
    when(this.movieRepository.save(any())).thenReturn(movie);

    mvc.perform(put("/movies/"+movie.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json")
      .content(movieString))

      .andDo(print())

      .andExpect(status().isOk())
      .andReturn();

    verify(this.movieRepository, times(1)).save(any());

  }

  @Test
  public void deleteMovie_whenPassId_thenReturnOk()throws Exception{
    Movie movie = this.returnMovie();
    Optional<Movie> optionalMovie = Optional.of(movie);

    when(this.movieRepository.findById(anyInt())).thenReturn(optionalMovie);


    mvc.perform(delete("/movies/"+movie.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))

      .andDo(print())

      .andExpect(status().isOk())
      .andReturn();


    verify(this.movieRepository, times(1)).delete(any());
  }

  @Test
  public void getCast_whenPassIdCast_thenReturnJsonArray() throws Exception{

    Cast cast = this.returnCast();

    Optional<Cast> optionalCast = Optional.of(cast);

   List<Person> list = this.returnListPerson();


    when(this.castMovieService.getCast(anyInt())).thenReturn(optionalCast);


    mvc.perform(get("/movies/"+cast.getId()+"/cast")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.id").value(cast.getId()))
      .andExpect(jsonPath("$.persons",hasSize(2)))
      .andExpect(jsonPath("$.persons[0].id").value(list.get(0).getId()))
      .andExpect(jsonPath("$.persons[0].name").value(list.get(0).getName()))
      .andExpect(jsonPath("$.persons[0].profile_path").value(list.get(0).getProfile_path()))
      .andExpect(jsonPath("$.persons[0].place_of_birth").value(list.get(0).getPlace_of_birth()))
      .andExpect(jsonPath("$.persons[0].gender").value(list.get(0).getGender().toString()));

  }

  @Test
  public void saveCast_whenPassBody_thenReturnOk() throws Exception{
    Cast cast = this.returnCast();
    ObjectMapper objectMapper = new ObjectMapper();;
    String castString = objectMapper.writeValueAsString(cast);


    when(this.castMovieService.saveCast(any(),anyInt(),any())).thenReturn(cast);

    mvc.perform(post("/movies/"+cast.getId()+"/cast")
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json")
      .content(castString))

      .andDo(print())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.id").value(cast.getId()))
      .andExpect(jsonPath("$.persons",hasSize(2)))
      .andExpect(jsonPath("$.persons[0].id").value( cast.getPersons().get(0).getId()))
      .andExpect(jsonPath("$.persons[0].name").value(cast.getPersons().get(0).getName()))
      .andExpect(jsonPath("$.persons[0].profile_path").value(cast.getPersons().get(0).getProfile_path()))
      .andExpect(jsonPath("$.persons[0].place_of_birth").value(cast.getPersons().get(0).getPlace_of_birth()))
      .andExpect(jsonPath("$.persons[0].gender").value(cast.getPersons().get(0).getGender().toString()));


  }


}
