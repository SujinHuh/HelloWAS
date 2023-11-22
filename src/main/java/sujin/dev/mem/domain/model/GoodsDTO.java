package sujin.dev.mem.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {

//    private Long id;
    private String name;
    private CurrentValueEntity currentValue;
    private int stockQuantity;

    // Inner class
    @Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class CurrentValueEntity {
        private BigDecimal amount;
        private Currency currency;
    }
}
