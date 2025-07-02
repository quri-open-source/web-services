package quri.teelab.api.teelab.accesssecurity.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import quri.teelab.api.teelab.accesssecurity.infrastructure.pipeline.middleware.attributes.AuthorizeInterceptor;

/**
 * Web MVC configuration for custom interceptors
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizeInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/authentication/**", "/swagger-ui/**", "/v3/api-docs/**");
    }
}
