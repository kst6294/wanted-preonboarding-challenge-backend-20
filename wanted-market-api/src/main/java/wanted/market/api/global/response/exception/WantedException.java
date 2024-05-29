package wanted.market.api.global.response.exception;

import wanted.market.api.global.response.enums.ExceptionDomain;
import wanted.market.api.global.response.enums.ExceptionMessage;

public class WantedException extends RuntimeException{

    private String domain;
    public WantedException(){
        super();
    }
    public WantedException(ExceptionMessage message){
        super(message.getName());
    }
    public WantedException(ExceptionDomain exceptionDomain, ExceptionMessage message){
        super(message.getName());
        this.domain= exceptionDomain.getValue();
    }

    public WantedException(ExceptionMessage message, Throwable cause){
        super(message.getName(), cause);
    }

    public WantedException(Throwable cause){
        super(cause);
    }

    public String getDomain(){
        return this.domain;
    }
}