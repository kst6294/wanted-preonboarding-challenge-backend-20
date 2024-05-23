import App from "./src/app";
import database from "./src/database";

const app = new App();

app.listen();
database.connect();
