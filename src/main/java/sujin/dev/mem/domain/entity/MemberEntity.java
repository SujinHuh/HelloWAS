package sujin.dev.mem.domain.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sujin.dev.mem.domain.model.MemberDTO;

@Getter @Setter @ToString @Builder
public class MemberEntity {

    private long id;

    private String name;

    private String phone;

    public static MemberEntity toEntity(MemberDTO member) {
        return MemberEntity.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }
}
