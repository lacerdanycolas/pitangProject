import { PessoaService } from './../pessoa.service';
import { Person } from 'src/app/models/person';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

@Component({
  selector: 'app-pessoa-detail',
  templateUrl: './pessoa-detail.component.html',
  styleUrls: ['./pessoa-detail.component.css']
})
export class PessoaDetailComponent implements OnInit {

  constructor(private _activatedRoute: ActivatedRoute,
    private _router: Router,
    private modalService: NgbModal,
    private location: Location,
    private _pessoaService: PessoaService) { }

    pessoa:Person;
    closeResult: string;


  ngOnInit() {
    this._activatedRoute.params.subscribe(params => {
     
      let id = params['id'];
     
      this._pessoaService.getById(parseInt(id,10))
        .subscribe(response => {
          this.pessoa = response as Person;
          
          
        })

    });
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

  deletePessoa(){
    this._activatedRoute.params.subscribe(params =>{
      
      let id = params['id']; 
      
      this._pessoaService.deletePerson(parseInt(id,10)).toPromise()
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
