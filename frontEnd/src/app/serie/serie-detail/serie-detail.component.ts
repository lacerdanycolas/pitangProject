import { Component, OnInit } from '@angular/core';
import { SerieService } from '../serie.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TvSerie } from 'src/app/models/tvSerie';
import { Genre } from 'src/app/models/genre';
import { Person } from 'src/app/models/person';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

@Component({
  selector: 'app-serie-detail',
  templateUrl: './serie-detail.component.html',
  styleUrls: ['./serie-detail.component.css']
})
export class SerieDetailComponent implements OnInit {

  constructor(private _activatedRoute: ActivatedRoute,
    private _serieService: SerieService,private modalService: NgbModal,
    private location: Location, private _router: Router) { }

    serie:TvSerie;
    genreSerie:Genre[] = [];
    listGenres:Genre[];
    castPerson:Person[] = [];
    slides=[];
    closeResult: string;
    
  ngOnInit() {

    this._serieService.getGenres().subscribe(
      response => {
        this.listGenres = response['content'];
      }
    )  


    this._activatedRoute.params.subscribe(params => {
      let id = params['id'];

      this._serieService.getById(id)
        .subscribe(response => {
          this.serie = response as TvSerie;
          
          this.buildGenresSerie(this.serie['genres_id']);
        })
    });

    this._activatedRoute.params.subscribe(params =>{
      let id = params['id'];

      this._serieService.getCastSerie(parseInt(id,10))
          .subscribe(response => {
            this.castPerson = response['persons'];
            this.buildIndex(this.castPerson.length);
          })
    });

  }

  buildGenresSerie(list){
    
    this.listGenres.forEach(genreAll => {
      list.forEach(genreSerie => {
        
       
        let id = genreAll['id'];
        let idSerie = genreSerie;
      
        if(id == idSerie){
          this.genreSerie.push(genreAll); 
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


openEditar(serie){
  this._router.navigate(['detalhes/:id/editarTv', serie.id])
}

deleteSerie(){
  
  this._activatedRoute.params.subscribe(params =>{
    
    let id = params['id']; 
    
    this._serieService.deleteSerie(parseInt(id,10)).toPromise()
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
