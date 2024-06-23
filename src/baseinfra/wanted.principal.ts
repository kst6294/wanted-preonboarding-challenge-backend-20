

export class WantedPrincipal {
  id: number;
  email: string;
  authorities?: string[];

  constructor(user: { id: number, username: string, authorities?: string[]; }) {
    const { id, username, authorities } = user;
    this.id = id;
    this.email = username;
    this.authorities = authorities ?? [];
  }
}