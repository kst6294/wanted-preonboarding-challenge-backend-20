// Update with your config settings.

/**
 * @type { Object.<string, import("knex").Knex.Config> }
 */
import dotenv from "dotenv";
dotenv.config();

const knexConfig = {
    development: {
        client: "sqlite3",
        useNullAsDefault: true,
        connection: {
            filename: "db/dev.sqlite3",
        },
    },

    staging: {},

    production: {},
};

export default knexConfig;
