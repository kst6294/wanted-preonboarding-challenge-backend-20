package wanted.market.api.common.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import wanted.market.api.common.security.CustomMemberDetailService
import java.util.*

@Component
class JwtTokenProvider(
    private val customMemberDetailService: CustomMemberDetailService,
    @Value("\${jwt.secret}")
    private var secretKey: String? = null
) {

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun resolveToken(request: HttpServletRequest): String {
        return request.getHeader("Authorization")
    }

    fun decode(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
    }

    // Jwt 토큰으로 인증 정보를 조회
    fun getAuthentication(token: String): Authentication {
        val claims = decode(token.substring("Bearer ".length))
        val userDetails = customMemberDetailService.loadUserByUsername(claims["memberId"].toString())
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        val claims = Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseSignedClaims(jwtToken!!.substring("Bearer ".length))
        return !claims.getBody().expiration.before(Date()) // 만료시간이 현재시간보다 전인지 확인
    }
}
