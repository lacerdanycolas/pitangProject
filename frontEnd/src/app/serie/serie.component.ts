import { TvSerie } from './../models/tvSerie';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SerieService } from './serie.service';
import { Filter } from '../models/filter';

@Component({
  selector: 'app-serie',
  templateUrl: './serie.component.html',
  styleUrls: ['./serie.component.css']
})
export class SerieComponent implements OnInit {
  constructor(private _serieService: SerieService,
    private _router: Router) { }

  series : TvSerie[];
  totalPages:number;
  unique = 0;
  page = {
    index:-1
 }
 pages = [];

 filter:Filter = {} as Filter;

  ngOnInit() {
    this.getSeries(1);
  }

  goDetail(serie) {
    this._router.navigate(['series/detalhes', serie.id])
  }

  getSeries(index){
    this._serieService.getSeriesUrl(index).subscribe(
      response => {
      
        this.series = response['content'];
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
  this._serieService.searchSerie(this.filter).subscribe(
    response => {
      
      this.series = response['content'];
      
    }
  )
 
 }

}
