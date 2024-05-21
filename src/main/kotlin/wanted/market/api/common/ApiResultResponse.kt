package wanted.market.api.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK

data class ApiResultResponse<T>(
    val status: HttpStatus = OK,
    val data: T
)
