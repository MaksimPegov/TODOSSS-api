package com.maksimpegov.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
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

		// If the header value is not a valid JWT, abort the request
		String existingHeader = headers.getFirst(headerName);
		if (!jwtService.isTokenValid(existingHeader)) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		// Change JWT header value to user id
		String modifiedHeaderValue = jwtService.getUserId(existingHeader);

		// Create a new HttpHeaders object with the modified header value
		HttpHeaders modifiedHeaders = new HttpHeaders();
		modifiedHeaders.putAll(headers);
		modifiedHeaders.set(headerName, modifiedHeaderValue);

		// Create a new ServerHttpRequest with the modified headers
		ServerHttpRequest modifiedRequest = request.mutate().headers(httpHeaders -> httpHeaders.addAll(modifiedHeaders)).build();

		// Continue the filter chain with the modified request
		return chain.filter(exchange.mutate().request(modifiedRequest).build());
	}
}
