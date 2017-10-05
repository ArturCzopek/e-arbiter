export interface TaskUserDetails {
  taskId: string
  earnedPoints: number,
  userAttempts: number,
  maxAttempts?: number   // if null, there user has infinite amount of attempts
}
