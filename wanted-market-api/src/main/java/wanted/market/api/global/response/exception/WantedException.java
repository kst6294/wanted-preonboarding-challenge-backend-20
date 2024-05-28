package wanted.market.api.global.response.exception;

import wanted.market.api.global.response.enums.ExceptionMessage;

public class WantedException extends RuntimeException{

    public WantedException(){
        super();
    }
    public WantedException(ExceptionMessage message){
        super(message.getName());
    }

    public WantedException(ExceptionMessage message, Throwable cause){
        super(message.getName(), cause);
    }

    public WantedException(Throwable cause){
        super(cause);
    }

}