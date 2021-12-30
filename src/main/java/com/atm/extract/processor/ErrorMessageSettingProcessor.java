package com.atm.extract.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  errorMessage를 body에 담는 프로세서
 */
@Component(ErrorMessageSettingProcessor.NAME)
public class ErrorMessageSettingProcessor implements Processor {

    public static final String NAME = "ErrorMessageSettingProcessor";

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<?,?> headers = exchange.getMessage().getHeaders();
        String bodyString = exchange.getMessage().getBody(String.class);

        Map<String, String> body = new HashMap<>();
        body.put("statusCode", (String) headers.get("statusCode"));
        body.put("errorMessage", (String) headers.get("errorMessage"));
        body.put("data",bodyString);
        exchange.getMessage().setBody(body);
    }
}
