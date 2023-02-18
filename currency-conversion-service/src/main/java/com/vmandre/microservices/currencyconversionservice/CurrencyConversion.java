package com.vmandre.microservices.currencyconversionservice;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CurrencyConversion {
    private Long id;
    @NonNull
    private String from;
    @NonNull
    private String to;
    private BigDecimal conversionMultiple;
    @NonNull
    private BigDecimal quantity;
    private BigDecimal totalCalculatedAmount;
    @NonNull
    private String environment;
}
