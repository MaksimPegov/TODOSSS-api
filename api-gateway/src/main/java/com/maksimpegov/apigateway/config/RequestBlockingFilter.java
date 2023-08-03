package com.maksimpegov.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RequestBlockingFilter implements WebFilter, Ordered {

	@Value("${spring.constraints.blockedPath.clearTodos}")
	private String BLOCKED_PATH1;

	@Value("${spring.constraints.blockedPath.createToken}")
	private String BLOCKED_PATH2;

	@Value("${spring.constraints.userMicroserviceIdentifier}")
	private String USER_MICROSERVICE_IDENTIFIER;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		String requestUri = request.getPath().toString();
		HttpHeaders headers = request.getHeaders();

		String headerName = "Authorization";

		// Block the "clear-todos" endpoint
		if (requestUri.contains(BLOCKED_PATH1)) {
			response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
			return response.setComplete();
		}

		// Allowing the "create-token" endpoint only for the user microservice
		if (request.getMethod() == HttpMethod.POST && requestUri.contains(BLOCKED_PATH2)) {
			String headerValue = headers.getFirst(headerName);
			if (headerValue == null || !headerValue.equals(USER_MICROSERVICE_IDENTIFIER)) {
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
