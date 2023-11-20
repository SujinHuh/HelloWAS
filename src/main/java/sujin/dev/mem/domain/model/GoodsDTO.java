package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CurrentValueEntity;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {

//    private Long id;

    private String name;

    private CurrentValueEntity currentValue;

    private int stockQuantity;
}
