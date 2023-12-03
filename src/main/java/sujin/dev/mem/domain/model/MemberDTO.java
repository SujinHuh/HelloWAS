package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.MemberEntity;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class MemberDTO {
//    private long id;
    private String name;
    private String phone;
    public static MemberEntity toEntity(MemberDTO memberDTO) {
        return MemberEntity.builder()
                .name(memberDTO.getName())
                .phone(memberDTO.getPhone())
                .build();
    }

    public static MemberDTO fromEntity(MemberEntity memberEntity) {
        return MemberDTO.builder()
                .name(memberEntity.getName())
                .phone(memberEntity.getPhone())
                .build();
    }
}
