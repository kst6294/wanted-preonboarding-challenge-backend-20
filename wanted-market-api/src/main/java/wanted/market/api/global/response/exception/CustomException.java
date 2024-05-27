package wanted.market.api.global.response.exception;

import wanted.market.api.global.response.enums.ExceptionMessage;

public class CustomException extends RuntimeException{

    public CustomException(){
        super();
    }
    public CustomException(ExceptionMessage message){
        super(message.getName());
    }

    public CustomException(ExceptionMessage message, Throwable cause){
        super(message.getName(), cause);
    }

    public CustomException(Throwable cause){
        super(cause);
    }

}