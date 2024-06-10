export interface AuthServiceInterface {
  validateUser(email: string, password: string): Promise<any>;
  login(email: string, password: string): Promise<{ accessToken: string }>;
}
