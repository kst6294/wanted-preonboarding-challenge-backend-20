import jwt from "jsonwebtoken";

function verifyToken(req, res, next) {
    try {
        req.decoded = jwt.verify(req.headers.authorization, process.env.JWT_SECRET);
        return next();
    } catch (error) {
        res.status(403).send("자격 증명 실패");
    }
}

export default verifyToken;
