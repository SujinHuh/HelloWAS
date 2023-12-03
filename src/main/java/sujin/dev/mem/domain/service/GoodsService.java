package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;
import java.util.Optional;

public interface GoodsService {
    void registerGoods(GoodsEntity goods);

    List<sujin.dev.mem.domain.model.GoodsDTO> getGoods();
    sujin.dev.mem.domain.model.GoodsDTO mapToDTO(GoodsEntity goods);
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
        public sujin.dev.mem.domain.model.GoodsDTO mapToDTO(GoodsEntity goods) {
            if (goods == null) {
                return null;
            }

            return sujin.dev.mem.domain.model.GoodsDTO.builder()
                    .name(goods.getName())
                    .currentValue(sujin.dev.mem.domain.model.GoodsDTO.CurrentValueEntity.builder()
                            .amount(goods.getCurrentValue().getAmount())
                            .currency(goods.getCurrentValue().getCurrency())
                            .build())
                    .stockQuantity(goods.getStockQuantity())
                    .build();
        }

        @Override
        public List<sujin.dev.mem.domain.model.GoodsDTO> getGoods() {
            return this.repository.findAll().stream()
                    .map(this::convertToDTO)
                    .toList();
        }


        private sujin.dev.mem.domain.model.GoodsDTO convertToDTO(GoodsEntity goodsEntity) {
                if (goodsEntity == null) {
                    return null;
                }
            return sujin.dev.mem.domain.model.GoodsDTO.builder()
                    .name(goodsEntity.getName())
                    .currentValue(sujin.dev.mem.domain.model.GoodsDTO.CurrentValueEntity.builder()
                            .amount(goodsEntity.getCurrentValue().getAmount())
                            .currency(goodsEntity.getCurrentValue().getCurrency())
                            .build())
                    .stockQuantity(goodsEntity.getStockQuantity())
                    .build();
        }

    }
}
