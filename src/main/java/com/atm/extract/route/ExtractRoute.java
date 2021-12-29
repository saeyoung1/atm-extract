package com.atm.extract.route;

import com.atm.extract.processor.BindHeadersProcessor;
import com.atm.extract.processor.QualityVerificationProcessor;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExtractRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception{
        from(jetty("http://localhost:8081/atm/intf1"))
        .unmarshal().json()
        .process(QualityVerificationProcessor.NAME)
        .process(BindHeadersProcessor.NAME)
        .process(exchange -> {
           Map<?,?> headers = exchange.getMessage().getHeaders();
           String bodyString = exchange.getMessage().getBody(String.class);

           if(!StringUtils.equals((CharSequence) headers.get("statusCode"),"200")){
               Map<String, String> body = new HashMap<>();
               body.put("statusCode", (String) headers.get("statusCode"));
               body.put("errorMessage", (String) headers.get("errorMessage"));
               body.put("data",bodyString);
               exchange.getMessage().setBody(body);
           }
        })
        .marshal().json()
        .log("finish");
    }
}

