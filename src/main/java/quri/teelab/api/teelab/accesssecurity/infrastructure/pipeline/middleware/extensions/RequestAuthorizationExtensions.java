package quri.teelab.api.teelab.accesssecurity.infrastructure.pipeline.middleware.extensions;

import jakarta.servlet.http.HttpServletRequest;
import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;

import java.util.Optional;
import java.util.UUID;

/**
 * RequestAuthorizationExtensions
 * This class includes utility methods for working with request authorization.
 */
public class RequestAuthorizationExtensions {

    /**
     * Gets the authenticated user from the request attributes.
     * 
     * @param request The HTTP servlet request
     * @return Optional containing the authenticated user if present, empty otherwise
     */
    public static Optional<User> getAuthenticatedUser(HttpServletRequest request) {
        Object userObj = request.getAttribute("User");
        if (userObj instanceof User) {
            return Optional.of((User) userObj);
        }
        return Optional.empty();
    }

    /**
     * Checks if the request has an authenticated user.
     * 
     * @param request The HTTP servlet request
     * @return true if the request has an authenticated user, false otherwise
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getAuthenticatedUser(request).isPresent();
    }

    /**
     * Gets the user ID from the authenticated user in the request.
     * 
     * @param request The HTTP servlet request
     * @return Optional containing the user ID if authenticated, empty otherwise
     */
    public static Optional<UUID> getAuthenticatedUserId(HttpServletRequest request) {
        return getAuthenticatedUser(request).map(User::getId);
    }

    /**
     * Gets the username from the authenticated user in the request.
     * 
     * @param request The HTTP servlet request
     * @return Optional containing the username if authenticated, empty otherwise
     */
    public static Optional<String> getAuthenticatedUsername(HttpServletRequest request) {
        return getAuthenticatedUser(request).map(User::getUsername);
    }

    /**
     * Extracts the Bearer token from the Authorization header.
     * 
     * @param request The HTTP servlet request
     * @return Optional containing the token if present, empty otherwise
     */
    public static Optional<String> extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }

    /**
     * Checks if the request path should be excluded from authorization.
     * 
     * @param requestPath The request path to check
     * @return true if the path should be excluded, false otherwise
     */
    public static boolean isExcludedPath(String requestPath) {
        if (requestPath == null) {
            return false;
        }
        
        String path = requestPath.toLowerCase();
        return path.startsWith("/swagger") ||
               path.startsWith("/api/v1/authentication") ||
               path.equals("/") ||
               path.contains("/swagger-ui") ||
               path.contains("/v3/api-docs") ||
               path.contains("/swagger.json") ||
               path.contains("/swagger-resources") ||
               path.contains("/webjars") ||
               path.endsWith(".html") ||
               path.endsWith(".css") ||
               path.endsWith(".js") ||
               path.endsWith(".png") ||
               path.endsWith(".ico");
    }

    /**
     * Sets the authenticated user in the request attributes.
     * 
     * @param request The HTTP servlet request
     * @param user The authenticated user to set
     */
    public static void setAuthenticatedUser(HttpServletRequest request, User user) {
        request.setAttribute("User", user);
    }

    /**
     * Removes the authenticated user from the request attributes.
     * 
     * @param request The HTTP servlet request
     */
    public static void removeAuthenticatedUser(HttpServletRequest request) {
        request.removeAttribute("User");
    }

    /**
     * Checks if the request contains a valid authorization header format.
     * 
     * @param request The HTTP servlet request
     * @return true if the authorization header is in valid format, false otherwise
     */
    public static boolean hasValidAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && authHeader.startsWith("Bearer ") && authHeader.length() > 7;
    }
}