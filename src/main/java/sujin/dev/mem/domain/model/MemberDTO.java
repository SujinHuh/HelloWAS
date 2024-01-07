package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.MemberEntity;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class MemberDTO {
//    private long id;
    private String memberId; // 사용자 이름 또는 이메일
    private String name;     // 실제 이름 또는 별명
    private String password; // 비밀번호
    private String phone;
    private String address;

    public static MemberDTO fromEntity(MemberEntity member) {
        return member == null ? null : MemberDTO.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .build();
    }
}
