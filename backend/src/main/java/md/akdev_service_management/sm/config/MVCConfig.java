package md.akdev_service_management.sm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    private final FrontEndInterceptor frontEndInterceptor;
    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    public MVCConfig(FrontEndInterceptor frontEndInterceptor) {
        this.frontEndInterceptor = frontEndInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(frontEndInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry cors) {
        cors.addMapping("/**").allowedOrigins(allowedOrigins);
    }

}
