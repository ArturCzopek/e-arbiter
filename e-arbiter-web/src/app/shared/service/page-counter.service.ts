import {Injectable} from '@angular/core';
import {EMPTY_PAGE, Page} from '../interface/page.interface';
import * as _ from 'lodash';

/**
 * Service responsible for calculating pages to display for pagination
 */
@Injectable()
export class PageCounterService {

  public generatePageList(page: Page<any>, maxToDisplay: number, offset: number, currentPage: number): number[] {

    const currentPageSize = 1;

    // empty page -> no data or invalid page
    if (page === EMPTY_PAGE) {
      return [];
    }

    // display all what you can
    if (page.totalPages <= maxToDisplay) {
      return _.range(1, page.totalPages + 1);
    }

    let leftSide = [];

    // display half to the left or first n < offset possibilities
    if (!page.first) {
      leftSide = (currentPage > offset) ? _.range(currentPage - offset, currentPage) : _.range(1, currentPage);
    }

    // how many elements can we add?
    const elToFillToRight = (maxToDisplay - currentPageSize - leftSide.length < page.totalPages - currentPage) ?
      maxToDisplay - 1 - leftSide.length : page.totalPages - currentPage;

    const rightSide = _.range(currentPage + currentPageSize, currentPage + currentPageSize + elToFillToRight);

    // not proper filled array
    if (leftSide.length + currentPageSize + rightSide.length !== maxToDisplay) {
      // we have some space so we should fill left side by it (right would have already been if it would be possible)
      const elToFillToLeft = maxToDisplay - leftSide.length - currentPageSize - rightSide.length;
      leftSide = [..._.range(leftSide[0] - elToFillToLeft, leftSide[0]), ...leftSide];
    }
    return [...leftSide, currentPage, ...rightSide];
  }
}
