package quri.teelab.api.teelab.accesssecurity.infrastructure.pipeline.middleware.components;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices.ITokenService;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByIdQuery;
import quri.teelab.api.teelab.accesssecurity.domain.services.IUserQueryService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * RequestAuthorizationMiddleware is a custom filter.
 * This filter is used to authorize requests.
 * It validates a token is included in the request header and that the token is valid.
 * If the token is valid then it sets the user in request attributes.
 */
@Component
public class RequestAuthorizationMiddleware implements Filter {

    private final IUserQueryService userQueryService;
    private final ITokenService tokenService;

    public RequestAuthorizationMiddleware(IUserQueryService userQueryService, ITokenService tokenService) {
        this.userQueryService = userQueryService;
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        System.out.println("Entering RequestAuthorizationMiddleware");
        
        // Skip authorization for Swagger endpoints and other public paths
        String path = httpRequest.getRequestURI().toLowerCase();
        if (path.startsWith("/swagger") || 
            path.startsWith("/api/v1/authentication") ||
            path.equals("/") || 
            path.contains("/swagger-ui") ||
            path.contains("/v3/api-docs") ||
            path.contains("/swagger.json")) {
            
            System.out.println("Skipping authorization for public path: " + path);
            chain.doFilter(request, response);
            return;
        }
        
        System.out.println("Entering authorization");
        
        try {
            // Get token from request header
            String authHeader = httpRequest.getHeader("Authorization");
            String token = null;
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            // If token is null then throw exception
            if (token == null) {
                throw new RuntimeException("Null or invalid token");
            }

            // Validate token
            Optional<UUID> userId = tokenService.validateToken(token);

            // If token is invalid then throw exception
            if (userId.isEmpty()) {
                throw new RuntimeException("Invalid token");
            }

            // Get user by id
            var getUserByIdQuery = new GetUserByIdQuery(userId.get());
            var user = userQueryService.handle(getUserByIdQuery);
            
            if (user.isPresent()) {
                System.out.println("Successful authorization. Updating Request...");
                httpRequest.setAttribute("User", user.get());
                System.out.println("Continuing with Filter Chain");
            } else {
                throw new RuntimeException("User not found");
            }
            
            // Continue with filter chain
            chain.doFilter(request, response);
            
        } catch (Exception e) {
            System.out.println("Authorization failed: " + e.getMessage());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"error\":\"Unauthorized\"}");
            httpResponse.setContentType("application/json");
        }
    }
}