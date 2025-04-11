package org.socialnet.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtGatewayFilter jwtGatewayFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-auth", r -> r.path("/user-service/auth/**")
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path("/user-service/users/**")
                        .filters(f -> f.filter(jwtGatewayFilter.apply(new JwtGatewayFilter.Config())))
                        .uri("lb://user-service"))
                .route("post-service", r -> r.path("/posts/**")
                        .filters(f -> f.filter(jwtGatewayFilter.apply(new JwtGatewayFilter.Config())))
                        .uri("lb://post-service"))
                .build();
    }
}
