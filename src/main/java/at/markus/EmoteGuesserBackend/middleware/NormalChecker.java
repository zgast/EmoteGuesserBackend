package at.markus.EmoteGuesserBackend.middleware;

import at.markus.EmoteGuesserBackend.security.Keys;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NormalChecker  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return request.getHeader("key").equals(Keys.NORMAL);
    }
}
