import { PageEvent } from '@angular/material/paginator';
import { Person } from 'src/app/models/person';
import { PessoaService } from './pessoa.service';
import { Component, OnInit } from '@angular/core';
import { Filter } from '../models/filter';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pessoa',
  templateUrl: './pessoa.component.html',
  styleUrls: ['./pessoa.component.css']
})
export class PessoaComponent implements OnInit {

  constructor(private _pessoaService:PessoaService,
    private _router: Router) { }

  totalElements:number;
  size:number;
  page:number = 1;
  pessoas: Person[];

  filter:Filter = {} as Filter;


  ngOnInit() {

    this.getPersons(1);

  }

  getPersons(index){

    this._pessoaService.getPersonIndex(index).subscribe(
      response => {
        
        this.pessoas = response['content'];
        
        this.totalElements = response['totalElements'];
        this.size = response['size'];
        
      }
    )
  }

  getPageablePerson(page){
    
    this._pessoaService.getPersonIndex(page).subscribe(
      response => {
        
        this.pessoas = response['content'];
        this.totalElements = response['totalElements'];
        this.size = response['size'];
        this.page = response['pageable']['pageNumber'];
      }
    )
  }

  pageChanged(pagechange){
    
    if(pagechange!=1){
      this.page = pagechange;
      this.getPageablePerson(this.page);
    }else{
      this.getPersons(1);
    }
    
  }

  goDetail(pessoa) {
   
    this._router.navigate(['pessoas/detalhes', pessoa.id])
  }

  search(){
    this._pessoaService.searchPerson(this.filter).subscribe(
      response => {
        
        this.pessoas = response['content'];
        
      }
    )
   
   }

}
