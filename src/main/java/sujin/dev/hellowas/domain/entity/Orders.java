package sujin.dev.hellowas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sujin.dev.hellowas.model.OrderDTO;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity{

    @Id
    private Long id;

    @OneToMany(mappedBy = "orders")
    private List<Cart> carts;
    @ManyToOne
    private Person person;
    @OneToOne
    private Goods goods;

    private int stockQuantity;

    public int getTotalCarts(){
        return this.carts.size();
    }

    public void addCart(Cart cart) {
        this.carts.add(cart);
        cart.setOrders(this);
    }

    private Orders(OrderDTO orderDTO) {
        this.carts = orderDTO.getCarts();
        this.person = orderDTO.getPerson();
        this.goods = orderDTO.getGoods();
        this.stockQuantity = orderDTO.getStockQuantity();
    }
    public static Orders createOrders(OrderDTO orderDTO){
        return new Orders(orderDTO);
    }
}
