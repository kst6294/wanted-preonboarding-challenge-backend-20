function dbErrorHandler(err, req, res, next) {
    console.error(err);

    if (err.message.includes("query")) {
        const error = new Error("질의 오류");
        error.status = 400;
        return next(error);
    } else {
        return next(err);
    }
}

export default dbErrorHandler;
