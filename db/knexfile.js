// Update with your config settings.

/**
 * @type { Object.<string, import("knex").Knex.Config> }
 */
import dotenv from "dotenv";
dotenv.config();

const knexConfig = {
    test: {
        client: "sqlite3",
        useNullAsDefault: true,
        connection: {
            filename: "db/test.sqlite3",
        },
        pool: { min: 0, max: 10 },
    },

    development: {
        client: "sqlite3",
        useNullAsDefault: true,
        connection: {
            filename: "db/dev.sqlite3",
        },
        pool: { min: 0, max: 10 },
    },

    staging: {},

    production: {},
};

export default knexConfig;
