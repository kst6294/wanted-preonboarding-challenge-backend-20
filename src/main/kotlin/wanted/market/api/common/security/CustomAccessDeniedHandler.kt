package wanted.market.api.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import wanted.market.api.common.ErrorCode.HANDLE_ACCESS_DENIED
import wanted.market.api.log

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    private val exceptionResponse = ErrorResponse.of(HANDLE_ACCESS_DENIED)

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        log.error { "CustomAccessDeniedHandler => ${accessDeniedException.message}" }

        response.contentType = APPLICATION_JSON_VALUE
        response.status = HANDLE_ACCESS_DENIED.status.value()

        response.outputStream.use { os ->
            val objectMapper = ObjectMapper()
            objectMapper.writeValue(os, exceptionResponse)
            os.flush()
        }
    }
}
