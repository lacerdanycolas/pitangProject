
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // this is needed!
import { MatPaginatorModule } from '@angular/material/paginator';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AccountService } from './services/account.service';

import { EditComponent } from './edit/edit.component';
import { MovieService } from './movie/movie.service';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { BrMasker4Module } from 'brmasker4';




@NgModule({
  declarations: [
    AppComponent,
    EditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatPaginatorModule,
    NgbModule.forRoot(),
    BrMasker4Module 
  ],
  providers: [
    AccountService,
    MovieService,
    NgbCarouselConfig

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
