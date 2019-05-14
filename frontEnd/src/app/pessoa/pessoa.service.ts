import { BaseService } from './../services/base/base.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Filter } from '../models/filter';

@Injectable({
  providedIn: 'root'
})
export class PessoaService extends BaseService {

  constructor(httpClient: HttpClient) {
    super(httpClient);
   }

   getPersonIndex(page){
    if(page==1){
      return this._httpClient.get("http://localhost:8080/persons?size=60");
    }else{
     
      var urlGet = "http://localhost:8080/persons?size=60&page="+page;
     
      return this._httpClient.get(urlGet);
    }
   
  }

  getById(id: number) {
    return this._httpClient.get("http://localhost:8080/persons/"+id)
  }


  deletePerson(id:number){
    return this._httpClient.delete("http://localhost:8080/persons/"+id);
  }

  searchPerson(filter:Filter){
    
    var url = "http://localhost:8080/persons/filter/?";
    if(filter.nome)
    url += "name=" + filter.nome;
    else
      return this._httpClient.get("http://localhost:8080/persons?size=60");


    return this._httpClient.get(url);  
  }
 

}
