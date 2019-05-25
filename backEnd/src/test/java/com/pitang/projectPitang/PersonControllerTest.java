package com.pitang.projectPitang;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.controllers.PersonController;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.models.dto.PersonEntityDTO;
import com.pitang.projectPitang.repository.PersonRepository;
import com.pitang.projectPitang.utils.Gender;
import com.pitang.projectPitang.utils.specifications.PersonSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectPitangApplication.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class PersonControllerTest {


  @Autowired
  private MockMvc mvc;


  @MockBean
  private PersonRepository personRepository;

  @Autowired
  private PersonController personController;




  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(personController)
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .setViewResolvers(new ViewResolver() {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
          return new MappingJackson2JsonView();
        }
      })
      .build();

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

  @Test
  public void givenPersons_whenGetPersons_thenReturnJsonArray() throws Exception{

    //Person person = this.returnPerson();
    List<Person> list = this.returnListPerson();
    Page<Person> personPage = new PageImpl<Person>(list);

    when(personRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(personPage);

        mvc.perform(get("/persons")
                                  .accept(MediaType.APPLICATION_JSON))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andExpect(jsonPath("$.content", hasSize(2)))
                                  .andExpect(jsonPath("$.content[0].name").value(list.get(0).getName()))
                                  .andExpect(jsonPath("$.content[0].id").value(list.get(0).getId()))
                                  .andExpect(jsonPath("$.content[0].profile_path").value(list.get(0).getProfile_path()))
                                  .andExpect(jsonPath("$.content[0].place_of_birth").value(list.get(0).getPlace_of_birth()))
                                  .andExpect(jsonPath("$.content[0].gender").value(list.get(0).getGender().toString()));

      }

      @Test
      public void givenOnePerson_whenGetAPerson_thenReturnsJsonArray() throws Exception{

        Person person = this.returnPerson();


        Optional<Person> optionalPerson = Optional.of(person);

        when(personRepository.findById(anyInt())).thenReturn(optionalPerson);

        mvc.perform(get("/persons/"+person.getId())
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").exists())
          .andExpect(jsonPath("$.name").value(person.getName()))
          .andExpect(jsonPath("$.id").value(person.getId()))
          .andExpect(jsonPath("$.profile_path").value(person.getProfile_path()))
          .andExpect(jsonPath("$.place_of_birth").value(person.getPlace_of_birth()))
          .andExpect(jsonPath("$.gender").value(person.getGender().toString()));

      }

      @Test
      public void getPersonByFilter_whenPassName_thenReturnPerson() throws Exception{

        Person person = this.returnPerson();
        Page<Person> personPage = new PageImpl<Person>(Arrays.asList(person));


        when(this.personRepository.findAll(any(Specification.class),ArgumentMatchers.isA(Pageable.class))).thenReturn(personPage);


        mvc.perform(get("/persons/filter/")
          .param("name",person.getName())
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content").exists())
          .andExpect(jsonPath("$.content[0].name").value(person.getName()))
          .andExpect(jsonPath("$.content[0].id").value(person.getId()))
          .andExpect(jsonPath("$.content[0].profile_path").value(person.getProfile_path()))
          .andExpect(jsonPath("$.content[0].place_of_birth").value(person.getPlace_of_birth()))
          .andExpect(jsonPath("$.content[0].gender").value(person.getGender().toString()));

      }

      @Test
      public void savePerson_whenPassBody_thenReturnCreated() throws  Exception{

        Person person = this.returnPerson();
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntityDTO personEntityDTO = objectMapper.convertValue(person, PersonEntityDTO.class);
        String personString = objectMapper.writeValueAsString(personEntityDTO);

        when(this.personRepository.save(any())).thenReturn(person);

        mvc.perform(post("/persons")
          .contentType(MediaType.APPLICATION_JSON)
          .accept("application/json")
          .content(personString))

          .andDo(print())

          .andExpect(content().contentType("application/json;charset=UTF-8"))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$").exists())
          .andExpect(jsonPath("$.name").value(person.getName()))
          .andExpect(jsonPath("$.profile_path").value(person.getProfile_path()))
          .andExpect(jsonPath("$.place_of_birth").value(person.getPlace_of_birth()))
          .andExpect(jsonPath("$.gender").value(person.getGender().toString()))
          .andReturn();


      }

      @Test
      public void updatePerson_whenPassUpdateObject_thenReturnOk() throws Exception{
        Person person = this.returnPerson();
        Optional<Person> optionalPerson = Optional.of(person);
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntityDTO personEntityDTO = objectMapper.convertValue(person, PersonEntityDTO.class);
        String personString = objectMapper.writeValueAsString(personEntityDTO);

        when(this.personRepository.findById(anyInt())).thenReturn(optionalPerson);
        when(this.personRepository.save(any())).thenReturn(person);

        mvc.perform(put("/persons/"+person.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .accept("application/json")
          .content(personString))

          .andDo(print())

          .andExpect(status().isOk())
          .andReturn();
      }

      @Test
      public void deletePerson_whenPassId_thenReturnOk() throws Exception{

        Person person = this.returnPerson();
        Optional<Person> optionalPerson = Optional.of(person);

        when(this.personRepository.findById(anyInt())).thenReturn(optionalPerson);

        mvc.perform(delete("/persons/"+person.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))

          .andDo(print())

          .andExpect(status().isOk())
          .andReturn();


        verify(this.personRepository, times(1)).delete(any());

      }


}
