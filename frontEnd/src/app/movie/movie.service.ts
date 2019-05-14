import { Movie } from './../models/movie';
import { Filter } from './../models/filter';
import { BaseService } from '../services/base/base.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { MovieDTO } from '../models/movieDTO';

@Injectable()
export class MovieService extends BaseService {

  constructor(httpClient: HttpClient) {
    super(httpClient);
  }
  

  getMoviesUrl(index){
    if(index==1){
      return this._httpClient.get(`${this.URL_MOVIE}`);
    }else{
     
      var indexPage = index - 1;
      var urlGet = "http://localhost:8080/movies?size=6&page="+indexPage;
     
      return this._httpClient.get(urlGet);
    }
   
  }

  
  getById(id: number) {
    return this._httpClient.get("http://localhost:8080/movies/"+id)
  }

  getGenres(){
    return this._httpClient.get("http://localhost:8080/genres?size=27");
  }

  getCastMovie(id:number){
    return this._httpClient.get("http://localhost:8080/movies/"+id+"/cast");
  }

  deleteMovie(id:number){
    return this._httpClient.delete("http://localhost:8080/movies/"+id);
  }

  updateMovie(movie:MovieDTO, id){
    
    return this._httpClient.put<MovieDTO>("http://localhost:8080/movies/"+id, movie);
  }

  searchMovie(filter:Filter){
    
    var url = "http://localhost:8080/movies/filter/?";
    if(filter.nome && filter.ano && filter.lingua)
      url += "title=" + filter.nome +"&year=" + filter.ano.toString(10) + "&language=" + filter.lingua;
    else if(filter.nome && filter.lingua)
      url += "title=" + filter.nome + "&language=" + filter.lingua;
    else if(filter.nome && filter.ano)
      url += "title=" + filter.nome + "&year=" + filter.ano.toString(10);
    else if(filter.ano && filter.lingua)
      url += "year=" + filter.ano.toString(10) + "&language=" + filter.lingua;
    else if(filter.ano)
      url += "year=" + filter.ano.toString(10);
    else if(filter.nome)
      url += "title=" + filter.nome;  
    else if(filter.lingua)
      url += "language=" + filter.lingua;
    else 
       return this._httpClient.get("http://localhost:8080/movies?size=6&page=0");

    return this._httpClient.get(url);  

  }
}
