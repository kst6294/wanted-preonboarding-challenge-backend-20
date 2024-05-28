package wanted.market.common.exception


import org.hibernate.TypeMismatchException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BindException::class)
    fun bindException(bindingResult: BindingResult): ResponseEntity<ErrorResponse> {
        val reason = StringBuilder()
        for (fieldError in bindingResult.fieldErrors) {
            val errorMessage = "${fieldError.field} : ${fieldError.defaultMessage}"
            reason.append(errorMessage).append(", ")
        }
        log.error("ValidationException - {}", reason)
        val errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), reason.toString())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(TypeMismatchException::class, MissingServletRequestParameterException::class)
    fun notValidParameterException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("[{}] {} ({})", e.javaClass.simpleName, e.message, e.stackTrace[0])
        val errorResponse = ErrorResponse(ErrorCode.BAD_REQUEST.httpStatus, ErrorCode.BAD_REQUEST.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(BaseException::class)
    fun badRequestException(e: BaseException): ResponseEntity<ErrorResponse> {
        log.error("[{}] {} ({})", e.javaClass.simpleName, e.message, e.stackTrace[0])
        val errorResponse = ErrorResponse(e.errorCode.httpStatus, e.message)
        return ResponseEntity.status(e.errorCode.httpStatus).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("[{}] {} ({})", e.javaClass.simpleName, e.message, e.stackTrace[0])
        val errorResponse = ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.httpStatus, ErrorCode.INTERNAL_SERVER_ERROR.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
