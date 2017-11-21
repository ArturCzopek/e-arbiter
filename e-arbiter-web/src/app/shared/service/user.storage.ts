import {Injectable} from '@angular/core';
import {User} from '../interface/user.interface';

@Injectable()
export class UserStorage {
  private user: User = null;

  public clearUser() {
    this.user = null;
  }

  public getUser() {
    return this.user;
  }

  public setUser(user: User) {
    this.user = user;
  }
}
