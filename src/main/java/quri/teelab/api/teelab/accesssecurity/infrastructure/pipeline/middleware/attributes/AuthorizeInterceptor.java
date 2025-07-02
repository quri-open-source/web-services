package quri.teelab.api.teelab.accesssecurity.infrastructure.pipeline.middleware.attributes;

import jakarta.servlet.http.HttpServletRequest;
import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor to handle authorization
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        
        // Check if method has @AllowAnonymous annotation
        AllowAnonymous allowAnonymous = handlerMethod.getMethodAnnotation(AllowAnonymous.class);
        if (allowAnonymous != null) {
            System.out.println("Skipping authorization");
            return true;
        }
        
        // Check if class or method has @Authorize annotation
        Authorize authorize = handlerMethod.getMethodAnnotation(Authorize.class);
        if (authorize == null) {
            authorize = handlerMethod.getBeanType().getAnnotation(Authorize.class);
        }
        
        if (authorize != null) {
            // Verify if a user is signed in by checking if request.getAttribute("User") is set
            User user = (User) request.getAttribute("User");
            
            // If a user is not signed in, then return 401-status code
            if (user == null) {
                response.setStatus(401);
                return false;
            }
        }
        
        return true;
    }
}
