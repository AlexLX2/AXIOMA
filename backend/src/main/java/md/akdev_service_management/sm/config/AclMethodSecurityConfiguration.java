package md.akdev_service_management.sm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AclMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    private final MethodSecurityExpressionHandler methodSecurityExpressionHandler;

    @Autowired
    public AclMethodSecurityConfiguration(MethodSecurityExpressionHandler methodSecurityExpressionHandler) {
        this.methodSecurityExpressionHandler = methodSecurityExpressionHandler;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler(){
        return methodSecurityExpressionHandler;
    }
}
