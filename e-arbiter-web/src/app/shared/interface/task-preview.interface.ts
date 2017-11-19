/**
 * Generic interface for displaying task preview
 * More details will be passed in other interface
 */
import { TaskUserDetails } from './task-user-details.interface';

export interface TaskPreview {
  tournamentId: string,
  name: string,
  description: string,
  maxPoints: number,
  type: string,
  taskUserDetails?: TaskUserDetails,
  languages: String[]
}

