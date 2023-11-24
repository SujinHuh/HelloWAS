package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;
import java.util.Optional;

public interface GoodsService {
    void registerGoods(GoodsEntity goods);

    List<GoodsDTO> getGoods();

    @RequiredArgsConstructor // 생성자 자동생성
    class GoodsServiceImpl implements GoodsService {
        private final DataRepository<GoodsEntity> repository;

        public void registerGoods(GoodsEntity goods) {
            try {
                Long goodsId = goods.getId();

                Optional<GoodsEntity> optionalGoods = goodsId != null ? Optional.ofNullable(this.repository.findById(goodsId)) : Optional.empty();

                optionalGoods.or(() -> {
                    this.repository.insert(goods);
                    return Optional.empty();
                }).ifPresent(existingGoods -> this.repository.update(goods));

            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }
        @Override
        public List<GoodsDTO> getGoods() {
            return this.repository.findAll().stream()
                    .map(this::convertToDTO)
                    .toList();
        }

        private GoodsDTO convertToDTO(GoodsEntity goodsEntity) {
            return GoodsDTO.builder()
                    .name(goodsEntity.getName())
                    .currentValue(GoodsDTO.CurrentValueEntity.builder()
                            .amount(goodsEntity.getCurrentValue().getAmount())
                            .currency(goodsEntity.getCurrentValue().getCurrency())
                            .build())
                    .stockQuantity(goodsEntity.getStockQuantity())
                    .build();
        }
    }
}
