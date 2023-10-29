package sujin.dev.hellowas.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.hellowas.domain.entity.Cart;
import sujin.dev.hellowas.domain.entity.Goods;
import sujin.dev.hellowas.domain.entity.Person;

import java.util.List;

@Getter @Setter @Builder
public class OrderDTO {
    private List<Cart> carts;
    private Person person;
    private Goods goods;
    private int stockQuantity;
}
