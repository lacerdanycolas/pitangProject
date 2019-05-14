import { Filter } from './../models/filter';
import { Movie } from './../models/movie';
import { MovieService } from './movie.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {

  constructor(private _movieService: MovieService,
    private _router: Router) { }

  movies : Movie[];
  totalPages:number;
  unique = 0;
  page = {
     index:-1
  }
  pages = [];
  
  filter:Filter = {} as Filter;

  


  ngOnInit() {
    
    this.getMovies(1);
  
  
  }
    
  
  goDetail(movie) {
    this._router.navigate(['filmes/detalhes', movie.id])
  }
  
  getMovies(index){
    this._movieService.getMoviesUrl(index).subscribe(
      response => {
      
        this.movies = response['content'];
        this.totalPages = response['totalPages'];
        this.buildPages(this.totalPages);
      }
    )
  }

  buildPages(total_pages){
    if(this.unique==0){
      for(let i=0; i<total_pages;i++){
        this.page.index = i+1;
        this.pages.push(this.page);
        this.page = {
          index:-1
        };
    }
  }
  this.unique = 1;
}

search(){
 this._movieService.searchMovie(this.filter).subscribe(
   response => {
     
     this.movies = response['content'];
     
   }
 )

}

  

}
