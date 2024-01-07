package sujin.dev.mem.domain.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class CurrentValueEntity {

    private BigDecimal amount;
    private Currency currency;
}
