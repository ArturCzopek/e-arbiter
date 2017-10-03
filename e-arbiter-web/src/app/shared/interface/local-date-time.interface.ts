/**
 * This interface has more fields. We need only few of them for now so not whole interface is mapped
 */
export interface LocalDateTime {
  second: number;
  minute: number;
  hour: number;
  dayOfMonth: number;
  monthValue: number;
  year: number;
}

export class TmpLocalDateTime implements LocalDateTime {
  constructor(public second: number,
              public minute: number,
              public hour: number,
              public dayOfMonth: number,
              public monthValue: number,
              public year: number) {

  }
}
