package wanted.preonboarding.backend.auth.config;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wanted.preonboarding.backend.auth.interceptor.AuthenticationInterceptor;
import wanted.preonboarding.backend.auth.interceptor.PathMatcherInterceptor;

public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pathMatcherInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/item/{itemId}");
    }

    private PathMatcherInterceptor pathMatcherInterceptor() {
        PathMatcherInterceptor interceptor = new PathMatcherInterceptor(new AuthenticationInterceptor());
        return interceptor
                .excludePathPattern("/auth/login", HttpMethod.POST)
                .excludePathPattern("/members/sign-up", HttpMethod.POST)
                .excludePathPattern("/item/list", HttpMethod.GET);
    }
}
