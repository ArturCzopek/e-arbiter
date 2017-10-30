import {Role} from './role.interface';

export interface User {
  id: number,
  githubId: number,
  name: string,
  enabled: boolean,
  roles: Role[]
}
