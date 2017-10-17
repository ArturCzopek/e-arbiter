import {DateService} from './date.service';
import {LocalDateTime} from '../interface/local-date-time.interface';

describe('DateService', () => {
  let dateService: DateService;

  beforeEach(() => {
    dateService = new DateService();
  });

  it('#parseDateToString should return correct parsed date object to string (19th April 2017 15:11:00.000)', () => {
    const dateToParse = new Date(2017, 3, 19, 15, 11, 0, 0);
    const expectedParsedDate = '2017-04-19T15:11:00.000';
    expect(dateService.parseDateToString(dateToParse)).toBe(expectedParsedDate);
  });

  it('#parseDateToString should return correct parsed date object to string (1st January 2017 12:00:00.000)', () => {
    const dateToParse = new Date(2017, 0, 1, 12, 0, 0, 0);
    const expectedParsedDate = '2017-01-01T12:00:00.000';
    expect(dateService.parseDateToString(dateToParse)).toBe(expectedParsedDate);
  });

  it('#parseDateToString should return empty string for null date', () => {
    const dateToParse = null;
    const expectedParsedDate = '';
    expect(dateService.parseDateToString(dateToParse)).toBe(expectedParsedDate);
  });

  it('#parseLocalDateTimeToString should return correct parsed local date time object to string (19th April 2017 15:11)', () => {{
    const localDateTimeToParse = new MockLocalDateTime(0, 11, 15, 19, 4, 2017);
    const expectedParsedLocalDateTime =  '19/04/2017 15:11';
    expect(dateService.parseLocalDateTimeToString(localDateTimeToParse)).toBe(expectedParsedLocalDateTime);
  }});

  it('#parseLocalDateTimeToString should return correct parsed local date time object to string (1st January 2017 12:00)', () => {{
    const localDateTimeToParse = new MockLocalDateTime(0, 0, 12, 1, 1, 2017);
    const expectedParsedLocalDateTime =  '01/01/2017 12:00';
    expect(dateService.parseLocalDateTimeToString(localDateTimeToParse)).toBe(expectedParsedLocalDateTime);
  }});

  it('#parseLocalDateTimeToString should return empty string for null local date time', () => {
    const localDateTimeToParse  = null;
    const expectedParsedLocalDateTime = '';
    expect(dateService.parseLocalDateTimeToString(localDateTimeToParse)).toBe(expectedParsedLocalDateTime);
  });
});


export class MockLocalDateTime implements LocalDateTime {
  constructor(public second: number,
              public minute: number,
              public hour: number,
              public dayOfMonth: number,
              public monthValue: number,
              public year: number) {

  }
}
