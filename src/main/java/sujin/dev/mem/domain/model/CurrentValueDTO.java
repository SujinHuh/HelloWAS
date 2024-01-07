package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CurrentValueEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class CurrentValueDTO {
    private BigDecimal amount;
    private Currency currency;
    public static CurrentValueDTO fromEntity(CurrentValueEntity currentValue) {
        if (currentValue == null) {
            return null;
        }

        return CurrentValueDTO.builder()
                .amount(currentValue.getAmount())
                .currency(currentValue.getCurrency())
                .build();
    }
}
