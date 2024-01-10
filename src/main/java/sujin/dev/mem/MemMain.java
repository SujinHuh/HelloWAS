package sujin.dev.mem;

import lombok.extern.slf4j.Slf4j;
import sujin.dev.mem.controller.RestController;
import sujin.dev.mem.domain.model.CurrentValueDTO;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.domain.service.*;
import sujin.dev.mem.infra.repo.impl.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Scanner;

@Slf4j
public class MemMain {

    // 전역 변수로 currentLoggedInMember 선언
    private static MemberDTO currentLoggedInMember = null;

    public static void main(String[] args) {

        // MemberService 생성 및 초기화
        // - MemberServiceImpl은 MemberService의 구현체이며, MemRepository를 의존성 주입받음
        // - MemRepository는 ArrayList를 사용한 메모리 기반의 더미 데이터 저장소를 가짐
        MemberService memberService = new MemberService.MemberServiceImpl(new MemRepository(new ArrayList<>()));
        GoodsService goodsService = new GoodsService.GoodsServiceImpl(new GoodsRepository(new ArrayList<>()));
        OrderService orderService = new OrderService.OrderServiceImpl(new OrderRepository(new ArrayList<>()));

        CartGoodsService cartGoodsService = new CartGoodsService.CartGoodsServiceImple(new CartGoodsRepository(new ArrayList<>()),memberService);
        // CartService 생성 시에 필요한 의존성 주입
        CartService cartService = new CartService.CartServiceImple(new CartRepository(new ArrayList<>()), goodsService, memberService);

        // RestController 생성 및 초기화
        RestController restController = new RestController(memberService, cartService, goodsService,cartGoodsService,orderService);


        // Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);
        boolean isProgramRunning = true;

        while (isProgramRunning) {
            System.out.println();
            System.out.println("=== CLI 프로그램 ===");
            System.out.println("1. 회원 가입");
            System.out.println("2. 로그인");
            System.out.println("3. 상품 등록");
            System.out.println("4. 상품 조회");
            System.out.println("5. 장바구니");
            System.out.println("6. 주문하기");

            System.out.print("원하는 기능을 선택하세요 (1, 2, 또는 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Enter 키를 소진하기 위한 추가 코드

            switch (choice) {
                case 1:
                    // 회원 가입
                    System.out.print("이름을 입력하세요: ");
                    String name = scanner.nextLine();

                    System.out.print("아이디 입력하세요: ");
                    String userName = scanner.nextLine();

                    System.out.print("비밀번호을 입력하세요: ");
                    String password = scanner.nextLine();

                    System.out.print("주소을 입력하세요: ");
                    String address = scanner.nextLine();

                    System.out.print("전화번호를 입력하세요: ");
                    String phone = scanner.nextLine();

                    MemberDTO member = MemberDTO.builder()
                            .name(name)
                            .memberId(userName)
                            .password(password)
                            .phone(phone)
                            .address(address)
                            .build();

                    restController.registerMember(member);

                    System.out.println();
                    break;
                case 2:
                    // 로그인 로직
                    System.out.print("아이디를 입력하세요: ");
                    String memberId = scanner.nextLine();

                    System.out.print("비밀번호를 입력하세요: ");
                    String memberPassword = scanner.nextLine();

                    MemberDTO memberLogin = restController.login(memberId, memberPassword);
                    if (memberLogin != null) {
                        currentLoggedInMember = memberLogin;
                        System.out.println(memberLogin.getName() + "님, 환영합니다!");
                    } else {
                        System.out.println("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
                    }
                    break;
                case 3:
                    System.out.print("상품 이름을 입력하세요: ");
                    String goodsName = scanner.nextLine();

                    System.out.print("상품 가격을 입력하세요: ");
                    BigDecimal goodsAmount = scanner.nextBigDecimal();
                    scanner.nextLine();

                    System.out.print("상품 재고 수량을 입력하세요: ");
                    int stockQuantity = scanner.nextInt();
                    scanner.nextLine();

                    // 상품 가격 정보를 담는 CurrentValueDTO 객체 생성
                    CurrentValueDTO currentValue = CurrentValueDTO.builder()
                            .amount(goodsAmount)
                            .currency(Currency.getInstance("KRW")) // 기본 통화 USD
                            .build();

                    // GoodsDTO 객체 생성
                    GoodsDTO goods = GoodsDTO.builder()
                            .name(goodsName)
                            .currentValue(currentValue)
                            .stockQuantity(stockQuantity)
                            .build();

                    restController.registerGoods(goods);
                    System.out.println();
                    System.out.println("상품 등록이 완료되었습니다.");
                    break;
                case 4:
                    System.out.println("상품 목록 조회");
                    System.out.println();

                    restController.getGoodsList();
                    break;
                case 5:
                    if (currentLoggedInMember == null) {
                        System.out.println("로그인이 필요한 기능입니다.");
                        break;
                    }
                    System.out.println("장바구니 목록 조회");
                    restController.getCartList(currentLoggedInMember);
                    break;

                case 6:
                    if (currentLoggedInMember == null) {
                        System.out.println("로그인이 필요한 기능입니다.");
                        break;
                    }
                    System.out.println("주문하기");
                    restController.placeOrder(currentLoggedInMember);
                    break;

                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }

        scanner.close();
        System.out.println("====== 프로그램이 종료됩니다. ======");
    }
}
