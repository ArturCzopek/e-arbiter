/**
 * Generic interface for displaying task preview
 * It doesn't matter if it's quiz or code task
 * More details will be passed in other interface
 */
export interface TaskPreview {
  name: string,
  description: string,
  maxPoints: number,
  earnedPoints?: number,
  userAttempts?: number,
  maxAttempts?: number   // if null, there user has infinite amount of attempts
}

