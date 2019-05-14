import { PessoaService } from './pessoa.service';
import { PessoaRoutingModule } from './pessoa.routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PessoaComponent } from './pessoa.component';
import { PessoaDetailComponent } from './pessoa-detail/pessoa-detail.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [PessoaComponent, PessoaDetailComponent],
  imports: [
    CommonModule,
    NgbModule,
    FormsModule,
    PessoaRoutingModule
  ],
  providers:[PessoaService]
})
export class PessoaModule { }
