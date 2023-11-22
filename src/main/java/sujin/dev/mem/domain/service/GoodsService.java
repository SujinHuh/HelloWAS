package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

public interface GoodsService {
    void registerGoods(GoodsEntity goods);

    List<GoodsDTO> getGoods();

    @RequiredArgsConstructor // 생성자 자동생성
    class GoodsServiceImpl implements GoodsService {
        private final DataRepository<GoodsEntity> repository;

        public void registerGoods(GoodsEntity goods) {
            try {
                if (goods.getId() != null) {
                    GoodsEntity byId = this.repository.findById(goods.getId());
                    if (byId == null) {
                        this.repository.insert(goods);
                    }
                    this.repository.update(goods);
                } else {
                    // id가 null인 경우에 대한 처리
                    this.repository.insert(goods);
                }
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }
        @Override
        public List<GoodsDTO> getGoods() {
            return this.repository.findAll().stream().map(m ->{
                GoodsDTO goodsDTO = new GoodsDTO();
                goodsDTO.setName(m.getName());
                goodsDTO.setCurrentValue(m.getCurrentValue());
                goodsDTO.setStockQuantity(m.getStockQuantity());
                return goodsDTO;
            }).toList();
        }
    }
}
