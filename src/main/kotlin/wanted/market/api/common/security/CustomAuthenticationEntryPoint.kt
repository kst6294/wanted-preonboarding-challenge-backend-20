package wanted.market.api.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import wanted.market.api.common.ErrorCode.HANDLE_ACCESS_DENIED
import java.io.IOException

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val exceptionResponse: ErrorResponse = ErrorResponse.of(HANDLE_ACCESS_DENIED)

    override fun commence(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: AuthenticationException) {
        httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
        httpServletResponse.status = HttpStatus.UNAUTHORIZED.value()

        httpServletResponse.outputStream.use { os ->
            val objectMapper = ObjectMapper()
            objectMapper.writeValue(os, exceptionResponse)
            os.flush()
        }
    }
}