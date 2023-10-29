package sujin.dev.hellowas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "tb_goods")
public class Goods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private CurrentValue price = new CurrentValue(BigDecimal.valueOf(0), Currency.getInstance("KRW"));

    private int stockQuantity;

    public int  decrementStockQuantity(int quantity) {
        if(stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다. ");
        }
        stockQuantity -= quantity;
        return stockQuantity;
    }

}
