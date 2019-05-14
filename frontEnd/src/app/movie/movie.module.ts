import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovieComponent } from './movie.component';
import { MovieDetailComponent } from './movie-detail/movie-detail.component';
import { MovieRoutingModule } from './movie.routing.module';
import { MovieService } from './movie.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';




@NgModule({
  declarations: [
    MovieComponent,
    MovieDetailComponent,
    
  ],
  imports: [
    CommonModule,
    MovieRoutingModule,
    NgbModule,
    FormsModule,
    
   
  ],
  providers:[
    MovieService
  ],
  exports: [],
  bootstrap:[]
})
export class MovieModule { }
