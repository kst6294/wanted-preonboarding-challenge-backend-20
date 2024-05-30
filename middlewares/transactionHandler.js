import dbClient from "../db/dbClient.js";

function transactionHandler(req, res, next) {
    req.transactionProvider = dbClient.transactionProvider();
    next();
}

export default transactionHandler;
