package wanted.challenge.aop.api;

public record ApiResponse<T> (
    int status,
    String message,
    T data
) {
   //성공 응답 생성 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, "success", null);
    }

    //실패 응답 생성 메서드
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(400, message, null);
    }
}
