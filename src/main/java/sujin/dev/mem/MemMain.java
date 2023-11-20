package sujin.dev.mem;

import lombok.extern.slf4j.Slf4j;
import sujin.dev.mem.controller.RestController;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.domain.service.CartService;
import sujin.dev.mem.domain.service.GoodsService;
import sujin.dev.mem.domain.service.MemberService;
import sujin.dev.mem.infra.repo.impl.CartRepository;
import sujin.dev.mem.infra.repo.impl.GoodsRepository;
import sujin.dev.mem.infra.repo.impl.MemRepository;

import java.util.ArrayList;

@Slf4j
public class MemMain {

    public static void main(String[] args) {

        // MemberService 생성 및 초기화
        // - MemberServiceImpl은 MemberService의 구현체이며, MemRepository를 의존성 주입받음
        // - MemRepository는 ArrayList를 사용한 메모리 기반의 더미 데이터 저장소를 가짐
        MemberService memberService = new MemberService.MemberServiceImpl(new MemRepository(new ArrayList<>()));
        CartService cartService = new CartService.CartServiceImple(new CartRepository(new ArrayList<>()));
        GoodsService goodsService = new GoodsService.GoodsServiceImpl(new GoodsRepository(new ArrayList<>()));


        // RestController 생성 및 초기화
        // - RestController는 MemberService를 의존성 주입받음
        RestController restController = new RestController(memberService, cartService, goodsService);

        // MemberDTO 생성
        // - MemberDTO.builder()를 통해 빌더 패턴 활용
        MemberDTO memberDTO = MemberDTO.builder()
                .name("dssdfs")
                .phone("123123").build();

        // RestController의 registerMember 메서드 호출
        // - 주어진 MemberDTO를 사용하여 새로운 회원 등록을 시도
        restController.registerMember(memberDTO);

        log.info(memberDTO.toString());
        // 프로그램이 종료됨
    }
}
