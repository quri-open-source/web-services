package quri.teelab.api.teelab.accesssecurity.infrastructure.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import quri.teelab.api.teelab.accesssecurity.infrastructure.pipeline.middleware.components.RequestAuthorizationMiddleware;

/**
 * Filter configuration for custom authorization middleware
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<RequestAuthorizationMiddleware> authorizationFilter(
            RequestAuthorizationMiddleware requestAuthorizationMiddleware) {
        
        FilterRegistrationBean<RequestAuthorizationMiddleware> registrationBean = 
                new FilterRegistrationBean<>();
        
        registrationBean.setFilter(requestAuthorizationMiddleware);
        
        registrationBean.setOrder(1);
        
        return registrationBean;
    }
}
