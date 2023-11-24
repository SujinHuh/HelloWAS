package sujin.dev.mem;

import lombok.extern.slf4j.Slf4j;
import sujin.dev.mem.controller.RestController;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.domain.service.CartService;
import sujin.dev.mem.domain.service.GoodsService;
import sujin.dev.mem.domain.service.MemberService;
import sujin.dev.mem.infra.repo.impl.CartRepository;
import sujin.dev.mem.infra.repo.impl.GoodsRepository;
import sujin.dev.mem.infra.repo.impl.MemRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Scanner;

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
//        MemberDTO memberDTO = MemberDTO.builder()
//                .name("수진_94")
//                .phone("123123").build();


        // Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);
        boolean isProgramRunning = true;

        while (isProgramRunning) {
            System.out.println();
            System.out.println("=== CLI 프로그램 ===");
            System.out.println("1. 회원 가입");
            System.out.println("2. 회원 목록 조회");
            System.out.println("3. 상품 등록");
            System.out.println("4. 상품 조회");

            System.out.print("원하는 기능을 선택하세요 (1, 2, 또는 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Enter 키를 소진하기 위한 추가 코드

            switch (choice) {
                case 1:
                    // 회원 가입
                    System.out.print("이름을 입력하세요: ");
                    String name = scanner.nextLine();

                    System.out.print("전화번호를 입력하세요: ");
                    String phone = scanner.nextLine();

                    MemberDTO memberDTO = MemberDTO.builder()
                            .name(name)
                            .phone(phone)
                            .build();

                    restController.registerMember(memberDTO);
                    System.out.println();
                    System.out.println("회원가입이 완료되었습니다.");
                    break;
                case 2:
                    System.out.println("회원 목록 조회");
                    System.out.println();

                    restController.getmemberList();

                    break;
                case 3:
                    System.out.print("상품 이름을 입력하세요: ");
                    String goodsName = scanner.nextLine();

                    System.out.print("상품 가격을 입력하세요: ");
                    BigDecimal goodsAmount = scanner.nextBigDecimal();
                    scanner.nextLine(); // Enter 키 소진

                    System.out.print("상품 재고 수량을 입력하세요: ");
                    int stockQuantity = scanner.nextInt();
                    scanner.nextLine(); // Enter 키 소진

                    GoodsDTO goodsDTO = GoodsDTO.builder()
                            .name(goodsName)
                            .currentValue(GoodsDTO.CurrentValueEntity.builder()
                                    .amount(goodsAmount)
                                    .currency(Currency.getInstance("KRW"))
                                    .build())
                            .stockQuantity(stockQuantity)
                            .build();

                    restController.registerGoods(goodsDTO);
                    System.out.println();
                    System.out.println("상품 등록이 완료되었습니다.");
                    break;
                case 4:
                    System.out.println("상품 목록 조회");
                    System.out.println();

                    restController.getGoodsList();

                    break;


                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }

        scanner.close();
        System.out.println("====== 프로그램이 종료됩니다. ======");
    }
}
