/**
 * Generic interface for displaying task preview
 * It doesn't matter if it's quiz or code task
 * More details will be passed in other interface
 */
export interface TaskPreview {
  name: string,
  description: string,
  maxPoints: number,
  pointsReceived?: number,
  userAttempts?: number,
  maxAttempts?: number   // if null, there user has infinite amount of attempts
}

export class TmpTaskPreview implements TaskPreview {
  constructor(public name: string,
              public description: string,
              public maxPoints: number,
              public pointsReceived?: number,
              public userAttempts?: number,
              public maxAttempts?: number) {

  }
}
