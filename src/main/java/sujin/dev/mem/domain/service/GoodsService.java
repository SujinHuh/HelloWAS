package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.infra.repo.DataRepository;
import sujin.dev.mem.infra.repo.impl.GoodsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface GoodsService {
    void registerGoods(GoodsEntity goods);

    List<GoodsDTO> getGoods();

    GoodsDTO mapToDTO(GoodsEntity goods);
    GoodsEntity findGoodsEntityByName (String selectGoods);
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
            //1. repository 상품 엔티티 목록 가져오기.
            List<GoodsEntity> goodsEntity = repository.findAll();

            //2. 상품엔티티 목록을 GoodsDTO로 변환
            List<GoodsDTO> goodsDTO = goodsEntity.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            //3. 변환된 GoodsDTO목록 반환
            return goodsDTO;
        }

        @Override
        public GoodsDTO mapToDTO(GoodsEntity goods) {
            if(goods == null){
                return null;
            }
            return GoodsEntity.builder()
                    .name(goods.getName())
                    .currentValue(goods.getCurrentValue())
                    .stockQuantity(goods.getStockQuantity())
                    .build().toDTO();
        }

        @Override
        public GoodsEntity findGoodsEntityByName(String name) {
            if (repository instanceof GoodsRepository) {
                GoodsRepository goodsRepository = (GoodsRepository) repository;
                return goodsRepository.findGoodsEntityByName(name);
            } else {
                throw new IllegalStateException("Repository is not an instance of GoodsRepository");
            }
        }

    }
}
