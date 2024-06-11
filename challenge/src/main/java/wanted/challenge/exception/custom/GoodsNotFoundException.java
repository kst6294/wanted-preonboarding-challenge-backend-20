package wanted.challenge.exception.custom;

public class GoodsNotFoundException extends RuntimeException {
    public GoodsNotFoundException(String message) {
        super(message);
    }
}
