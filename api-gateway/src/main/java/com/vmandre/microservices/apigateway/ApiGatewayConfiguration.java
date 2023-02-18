package com.vmandre.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        Function<PredicateSpec, Buildable<Route>> routeFunction
                = p -> p.path("/get")
                .filters(f -> f
                        .addRequestHeader("MyHeader", "MyURI")
                        .addRequestParameter("Param", "MyValue"))
                .uri("http://httpbin.org:80");
        return builder.routes()
                .route(routeFunction)
                .route(p -> p.path("/currency-exchange/**") // redirect using the naming server
                        .uri("lb://currency-exchange/")) // name of the service in eureka server and load balance between the instances
                .route(p -> p.path("/currency-conversion/**") // redirect using the naming server
                        .uri("lb://currency-conversion/")) // name of the service in eureka server and load balance between the instances
                .route(p -> p.path("/currency-conversion-feign/**") // redirect using the naming server
                        .uri("lb://currency-conversion/")) // name of the service in eureka server and load balance between the instances
                .route(p -> p.path("/currency-conversion-new/**") // redirect using the naming server
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"
                        ))
                        .uri("lb://currency-conversion/")) // name of the service in eureka server and load balance between the instances
                .build();
    }
}
