import {PageCounterService} from './page-counter.service';
import {EMPTY_PAGE, Page} from '../interface/page.interface';

describe('PageCounter service', () => {
  let service: PageCounterService;

  // input
  let page: Page<any>;
  let currentPage = 2;
  const maxToDisplay = 5;
  const offset = 2;

  // expected
  let pageList: number[];

  beforeEach(() => {
    service = new PageCounterService()
    page = new MockPage();
  });

  it('should return empty list for empty page', () => {
    page = EMPTY_PAGE;
    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([]);
  });

  it('should return all pages when max to display is more than current available pages', () => {
    page.totalPages = 4;

    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([1, 2, 3, 4]);
  });

  it('should return five first pages for second page selected and more possible pages', () => {
    page.totalPages = 7;
    currentPage = 2;

    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([1, 2, 3, 4, 5]);
    expect(pageList[1]).toEqual(currentPage); // second page should be selected
  });

  it('should return 2-6 pages for fourth page selected and more possible pages', () => {
    page.totalPages = 7;
    currentPage = 4;

    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([2, 3, 4, 5, 6]);
    expect(pageList[2]).toEqual(currentPage); // second page should be selected
  });


  it('should return last pages for 6 (7-1) selected page and 7 possible pages', () => {
    page = new MockPage();
    page.totalPages = 7;
    currentPage = page.totalPages - 1;

    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([3, 4, 5, 6, 7]);
    expect(pageList[3]).toEqual(currentPage);
  });

  it('should return first pages for first page selected and more possible pages', () => {
    page.totalPages = 10;
    currentPage = 1;

    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([1, 2, 3, 4, 5]);
    expect(pageList[0]).toEqual(currentPage);
  });

  it('should return last pages for last page selected and more possible pages', () => {
    page.totalPages = 10;
    currentPage = page.totalPages;

    pageList = service.generatePageList(page, maxToDisplay, offset, currentPage);

    expect(pageList).toEqual([6, 7, 8, 9, 10]);
    expect(pageList[4]).toEqual(currentPage);
  });

});


class MockPage implements Page<any> {
  content: any[];
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  sort: string;
  totalElements: number;
  totalPages: number;

  constructor() {
  }
}
