package sujin.dev.mem.domain.entity;

import lombok.*;
import sujin.dev.mem.domain.model.MemberDTO;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberEntity {

    private Long id;        // 고유 식별자
    private String memberId; // 사용자 이름 또는 이메일
    private String name;     // 실제 이름 또는 별명
    private String password; // 비밀번호
    private String phone;
    private String address;

    public static MemberEntity toEntity(MemberDTO member) {
        return MemberEntity.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .password(member.getPassword())
                .phone(member.getPhone())
                .address(member.getAddress())
                .build();
    }
}
