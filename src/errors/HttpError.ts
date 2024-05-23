export default class HttpError extends Error {
  status: number;
  message: string;

  constructor(status: number, message: string) {
    super(message);

    this.status = status;
    this.message = message;
    this.name = this.constructor.name;

    Object.setPrototypeOf(this, HttpError.prototype);
  }
}
