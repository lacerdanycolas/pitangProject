import { EventEmitter, Injectable } from '@angular/core';
import { BaseService } from './base/base.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class AccountService extends BaseService {

  /*
  public loggedEmitter = new EventEmitter<boolean>();

  constructor(private http: HttpClient) {
    super(http);
  }

  public login(username: string, password: string): Observable<Account> {
    return this.http.post<any>(`${this.URL_LOGIN}/login`, { account: { username, password } })
      .pipe(map(account => {
        if (account) {
          localStorage.setItem('me', JSON.stringify(account));
        }
        this.loggedEmitter.emit(true);
        return account;
      }));
  }

  public logout() {
    this.loggedEmitter.next();
    localStorage.removeItem('me');
  }

  public me(): Account {
    const me = localStorage.getItem('me');
    if (me) {
      const { account } = JSON.parse(me);
      return account;
    }
  }*/
}
