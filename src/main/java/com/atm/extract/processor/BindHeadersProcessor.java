package com.atm.extract.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * header를 구성하는 프로세서
 */
@Component(BindHeadersProcessor.NAME)
public class BindHeadersProcessor implements Processor {

    public static final String NAME = "BindHeadersProcessor";

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getMessage().setHeader("txId",exchange.getExchangeId());
        exchange.getMessage().setHeader("txTypeCode","C");
        exchange.getMessage().setHeader("timestamp",System.currentTimeMillis());
        exchange.getMessage().setHeader("systemCode","ATFMS");
        exchange.getMessage().setHeader("interfaceId","ATFMS-0001");
    }
}
