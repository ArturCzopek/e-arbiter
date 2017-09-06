import {Injectable} from "@angular/core";
import {LocalDateTime} from "../interface/local-date-time.interface";

/**
 * Service for manipulating Date and LocalDateTime objects. You can add moment.js library when will be needed
 */
@Injectable()
export class DateService {

  public parseLocalDateTimeToString(localDateTimeObj: LocalDateTime): string {
    const {year, monthValue, dayOfMonth, hour, minute} = localDateTimeObj;
    return `${this.formatNumber(dayOfMonth)}/${this.formatNumber(monthValue)}/${year} ${this.formatNumber(hour)}:${this.formatNumber(minute)}`;
  }

  private formatNumber(value: number): string {
    return (value > 9) ? `${value}` : `0${value}`;
  }
}
