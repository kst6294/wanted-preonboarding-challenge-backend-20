import knex from "knex";
import knexConfig from "./knexfile.js";

export default knex(knexConfig[process.env.NODE_ENV] || "development");
