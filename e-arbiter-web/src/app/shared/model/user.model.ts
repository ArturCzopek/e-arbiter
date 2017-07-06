import {Role} from "./role.model";
export class User {
  constructor(public id: number, public githubId: number, public name: string, public roles: Role[]) {

  }
}
