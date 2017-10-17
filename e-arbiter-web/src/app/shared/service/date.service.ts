import {Injectable} from '@angular/core';
import {LocalDateTime} from '../interface/local-date-time.interface';

declare var moment: any;

/**
 * Service for manipulating Date and LocalDateTime objects. You can add moment.js library when will be needed
 */
@Injectable()
export class DateService {

  public parseDateToString(dateObj: Date): string {
    return (dateObj) ? moment(dateObj).format('YYYY-MM-DD[T]HH:mm:ss.SSS') : '';
  }

  public parseLocalDateTimeToString(localDateTimeObj: LocalDateTime): string {
    if (!localDateTimeObj) {
      return '';
    }

    const {year, monthValue, dayOfMonth, hour, minute} = localDateTimeObj;
    return `${this.formatNumber(dayOfMonth)}/${this.formatNumber(monthValue)}/${year} ${this.formatNumber(hour)}:${this.formatNumber(minute)}`;
  }

  public isPassedDateLaterThanNow(dateObj: Date): boolean {
    return (moment().diff(dateObj) < 0)  ? true : false; // if date is later than now, moment will return number less than 0
  }

  private formatNumber(value: number): string {
    return (value > 9) ? `${value}` : `0${value}`;
  }
}
