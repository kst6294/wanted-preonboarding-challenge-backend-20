package com.api.jellomarket.config.auth

import com.api.jellomarket.domain.user.User
import com.api.jellomarket.domain.user.UserRepository
import jakarta.servlet.http.HttpSession
import lombok.RequiredArgsConstructor
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
@RequiredArgsConstructor
class LoginUserArgumentResolver(
    private val httpSession: HttpSession? = null,
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser::class.java) != null
        val isUserClass = User::class.java == parameter.parameterType
        return isLoginUserAnnotation && isUserClass
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User? {
        val userId = httpSession!!.getAttribute("userId") ?: return null
        return userRepository.findById(userId as Long).get()
    }
}
