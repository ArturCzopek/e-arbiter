/**
 * Generic interface for displaying task preview
 * It doesn't matter if it's quiz or code task
 * More details will be passed in other interface
 */
import {TaskUserDetails} from './task-user-details.interface';

export interface TaskPreview {
  id: string,
  name: string,
  description: string,
  maxPoints: number,
  taskUserDetails?: TaskUserDetails
}

