package com.maksimpegov.apigateway;

import com.maksimpegov.apigateway.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {
	private final JwtService jwtService;

	@Autowired
	public GatewayService(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	public String createAuthenticationToken(String userId) {
		return jwtService.generateToken(userId);
	}
}
