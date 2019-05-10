package com.pitang.projectPitang.controllers;

import com.pitang.projectPitang.models.Genre;
import com.pitang.projectPitang.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/genres")
@CrossOrigin("http://localhost:4200")
public class GenreController {

  @Autowired
  private GenreRepository genreRepository;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(Pageable pageable) {
    Page<Genre> genre = this.genreRepository.findAll(pageable);
    return new ResponseEntity<>(genre, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> createListGenre(@Valid @RequestBody List<Genre> genres){
    this.genreRepository.saveAll(genres);
    return new ResponseEntity<>(genres,HttpStatus.CREATED);
  }


}
