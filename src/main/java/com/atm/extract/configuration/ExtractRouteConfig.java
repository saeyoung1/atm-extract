package com.atm.extract.configuration;

import com.atm.extract.route.ExtractRoute;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ExtractRouteConfig {

	@Autowired
	private CamelContext context;
	
	@PostConstruct
	public void addRoutes() throws Exception {
		long[] ports = { 8083, 8084 };
		for (long port : ports) {
			context.addRoutes(new ExtractRoute(port));
		}
	}
	
}
