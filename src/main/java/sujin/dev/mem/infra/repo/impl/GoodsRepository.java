package sujin.dev.mem.infra.repo.impl;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

@RequiredArgsConstructor
public class GoodsRepository implements DataRepository<GoodsEntity> {

    // 생성자를 통해 주입 받은 goods 리스트
    private final List<GoodsEntity> goods;
    @Override
    public GoodsEntity findById(long id) {
        return goods.stream()
                .filter( g-> g.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<GoodsEntity> findAll() {
        return this.goods;
    }

    @Override
    public void deleteById(long id) {
        this.goods.removeIf(g -> g.getId() == id);
    }

    @Override
    public void update(GoodsEntity goods) {
        this.goods.stream().filter(g -> g.getId() == goods.getId()).forEach(
                goods1 -> goods1 = goods
        );
    }

    @Override
    public void insert(GoodsEntity goods) {
        this.goods.add(goods);
    }

    public GoodsEntity findGoodsEntityByName(String name){
        return goods.stream()
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}
