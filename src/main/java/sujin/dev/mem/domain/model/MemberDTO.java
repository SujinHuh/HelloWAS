package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.MemberEntity;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class MemberDTO {
//    private long id;
    private String name;
    private String phone;

}
