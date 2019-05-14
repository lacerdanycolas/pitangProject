import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { AccountService } from '../account.service';

@Injectable()
export class BaseService {

  public URL = "http://localhost:8080";
  public URL_MOVIE = "http://localhost:8080/movies?size=6&page=0";
  // public URL_LOGIN = "https://r4mitdivdc.execute-api.us-east-1.amazonaws.com/dev";
 public API_KEY = "8c88c3d8f1562bc40e6de278d635bfaa";


  public _httpClient: HttpClient;
  private account: AccountService;

  constructor(httpClient: HttpClient) {
    this._httpClient = httpClient;
  }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 401) {
    //  this.account.logout();
      location.reload(true);
    }
    return throwError(error);
  }
}
