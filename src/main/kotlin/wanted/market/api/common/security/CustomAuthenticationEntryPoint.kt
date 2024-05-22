package wanted.market.api.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import wanted.market.api.common.ErrorCode.HANDLE_ACCESS_DENIED

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val exceptionResponse: ErrorResponse = ErrorResponse.of(HANDLE_ACCESS_DENIED)

    override fun commence(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: AuthenticationException) {
        httpServletResponse.contentType = APPLICATION_JSON_VALUE
        httpServletResponse.status = UNAUTHORIZED.value()

        httpServletResponse.outputStream.use { os ->
            val objectMapper = ObjectMapper()
            objectMapper.writeValue(os, exceptionResponse)
            os.flush()
        }
    }
}