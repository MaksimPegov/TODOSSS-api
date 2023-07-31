package com.maksimpegov.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class GatewayController {
	private final GatewayService gatewayService;

	@Autowired
	public GatewayController(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

	@PostMapping("/{userId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String createAuthenticationToken(@PathVariable String userId) {
		return gatewayService.createAuthenticationToken(userId);
	}
}
