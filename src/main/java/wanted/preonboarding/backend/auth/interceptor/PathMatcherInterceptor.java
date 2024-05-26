package wanted.preonboarding.backend.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import wanted.preonboarding.backend.auth.dto.PathContainer;

public class PathMatcherInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor interceptor;
    private final PathContainer pathContainer;

    public PathMatcherInterceptor(HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
        this.pathContainer = new PathContainer();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isNotIncludePath = pathContainer.isNotIncludePath(request.getServletPath(), request.getMethod());
        if (isNotIncludePath) {
            return true;
        }

        return interceptor.preHandle(request, response, handler);
    }

    public PathMatcherInterceptor includePathPattern(String pathPattern, HttpMethod httpMethod) {
        pathContainer.includePathPattern(pathPattern, httpMethod);
        return this;
    }

    public PathMatcherInterceptor excludePathPattern(String pathPattern, HttpMethod httpMethod) {
        pathContainer.excludePathPattern(pathPattern, httpMethod);
        return this;
    }
}
