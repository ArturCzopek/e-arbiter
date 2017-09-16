// Page interface from spring
export interface Page<T> {
  content: T[],
  first: boolean,
  last: boolean,
  number: number,
  numberOfElements: number,
  size: number,
  sort: string,
  totalElements: number,
  totalPages: number
}

export const EMPTY_PAGE: Page<any> = {
  content: [],
  first: true,
  last: true,
  number: 0,
  numberOfElements: 0,
  size: 0,
  sort: null,
  totalElements: 0,
  totalPages: 0
};
