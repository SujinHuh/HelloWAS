package sujin.dev.mem.infra.repo.impl;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

@RequiredArgsConstructor
public class MemRepository implements DataRepository<MemberEntity> {

    private final List<MemberEntity> members;
    @Override
    public MemberEntity findById(long id) {
        return members.stream().filter(m -> m.getId() == id).findFirst().orElseThrow();
    }

    @Override
    public List<MemberEntity> findAll() {
        return this.members;
    }

    @Override
    public void deleteById(long id) {
        this.members.removeIf(m -> m.getId() == id);
    }

    @Override
    public void update(MemberEntity memberEntity) {
        this.members.stream().filter(m -> m.getId() == memberEntity.getId()).forEach(
                memberEntity1 -> memberEntity1 = memberEntity
        );
    }

    @Override
    public void insert(MemberEntity memberEntity) {
        this.members.add(memberEntity);
    }
}
