package com.atm.extract.route;

import com.atm.extract.processor.BindHeadersProcessor;
import com.atm.extract.processor.ErrorMessageSettingProcessor;
import com.atm.extract.processor.QualityVerificationProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
public class ExtractRoute extends EndpointRouteBuilder {

    public static final String ROUTE_ID = "atm-extract";

    @Override
    public void configure() throws Exception{
        from(jetty("http://localhost:8081/atm/intf1")).routeId(ROUTE_ID)
        .unmarshal().json()
        .process(QualityVerificationProcessor.NAME)
        .process(BindHeadersProcessor.NAME)
        .filter(this :: isErrorMessage)
            .process(ErrorMessageSettingProcessor.NAME)
            .log("http status error : ${headers.errorMessage}")
        .end()
        .marshal().json()
        //.to(kafka("ATM-COLLECT-001"))
        .log("send to collect");
    }

    private boolean isErrorMessage(Exchange exchange) {
        return !StringUtils.equals((CharSequence) exchange.getMessage().getHeader("statusCode"), "200");
	}
}

