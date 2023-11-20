package sujin.dev.mem.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class CurrentValueDTO {

    private BigDecimal amount;

    private Currency currency;
}
