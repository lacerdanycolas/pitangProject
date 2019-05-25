package com.pitang.projectPitang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.controllers.GenreController;
import com.pitang.projectPitang.models.Genre;
import com.pitang.projectPitang.repository.GenreRepository;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectPitangApplication.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class GenreControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private GenreController genreController;

  @MockBean
  private GenreRepository genreRepository;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(genreController)
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .setViewResolvers(new ViewResolver() {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
          return new MappingJackson2JsonView();
        }
      })
      .build();
  }

  public List<Genre> returnGenre(){
    Genre genre = new Genre();
    genre.setId(1);
    genre.setName("Ação");

    Genre genre1 = new Genre();
    genre1.setId(2);
    genre1.setName("Aventura");

    List<Genre> list = new ArrayList<>();
    list.add(genre);
    list.add(genre1);

    return list;

  }

  @Test
  public void givenGenres_whenGetGenres_thenReturnJsonArray() throws Exception{

    List<Genre> listGenres = this.returnGenre();
    Page<Genre> genrePage = new PageImpl<Genre>(listGenres);

    when(this.genreRepository.findAll(ArgumentMatchers.isA(Pageable.class)))
      .thenReturn(genrePage);

    mvc.perform(get("/genres")
      .accept(MediaType.APPLICATION_JSON))

      .andDo(print())
      .andExpect(status().isOk())

      .andExpect(jsonPath("$.content", hasSize(2)))
      .andExpect(jsonPath("$.content[0].id").value(listGenres.get(0).getId()))
      .andExpect(jsonPath("$.content[0].name").value(listGenres.get(0).getName()));

  }

  @Test
  public void saveAllGenres_whenPassBody_thenReturnCreated() throws Exception{
    List<Genre> genreList = this.returnGenre();
    ObjectMapper objectMapper = new ObjectMapper();
    String genresString = objectMapper.writeValueAsString(genreList);

    when(this.genreRepository.saveAll(any())).thenReturn(genreList);


    mvc.perform(post("/genres")
      .contentType(MediaType.APPLICATION_JSON)
      .accept("application/json")
      .content(genresString))

      .andDo(print())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$[0].id").value(genreList.get(0).getId()))
      .andExpect(jsonPath("$[0].name").value(genreList.get(0).getName()));

  }




}
