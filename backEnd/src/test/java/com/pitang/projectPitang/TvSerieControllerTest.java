package com.pitang.projectPitang;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.controllers.TvSerieController;
import com.pitang.projectPitang.models.CastTv;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.models.TvSerie;
import com.pitang.projectPitang.models.dto.TvSerieEntityDTO;
import com.pitang.projectPitang.repository.TvSerieRepository;
import com.pitang.projectPitang.service.CastTvService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectPitangApplication.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class TvSerieControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private TvSerieController tvSerieController;

  @MockBean
  private TvSerieRepository tvSerieRepository;

  @MockBean
  private CastTvService castTvService;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(tvSerieController)
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .setViewResolvers(new ViewResolver() {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
          return new MappingJackson2JsonView();
        }
      })
      .build();
  }

  public TvSerie returnTvSerie(){

    TvSerie tvSerie = new TvSerie();
    tvSerie.setId(1);
    tvSerie.setName("TvSerie Teste");
    tvSerie.setOverview("Overview Teste TvSerie");
    tvSerie.setFirst_air_date("2019-06-02");
    tvSerie.setEpisode_run_time(60);
    tvSerie.setBackdrop_path("/backdropteste.jpg");
    tvSerie.setOriginal_language("pt");
    tvSerie.setOriginal_countries(Arrays.asList("BR","CO","EU"));
    tvSerie.setGenres_id(Arrays.asList(3,4));

    return tvSerie;

  }

  public List<TvSerie> returnListTvSerie(){

    TvSerie tvSerie = new TvSerie();
    tvSerie.setId(1);
    tvSerie.setName("TvSerie Teste");
    tvSerie.setOverview("Overview Teste TvSerie");
    tvSerie.setFirst_air_date("2019-06-02");
    tvSerie.setEpisode_run_time(60);
    tvSerie.setBackdrop_path("/backdropteste.jpg");
    tvSerie.setOriginal_language("pt");
    tvSerie.setOriginal_countries(Arrays.asList("BR","CO","EU"));
    tvSerie.setGenres_id(Arrays.asList(3,4));

    TvSerie tvSerie2 = new TvSerie();
    tvSerie2.setId(2);
    tvSerie2.setName("TvSerie Teste 2");
    tvSerie2.setOverview("Testando overview 2");
    tvSerie2.setFirst_air_date("2018-12-25");
    tvSerie2.setEpisode_run_time(45);
    tvSerie2.setBackdrop_path("/backdropteste2.jpg");
    tvSerie2.setOriginal_language("eu");
    tvSerie2.setOriginal_countries(Arrays.asList("EU","CA"));
    tvSerie2.setGenres_id(Arrays.asList(2,4));

    List<TvSerie> list = new ArrayList<>();
    list.add(tvSerie);
    list.add(tvSerie2);

    return list;
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

  public CastTv returnCastTv(){

    CastTv castTv = new CastTv();
    castTv.setId(1);
    castTv.setPersons(this.returnListPerson());
    castTv.setTvSerie(this.returnTvSerie());
    return castTv;
  }

  @Test
  public void givenSeries_whenGetTvSeries_thenReturnJsonArray() throws Exception{

    List<TvSerie> listSeries = this.returnListTvSerie();
    Page<TvSerie> tvSeriePage = new PageImpl<TvSerie>(listSeries);

    when(this.tvSerieRepository.findAll(ArgumentMatchers.isA(Pageable.class)))
      .thenReturn(tvSeriePage);

    mvc.perform(get("/tvseries")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content", hasSize(2)))
      .andExpect(jsonPath("$.content[0].id").value(listSeries.get(0).getId()))
      .andExpect(jsonPath("$.content[0].name").value(listSeries.get(0).getName()))
      .andExpect(jsonPath("$.content[0].backdrop_path").value(listSeries.get(0).getBackdrop_path()))
      .andExpect(jsonPath("$.content[0].overview").value(listSeries.get(0).getOverview()))
      .andExpect(jsonPath("$.content[0].first_air_date").value(listSeries.get(0).getFirst_air_date()))
      .andExpect(jsonPath("$.content[0].episode_run_time").value(listSeries.get(0).getEpisode_run_time()))
      .andExpect(jsonPath("$.content[0].original_language").value(listSeries.get(0).getOriginal_language()))
      .andExpect(jsonPath("$.content[0].genres_id",
        Matchers.containsInAnyOrder(3,4)))
      .andExpect(jsonPath("$.content[0].original_countries",
        Matchers.containsInAnyOrder("BR","CO","EU")));

  }

  @Test
  public void givenOneTvSerie_whenGetATvSerie_returnJsonArray() throws Exception{

    TvSerie tvSerie = this.returnTvSerie();
    Optional<TvSerie> optionalTvSerie = Optional.of(tvSerie);

    when(this.tvSerieRepository.findById(anyInt())).thenReturn(optionalTvSerie);


    mvc.perform(get("/tvseries/"+tvSerie.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.id").value(tvSerie.getId()))
      .andExpect(jsonPath("$.name").value(tvSerie.getName()))
      .andExpect(jsonPath("$.backdrop_path").value(tvSerie.getBackdrop_path()))
      .andExpect(jsonPath("$.overview").value(tvSerie.getOverview()))
      .andExpect(jsonPath("$.first_air_date").value(tvSerie.getFirst_air_date()))
      .andExpect(jsonPath("$.episode_run_time").value(tvSerie.getEpisode_run_time()))
      .andExpect(jsonPath("$.original_language").value(tvSerie.getOriginal_language()))
      .andExpect(jsonPath("$.genres_id",
        Matchers.containsInAnyOrder(3,4)))
      .andExpect(jsonPath("$.original_countries",
        Matchers.containsInAnyOrder("BR","CO","EU")));


  }

  @Test
  public void getTvSeriesByFilter_whenPassNameYearLanguage_thenReturnTvSerie()throws Exception{

    List<TvSerie> listSeries = this.returnListTvSerie();
    Page<TvSerie> tvSeriePage = new PageImpl<TvSerie>(listSeries);

    when(this.tvSerieRepository.findAll(any(Specification.class),ArgumentMatchers.isA(Pageable.class))).thenReturn(tvSeriePage);


    mvc.perform(get("/tvseries/filter/")
      .accept(MediaType.APPLICATION_JSON)
      .param("name",listSeries.get(1).getName())
      .param("year",listSeries.get(1).getFirst_air_date())
      .param("language", listSeries.get(1).getOriginal_language()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content").exists())
      .andExpect(jsonPath("$.content[1].id").value(listSeries.get(1).getId()))
      .andExpect(jsonPath("$.content[1].name").value(listSeries.get(1).getName()))
      .andExpect(jsonPath("$.content[1].backdrop_path").value(listSeries.get(1).getBackdrop_path()))
      .andExpect(jsonPath("$.content[1].overview").value(listSeries.get(1).getOverview()))
      .andExpect(jsonPath("$.content[1].first_air_date").value(listSeries.get(1).getFirst_air_date()))
      .andExpect(jsonPath("$.content[1].episode_run_time").value(listSeries.get(1).getEpisode_run_time()))
      .andExpect(jsonPath("$.content[1].original_language").value(listSeries.get(1).getOriginal_language()))
      .andExpect(jsonPath("$.content[1].genres_id",
        Matchers.containsInAnyOrder(2,4)))
      .andExpect(jsonPath("$.content[1].original_countries",
        Matchers.containsInAnyOrder("EU","CA")));


  }

  @Test
  public void saveTvSerie_whenPassBody_thenReturnCreated() throws Exception{
    TvSerie tvSerie = this.returnTvSerie();
    ObjectMapper objectMapper = new ObjectMapper();
    TvSerieEntityDTO tvSerieEntityDTO = objectMapper.convertValue(tvSerie, TvSerieEntityDTO.class);
    String tvSerieString = objectMapper.writeValueAsString(tvSerieEntityDTO);

    when(this.tvSerieRepository.save(any())).thenReturn(tvSerie);

    mvc.perform(post("/tvseries")
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json")
      .content(tvSerieString))

      .andDo(print())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.name").value(tvSerie.getName()))
      .andExpect(jsonPath("$.backdrop_path").value(tvSerie.getBackdrop_path()))
      .andExpect(jsonPath("$.overview").value(tvSerie.getOverview()))
      .andExpect(jsonPath("$.first_air_date").value(tvSerie.getFirst_air_date()))
      .andExpect(jsonPath("$.episode_run_time").value(tvSerie.getEpisode_run_time()))
      .andExpect(jsonPath("$.original_language").value(tvSerie.getOriginal_language()))
      .andExpect(jsonPath("$.genres_id",
        Matchers.containsInAnyOrder(3,4)))
      .andExpect(jsonPath("$.original_countries",
        Matchers.containsInAnyOrder("BR","CO","EU")));
  }

  @Test
  public void updateTvSerie_whenPassBody_thenReturnCreated() throws Exception{

    TvSerie tvSerie = this.returnTvSerie();
    ObjectMapper objectMapper = new ObjectMapper();
    Optional<TvSerie> optionalTvSerie = Optional.of(tvSerie);
    TvSerieEntityDTO tvSerieEntityDTO = objectMapper.convertValue(tvSerie, TvSerieEntityDTO.class);
    String tvSerieString = objectMapper.writeValueAsString(tvSerieEntityDTO);

    when(this.tvSerieRepository.findById(anyInt())).thenReturn(optionalTvSerie);
    when(this.tvSerieRepository.save(any())).thenReturn(tvSerie);

    mvc.perform(put("/tvseries/"+tvSerie.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json")
      .content(tvSerieString))

      .andDo(print())
      .andExpect(status().isOk());

    verify(this.tvSerieRepository, times(1)).save(any());

  }

  @Test
  public void deleteTvSerie_whenPassId_thenReturnOk()throws Exception{
    TvSerie tvSerie = this.returnTvSerie();

    Optional<TvSerie> optionalTvSerie = Optional.of(tvSerie);

    when(this.tvSerieRepository.findById(anyInt())).thenReturn(optionalTvSerie);

    mvc.perform(delete("/tvseries/"+tvSerie.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json"))

      .andDo(print())
      .andExpect(status().isOk());

    verify(this.tvSerieRepository, times(1)).delete(any());
  }

  @Test
  public void getCast_whenPassIdCast_thenReturnJsonArray() throws Exception{
    CastTv castTv = this.returnCastTv();
    Optional<CastTv> optionalCastTv = Optional.of(castTv);

    List<Person> list = this.returnListPerson();

    when(this.castTvService.getCast(anyInt())).thenReturn(optionalCastTv);

    mvc.perform(get("/tvseries/"+castTv.getId()+"/cast")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.id").value(castTv.getId()))
      .andExpect(jsonPath("$.persons",hasSize(2)))
      .andExpect(jsonPath("$.persons[0].id").value(list.get(0).getId()))
      .andExpect(jsonPath("$.persons[0].name").value(list.get(0).getName()))
      .andExpect(jsonPath("$.persons[0].profile_path").value(list.get(0).getProfile_path()))
      .andExpect(jsonPath("$.persons[0].place_of_birth").value(list.get(0).getPlace_of_birth()))
      .andExpect(jsonPath("$.persons[0].gender").value(list.get(0).getGender().toString()));
  }

  @Test
  public void saveCast_whenPassBody_thenReturnOk() throws Exception{

    CastTv castTv = this.returnCastTv();
    ObjectMapper objectMapper = new ObjectMapper();
    String castString = objectMapper.writeValueAsString(castTv);

    when(this.castTvService.saveCastTv(any(),anyInt(),any())).thenReturn(castTv);

    mvc.perform(post("/tvseries/"+castTv.getId()+"/cast")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .content(castString))

      .andDo(print())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(status().isCreated())

      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.id").value(castTv.getId()))
      .andExpect(jsonPath("$.persons",hasSize(2)))
      .andExpect(jsonPath("$.persons[0].id").value(castTv.getPersons().get(0).getId()))
      .andExpect(jsonPath("$.persons[0].name").value(castTv.getPersons().get(0).getName()))
      .andExpect(jsonPath("$.persons[0].profile_path").value(castTv.getPersons().get(0).getProfile_path()))
      .andExpect(jsonPath("$.persons[0].place_of_birth").value(castTv.getPersons().get(0).getPlace_of_birth()))
      .andExpect(jsonPath("$.persons[0].gender").value(castTv.getPersons().get(0).getGender().toString()));

  }

}
