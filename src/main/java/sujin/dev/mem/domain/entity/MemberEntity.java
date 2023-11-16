package sujin.dev.mem.domain.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.MemberDTO;

@Getter @Setter @Builder
public class MemberEntity {
    private long id;
    private String name;
    private String phone;

    public static MemberEntity of(MemberEntityBuilder memberEntityBuilder) {
        return memberEntityBuilder.build();
    }

    public static MemberEntity toEntity(MemberDTO member) {
        return of(MemberEntity.builder().name(member.getName()));
    }
}
