import { map } from 'rxjs/operators';
import { Movie } from './../../models/movie';
import { EditComponent } from './../../edit/edit.component';
import { MovieService } from '../movie.service';
import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Genre } from 'src/app/models/genre';
import {Person} from 'src/app/models/person';
import { Location } from '@angular/common';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {

  
  constructor(private _activatedRoute: ActivatedRoute,
    private _movieService: MovieService, private _router: Router,
    private modalService: NgbModal,
    private location: Location) { }
    
  movie:Movie;
  genreMovies:Genre[] = [];
  listGenres:Genre[];
  castPerson:Person[] = [];
  slides=[];
  closeResult: string;

  ngOnInit() {

    this._movieService.getGenres().subscribe(
      response => {
        this.listGenres = response['content'];
      }
    )  

    this._activatedRoute.params.subscribe(params => {
     
      let id = params['id'];
      this._movieService.getById(parseInt(id,10))
        .subscribe(response => {
          this.movie = response as Movie;
          this.buildGenresMovies(this.movie.genres_id);
          
        })

    });

    this._activatedRoute.params.subscribe(params =>{
      let id = params['id'];

      this._movieService.getCastMovie(parseInt(id,10))
          .subscribe(response => {
            this.castPerson = response['persons'];
            this.buildIndex(this.castPerson.length);
          })
    });


        
  }

  buildGenresMovies(list){
    
      this.listGenres.forEach(genreAll => {
        list.forEach(genreMovie => {
         
          let id = genreAll['id'];
          let idMovie = genreMovie;
        
          if(id == idMovie){
            this.genreMovies.push(genreAll); 
          }
        });
      
      });
    
  }

  buildIndex(tamList){
    for(let i=0; i < tamList;i++){
      this.slides.push(i);
    }
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }


  openEditar(movie){
    this._router.navigate(['detalhes/:id/editar', movie.id])
  }

  deleteMovie(){
    
    this._activatedRoute.params.subscribe(params =>{
      
      let id = params['id']; 
      
      this._movieService.deleteMovie(parseInt(id,10)).toPromise()
      .then(
        res => { // Success
          this.modalService.dismissAll();
          this.previousUrl();
        }
      );;

  });

}

  previousUrl(){
    this.location.back();
   }

}
