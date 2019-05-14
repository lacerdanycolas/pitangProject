import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseService } from '../services/base/base.service';
import { Filter } from '../models/filter';

@Injectable()
export class SerieService extends BaseService {

  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  getSeriesUrl(index){
    if(index==1){
      return this._httpClient.get("http://localhost:8080/tvseries?size=6&page=0");
    }else{
     
      var indexPage = index - 1;
      var urlGet = "http://localhost:8080/tvseries?size=6&page="+indexPage;
     
      return this._httpClient.get(urlGet);
    }
   
  }

  getById(id: number) {
    return this._httpClient.get("http://localhost:8080/tvseries/"+id)
  }

  getGenres(){
    return this._httpClient.get("http://localhost:8080/genres?size=27");
  }

  getCastSerie(id:number){
    return this._httpClient.get("http://localhost:8080/tvseries/"+id+"/cast");
  }

  deleteSerie(id:number){
    return this._httpClient.delete("http://localhost:8080/tvseries/"+id);
  }

  updateSerie( id){
 
  }

  searchSerie(filter:Filter){
    
    var url = "http://localhost:8080/tvseries/filter/?";
    if(filter.nome && filter.ano && filter.lingua)
      url += "name=" + filter.nome +"&year=" + filter.ano.toString(10) + "&language=" + filter.lingua;
    else if(filter.nome && filter.lingua)
      url += "name=" + filter.nome + "&language=" + filter.lingua;
    else if(filter.nome && filter.ano)
      url += "name=" + filter.nome + "&year=" + filter.ano.toString(10);
    else if(filter.ano && filter.lingua)
      url += "year=" + filter.ano.toString(10) + "&language=" + filter.lingua;
    else if(filter.ano)
      url += "year=" + filter.ano.toString(10);
    else if(filter.nome)
      url += "name=" + filter.nome;  
    else if(filter.lingua)
      url += "language=" + filter.lingua;
    else 
       return this._httpClient.get("http://localhost:8080/tvseries/?size=6&page=0");

    return this._httpClient.get(url);  

  }
}