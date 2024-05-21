package wanted.market.api.common.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.member.domain.entity.Tokens
import java.security.Key
import java.util.*

@Component
class JwtTokenGenerator(
    @Value("\${jwt.access-token-valid-seconds}")
    private val accessTokenValidSeconds: Long,
    @Value("\${jwt.refresh-token-valid-seconds}")
    private val refreshTokenValidSeconds: Long,
    @Value("\${jwt.secret}")
    private val jwtSecret: String
) {
    private val key: Key = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun create(member: Member): Tokens {
        val nowInMilliseconds = Date().time

        val accessToken =
            createAccessToken(member.id!!, member.userId, getAccessTokenValid(nowInMilliseconds))

        return Tokens(accessToken, member.userId)
    }

    private fun getAccessTokenValid(nowInMilliseconds: Long): Date {
        return Date(nowInMilliseconds + accessTokenValidSeconds * 1000)
    }

    private fun createAccessToken(memberId: Long, userId: String, expiry: Date): String {
        val claims = createClaims(memberId, userId)
        return Jwts.builder()
            .claims(claims)
            .setIssuedAt(Date())
            .setExpiration(expiry)
            .signWith(key, HS256)
            .compact()
    }

    companion object {
        private fun createClaims(memberId: Long, userId: String): Map<String, Any?> {
            val claims: MutableMap<String, Any?> = HashMap()
            claims["memberId"] = memberId
            claims["userId"] = userId
            claims["uuid"] = UUID.randomUUID().toString()
            return claims
        }
    }
}