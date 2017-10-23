export interface AdminServiceData {
  serviceName: string;
  port: string;
  profiles: string[];
  javaVersion: string;
  logFilePath: string;
  memoryFree: string;
  memoryTotal: string;
  memoryMax: string;
  health: string
}
