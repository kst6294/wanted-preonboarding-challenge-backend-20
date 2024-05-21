package wanted.market.api.member.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.common.jwt.JwtTokenGenerator
import wanted.market.api.member.domain.dto.`in`.CommandLoginMember
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.member.domain.entity.Tokens
import wanted.market.api.member.repository.MemberRepository

@Service
@Transactional
class MemberAuthService(
    private val memberRepository: MemberRepository,
    private val jwtTokenGenerator: JwtTokenGenerator
) {
    fun registerMember(request: CommandRegisterMember): CommandRegisterMemberResult {

        memberRepository.findByUserId(request.userId)?.let {
            throw IllegalArgumentException("이미 존재하는 아이디입니다.")
        }

        val member = Member.register(request.userId, request.password)

        memberRepository.save(member)

        return CommandRegisterMemberResult(
            id = member.id!!,
            userId = member.userId,
            password = member.password
        )
    }

    fun loginMember(request: CommandLoginMember): Tokens {
        val member = memberRepository.findByUserIdAndPassword(request.userId, request.password)
            ?: throw IllegalArgumentException("회원이 존재하지 않습니다.")
        return jwtTokenGenerator.create(member)
    }
}