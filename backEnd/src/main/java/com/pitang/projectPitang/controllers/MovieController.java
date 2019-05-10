package com.pitang.projectPitang.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.error.ResourceNotFoundException;
import com.pitang.projectPitang.models.Cast;
import com.pitang.projectPitang.models.CastTv;
import com.pitang.projectPitang.models.Movie;
import com.pitang.projectPitang.models.dto.MovieEntityDTO;
import com.pitang.projectPitang.repository.MovieRepository;
import com.pitang.projectPitang.service.CastMovieService;
import com.pitang.projectPitang.service.CastTvService;
import com.pitang.projectPitang.utils.specifications.MovieSpecification;
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
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@CrossOrigin("http://localhost:4200")
public class MovieController {


  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private CastMovieService castMovieService;



  @GetMapping()
  public ResponseEntity<?> getAll(Pageable pageable){
    Page<Movie> pageMovie = this.movieRepository.findAll(pageable);
    return new ResponseEntity<>(pageMovie, HttpStatus.OK);
  }

  @GetMapping("/filter/")
  public ResponseEntity<?> getMovieFilter(Pageable pageable,
                                          @RequestParam(required = false) String title,
                                          @RequestParam(required=false) String year,
                                          @RequestParam(required = false) String language){

    Specification<Movie> specification = MovieSpecification.searchMovie(title, year, language);
    Page<Movie> pageMovie = this.movieRepository.findAll(Specification.where(specification),pageable);
    return new ResponseEntity<>(pageMovie, HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getMovieById(@PathVariable Integer id){
    return new ResponseEntity<>(this.movieRepository.findById(id).get(),HttpStatus.OK);
  }


  @PostMapping()
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> createMovie(@Valid @RequestBody Movie movie){
   Movie movieRep = this.movieRepository.findByTitleIgnoreCase(movie.getTitle());
    if(movieRep == null){
      this.movieRepository.save(movie);
      return new ResponseEntity<>(movie,HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/{id}")
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> updateMovie(@Valid @RequestBody MovieEntityDTO movieEntityDTO,
                                       @PathVariable Integer id ){
    try{
      this.movieRepository.findById(id).get();
    }catch (Exception e){
      throw new ResourceNotFoundException("Não existe um filme com esse ID:" + id.toString());
    }

    ObjectMapper objectMapper = new ObjectMapper();
    Movie movie = objectMapper.convertValue(movieEntityDTO, Movie.class);
    movie.setId(id);
    this.movieRepository.save(movie);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @Transactional(rollbackFor = Exception.class )
  public ResponseEntity<?> deleteMovie(@PathVariable Integer id){

    try{
      this.castMovieService.deleteCast(id);
      Movie movie = this.movieRepository.findById(id).get();
      this.movieRepository.delete(movie);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      throw new ResourceNotFoundException("Não existe um filme com esse ID:" + id.toString());
    }
  }

  @RequestMapping(value = "/{id}/cast",method = RequestMethod.GET)
  public ResponseEntity<?> getCastMovie(@PathVariable Integer id){
    Optional<Cast> castMovie = this.castMovieService.getCast(id);
    return new ResponseEntity<>(castMovie,HttpStatus.OK);
  }

  @RequestMapping(value="/{id}/cast", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createCastMovie(@Valid @RequestBody Cast cast, @PathVariable Integer id){
    this.castMovieService.saveCast(cast,id,cast.getPersons());
      return new ResponseEntity<>(HttpStatus.CREATED);
  }


}
