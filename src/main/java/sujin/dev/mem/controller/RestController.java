package sujin.dev.mem.controller;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;
import sujin.dev.mem.domain.model.*;
import sujin.dev.mem.domain.service.CartService;
import sujin.dev.mem.domain.service.GoodsService;
import sujin.dev.mem.domain.service.MemberService;
import sujin.dev.mem.domain.service.OrderService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class RestController {

    private final MemberService memberService;
    private final CartService cartService;
    private final GoodsService goodsService;

    private final OrderService orderService;
    Scanner scanner = new Scanner(System.in);

    public void registerGoods(GoodsDTO goods) {
        //goods
        GoodsEntity goodsEntity = GoodsEntity.toEntity(goods);
        goodsService.registerGoods(goodsEntity);
        System.out.println();
        System.out.println("======= 상품 등록 완료 =======");
        System.out.println(String.format("%s 상품 등록 완료되었습니다.", goods.getName()));
    }


    // 회원목록
    public List<MemberDTO> getmemberList() {

        List<MemberDTO> members = memberService.getMembers();

        if (members == null) {
            throw new IllegalArgumentException("회원 목록이 없습니다.");
        }
        System.out.println("=== 회원목록 === ");
        for (MemberDTO member : members) {
            System.out.println("Name: " + member.getName() + ", Phone: " + member.getPhone());
        }
        return members;
    }

    public List<GoodsDTO> getGoodsList() {
        List<GoodsDTO> goodsList = goodsService.getGoods();

        if (goodsList == null || goodsList.isEmpty()) {
            System.out.println("상품 목록이 없습니다.");
            return Collections.emptyList();
        }

        System.out.println("=== 상품목록 === ");

        for (GoodsDTO goods : goodsList) {
            BigDecimal amount = goods.getCurrentValue().getAmount();
            Currency currency = goods.getCurrentValue().getCurrency();

            System.out.println("상품명: " + goods.getName() +
                    ", 상품 가격: " + (amount != null ? amount + " " + currency : "가격 정보 없음") +
                    ", 상품 개수:" + goods.getStockQuantity());
        }

        return goodsList;
    }


    public void registerMember(MemberDTO member) {
        // validation
        if (isNameLength(member)) {
            throw new IllegalArgumentException("이름은 20자를 초과할 수 없습니다.");
        }
        MemberEntity entity = MemberEntity.toEntity(member);
        memberService.registerMember(entity);

        System.out.println();
        System.out.println("===== 회원 가입 완료 =====");
        System.out.println(String.format("%s님 회원가입을 축하합니다.", member.getName()));
    }

    public ResponseResult<List<MemberDTO>> getMember(MemberDTO memberDTO) {
//        ResponseResult responseResult = new ResponseResult();
//        responseResult.setBody(service.getMembers());
//        responseResult.setCode(200);
//        responseResult.setMsg("success");
        // return responseResult;

        if (!memberDTO.getName().contains("KIM")) {
            return new ResponseResult<>
                    (501, "KIM Validation Fail", null);
        }

        return new ResponseResult<>
                (200, "success", memberService.getMembers());

    }

    private static boolean isNameLength(MemberDTO member) {
        return member.getName().length() > 20;
    }


    public void getCartList() {
        List<CartDTO> cartList = cartService.getCartList();

        if (cartList != null && !cartList.isEmpty()) {
            System.out.println("=== 장바구니 목록 === ");

            for (CartDTO cart : cartList) {
                List<GoodsEntity> goodsList = cart.getGoodsList();
                MemberEntity member = cart.getMember();

                if (goodsList != null && !goodsList.isEmpty()) {
                    for (GoodsEntity goods : goodsList) {
                        System.out.println("상품명: " + goods.getName() +
                                ", 상품 가격: " + goods.getCurrentValue().getAmount() + " " + goods.getCurrentValue().getCurrency() +
                                ", 상품 개수: " + goods.getStockQuantity() +
                                ", 회원명: " + member.getName() +
                                ", 전화번호: " + member.getPhone());
                    }
                } else {
                    System.out.println("장바구니에 상품이 없습니다.");
                }
            }
        } else {
            System.out.println("장바구니가 비어 있습니다.");
        }
    }

    public MemberDTO findMemberByName(String memberName) {
        // MemberService를 통해 회원 찾기
        MemberDTO foundMember = memberService.findMemberByName(memberName);

        if (foundMember != null) {
            System.out.println("회원이 있습니다. 회원 이름: " + foundMember.getName());
        } else {
            System.out.println("해당하는 회원이 없습니다.");
        }

        return foundMember;
    }

    public void placeOrder(MemberDTO member) {
        // 상품 목록 가져오기
        List<GoodsDTO> goodsList = getGoodsList();

        // 사용 가능한 상품 목록 표시
        System.out.println("=== 상품 목록 ===");
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsDTO goods = goodsList.get(i);
            System.out.println((i + 1) + ". " + goods.getName());
        }

        // 선택한 물건에 대한 입력 받기
        System.out.print("선택할 상품 번호를 입력하세요: ");
        int selectedItemIndex = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비

        // 선택한 상품 번호 유효성 검사
        if (selectedItemIndex < 1 || selectedItemIndex > goodsList.size()) {
            System.out.println("유효하지 않은 상품 번호입니다.");
            return; // 입력이 유효하지 않으면 메서드 종료
        }

        // 선택한 상품 가져오기 및 주문 개수 입력 받기
        GoodsDTO selectedGoods = goodsList.get(selectedItemIndex - 1);
        System.out.print("주문할 개수를 입력하세요: ");
        int orderQuantity = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비

        // 주문DTO를 선택한 상품과 주문 개수로 업데이트
        OrderDTO orderDTO = OrderDTO.builder()
                .member(MemberEntity.toEntity(member))
                .goods((GoodsEntity) goodsList)
                .stockQuantity(orderQuantity)
                // 기타 주문과 관련된 정보 추가
                .build();

        // 주문 서비스 호출
        orderService.registerOrder(OrdersEntity.toEntity(orderDTO));

        // 주문이 완료되면 장바구니 비우기
        cartService.clearCart(member);

        // 기타 주문과 관련된 로직 추가 가능
        System.out.println("주문이 완료되었습니다.");
    }
}


