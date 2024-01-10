package sujin.dev.mem.infra.repo.impl;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MemRepository implements DataRepository<MemberEntity> {

    // 생성자를 통해 주입받은 members 리스트
    private final List<MemberEntity> members;

    // id를 사용하여 특정 회원을 찾아 반환하는 메서드
    @Override
    public MemberEntity findById(long id) {
        return members.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null); // 또는 Optional.empty()를 사용하려면
    }

    // 현재 저장된 모든 회원 목록을 반환하는 메서드
    @Override
    public List<MemberEntity> findAll() {
        return this.members;
    }

    // id를 사용하여 특정 회원을 삭제하는 메서드
    @Override
    public void deleteById(long id) {
        this.members.removeIf(m -> m.getId() == id);
    }

    // 주어진 회원 엔티티로 회원 정보를 업데이트하는 메서드
    @Override
    public void update(MemberEntity memberEntity) {
        this.members.stream().filter(m -> m.getId() == memberEntity.getId()).forEach(
                memberEntity1 -> memberEntity1 = memberEntity
        );
//        this.members.stream()
//                .filter(m -> m.getId() == memberEntity.getId())
//                .forEach(memberEntity1 -> memberEntity1.update(memberEntity));
    }

    // 새로운 회원 엔티티를 저장하는 메서드
    @Override
    public void insert(MemberEntity memberEntity) {
        this.members.add(memberEntity);
    }

    public MemberEntity findByMemberId(String memberId) {
        return members.stream()
                .filter(member -> memberId.equals(member.getMemberId()))
                .findFirst()
                .orElse(null);
    }
}
