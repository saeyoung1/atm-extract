package com.atm.extract.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * 연계결과를 나타내는 http status 값을 입력하는 프로세서
 */
@Component(QualityVerificationProcessor.NAME)
public class QualityVerificationProcessor implements Processor {

    public static final String NAME = "QualityVerificationProcessor";

    @Override
    public void process(Exchange exchange) throws Exception {
        String className = exchange.getMessage().getBody().getClass().getSimpleName();
        String statusCode = "200";
        String errorMessage = null;

        if(!className.contains("Map")){
             statusCode = "412";
             errorMessage = "데이터 형식 관련 오류";
        }

        exchange.getMessage().setHeader("statusCode",statusCode);
        exchange.getMessage().setHeader("errorMessage",errorMessage);
    }
}
