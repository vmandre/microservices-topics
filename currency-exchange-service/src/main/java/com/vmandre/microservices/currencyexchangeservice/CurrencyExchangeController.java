package com.vmandre.microservices.currencyexchangeservice;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@RestController
public class CurrencyExchangeController {

    private Environment environment;

    private CurrencyExchangeRepository currencyExchangeRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        String port = environment.getProperty("local.server.port");
//        CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50), port);

        log.info("retrieveExchangeValue called with the parameters {} to {}", from , to);

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

        if (Objects.isNull(currencyExchange)) {
            throw new RuntimeException(String.format("Unable to find data for %s to %s", from, to));
        }

        currencyExchange.setEnvironment(environment.getProperty("server.port"));

        return currencyExchange;

    }
}
