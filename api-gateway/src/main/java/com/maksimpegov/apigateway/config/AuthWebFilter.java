package com.maksimpegov.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthWebFilter implements GlobalFilter {
	private JwtService jwtService;

	@Autowired
	public AuthWebFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		HttpHeaders headers = request.getHeaders();
		String headerName = "Authorization";

		// Allow the request for login and register
		String requestUri = request.getPath().toString();
		if (requestUri.contains("login") || requestUri.contains("register")) {
			return chain.filter(exchange);
		}

		// If the header is missing, abort the request
		if (!request.getHeaders().containsKey(headerName)) {
			response.setStatusCode(HttpStatus.BAD_REQUEST);
			return response.setComplete();
		}

		// Modify an existing header
		String existingHeader = headers.getFirst(headerName);
		if (!jwtService.isTokenValid(existingHeader)) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		String modifiedHeaderValue = jwtService.getUserId(existingHeader);
		headers.set(headerName, modifiedHeaderValue);

		// Continue the filter chain
		return chain.filter(exchange);
	}
}
