package com.pitang.projectPitang.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.projectPitang.error.ResourceNotFoundException;
import com.pitang.projectPitang.models.CastTv;
import com.pitang.projectPitang.models.TvSerie;
import com.pitang.projectPitang.models.dto.TvSerieEntityDTO;
import com.pitang.projectPitang.repository.TvSerieRepository;
import com.pitang.projectPitang.service.CastTvService;
import com.pitang.projectPitang.utils.specifications.TvSerieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/tvseries")
@CrossOrigin("http://localhost:4200")
public class TvSerieController {

  @Autowired
  private TvSerieRepository tvSerieRepository;

  @Autowired
  private CastTvService castTvService;

  @GetMapping
  public ResponseEntity<?> getAll(Pageable pageable){
    Page<TvSerie> pageTv = this.tvSerieRepository.findAll(pageable);
    return new ResponseEntity<>(pageTv, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getSerieById(@PathVariable Integer id){
    return new ResponseEntity<>(this.tvSerieRepository.findById(id).get(),HttpStatus.OK);
  }

  @GetMapping("/filter/")
  public ResponseEntity<?> getTvSerieFilter(Pageable pageable,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required=false) String year,
                                          @RequestParam(required = false) String language){

    Specification<TvSerie> specification = TvSerieSpecification.searchSerie(name, year, language);
    Page<TvSerie> pageSerie = this.tvSerieRepository.findAll(Specification.where(specification),pageable);
    return new ResponseEntity<>(pageSerie, HttpStatus.OK);

  }

  @PutMapping("/{id}")
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> updateMovie(@Valid @RequestBody TvSerieEntityDTO tvSerieEntityDTO,
                                       @PathVariable Integer id ){
    try{
      this.tvSerieRepository.findById(id).get();
    }catch (Exception e){
      throw new ResourceNotFoundException("Não existe uma Serie com esse ID:" + id.toString());
    }

    ObjectMapper objectMapper = new ObjectMapper();
    TvSerie tvSerie = objectMapper.convertValue(tvSerieEntityDTO, TvSerie.class);
    tvSerie.setId(id);
    this.tvSerieRepository.save(tvSerie);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}/cast",method = RequestMethod.GET)
  public ResponseEntity<?> getCastTv(@PathVariable Integer id){
    Optional<CastTv> castTv = this.castTvService.getCast(id);
    return new ResponseEntity<>(castTv,HttpStatus.OK);
  }

  @PostMapping()
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> createTvSerie(@Valid @RequestBody TvSerie tvSerie){
    TvSerie tvSerieRep = this.tvSerieRepository.findByNameIgnoreCase(tvSerie.getName());
    if(tvSerieRep == null){
      this.tvSerieRepository.save(tvSerie);
      return new ResponseEntity<>(tvSerie,HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping("/{id}")
  @Transactional(rollbackFor = Exception.class )
  public ResponseEntity<?> deleteTvSerie(@PathVariable Integer id){

    try{
      this.castTvService.deleteCast(id);
      TvSerie tvSerie = this.tvSerieRepository.findById(id).get();
      this.tvSerieRepository.delete(tvSerie);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      throw new ResourceNotFoundException("Não existe uma Serie com esse ID:" + id.toString());
    }
  }

}
