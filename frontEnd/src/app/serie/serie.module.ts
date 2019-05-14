import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SerieRoutingModule } from './serie.routing.module';
import { SerieComponent } from './serie.component';
import { SerieDetailComponent } from './serie-detail/serie-detail.component';
import { SerieService } from './serie.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    SerieComponent,
    SerieDetailComponent
  ],
  imports: [
    CommonModule,
    SerieRoutingModule,
    NgbModule,
    FormsModule,
  ],
  providers: [
    SerieService
  ]
})
export class SerieModule { }
