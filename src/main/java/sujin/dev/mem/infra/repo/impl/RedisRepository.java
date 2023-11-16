package sujin.dev.mem.infra.repo.impl;

import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

public class RedisRepository implements DataRepository<MemberEntity> {
    @Override
    public MemberEntity findById(long id) {
        return null;
    }

    @Override
    public List<MemberEntity> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(MemberEntity memberEntity) {

    }

    @Override
    public void insert(MemberEntity memberEntity) {

    }
}
