function dbErrorHandler(err, req, res, next) {
    console.error(err, err.message);

    if (err.message.includes("query")) {
        const error = new Error("질의 오류");
        error.status = 400;
        return next(error);
    } else if (err.code === "SQLITE_BUSY") {
        const error = new Error("수행 실패, 다시 시도");
        error.status = 501;
        return next(error);
    } else {
        return next(err);
    }
}

export default dbErrorHandler;
