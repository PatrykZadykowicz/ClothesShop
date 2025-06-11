package wi.pb.clothesshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("requestLogger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String logEntry = String.format("[%s] %s %s from IP %s",
                LocalDateTime.now(),
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        logger.info(logEntry);
        return true;
    }
}