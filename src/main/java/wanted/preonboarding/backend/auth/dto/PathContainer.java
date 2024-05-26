package wanted.preonboarding.backend.auth.dto;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;

public class PathContainer {

    private final PathMatcher pathMatcher;
    private final List<RequestPath> includePathPatterns;
    private final List<RequestPath> excludePathPatterns;

    public PathContainer() {
        this.pathMatcher = new AntPathMatcher();
        this.includePathPatterns = new ArrayList<>();
        this.excludePathPatterns = new ArrayList<>();
    }

    public boolean isNotIncludePath(String targetPath, String targetMethod) {
        boolean isIncluded = this.includePathPatterns.stream()
                .anyMatch(requestPath -> anyMatchedPath(targetPath, targetMethod, requestPath));
        boolean isExcluded = this.excludePathPatterns.stream()
                .anyMatch(requestPath -> anyMatchedPath(targetPath, targetMethod, requestPath));

        return isExcluded || !isIncluded;
    }

    private boolean anyMatchedPath(String targetPath, String targetMethod, RequestPath requestPath) {
        return pathMatcher.match(requestPath.getPath(), targetPath)
                && targetMethod.equals(requestPath.getHttpMethod().toString());
    }

    public void includePathPattern(String pathPattern, HttpMethod httpMethod) {
        this.includePathPatterns.add(new RequestPath(pathPattern, httpMethod));
    }

    public void excludePathPattern(String pathPattern, HttpMethod httpMethod) {
        this.excludePathPatterns.add(new RequestPath(pathPattern, httpMethod));
    }
}
