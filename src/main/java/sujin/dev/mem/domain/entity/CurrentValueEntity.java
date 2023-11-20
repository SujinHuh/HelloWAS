package sujin.dev.mem.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter
public class CurrentValueEntity {

    private BigDecimal amount;

    private Currency currency;
}
