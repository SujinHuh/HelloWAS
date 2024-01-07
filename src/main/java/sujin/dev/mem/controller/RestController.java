package sujin.dev.mem.controller;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.*;
import sujin.dev.mem.domain.service.*;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
public class RestController {

    private final MemberService memberService;
    private final CartService cartService;
    private final GoodsService goodsService;
    private final CartGoodsService cartGoodsService;
    private final OrderService orderService;

    Scanner scanner = new Scanner(System.in);

    public void registerGoods(GoodsDTO goodsDTO) {
        // DTO to Entity 변환
        GoodsEntity goodsEntity = GoodsEntity.toEntity(goodsDTO);

        goodsService.registerGoods(goodsEntity);

        System.out.println();
        System.out.println("======= 상품 등록 완료 =======");
        System.out.println(String.format("%s 상품 등록 완료되었습니다.", goodsDTO.getName()));
    }


    // 회원목록
    public List<MemberDTO> getmemberList() {

        List<MemberDTO> members = memberService.getMembers();

        System.out.println("=== 회원목록 === ");

        if (members == null || members.isEmpty()) {
            System.out.println("회원 목록이 없습니다.");
            return members; // 여기서 빈 리스트 또는 null을 반환합니다.
        }
        for (MemberDTO member : members) {
            System.out.println("ID: " + member.getMemberId() + ", Phone: " + member.getPhone());
        }
        return members;
    }

    public List<GoodsDTO> getGoodsList() {
        List<GoodsDTO> goodsList = goodsService.getGoods();

        if (goodsList == null || goodsList.isEmpty()) {
            System.out.println("상품 목록이 없습니다.");
            return Collections.emptyList();
        }

        // 사용 가능한 상품 목록 표시
        getGoodsList(goodsList);

        return goodsList;
    }


    public void registerMember(MemberDTO memberDTO) {
        // Validation
        if (isNameLength(memberDTO)) {
            throw new IllegalArgumentException("이름은 20자를 초과할 수 없습니다.");
        }

        // DTO to Entity 변환
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);

        // userId 중복 확인
        if(memberService.isUserIdTaken(memberEntity.getMemberId())){
            System.out.println("===== 회원 가입 오류 =====");
            System.out.println("사용할 수 없는 아이디입니다.");
            return;
        }
        // MemberEntity 저장
        memberService.registerMember(memberEntity);

        // 저장 후 ID 확인
        String generatedId = memberEntity.getMemberId();
        System.out.println();
        System.out.println("===== 회원 가입 완료 =====");
        System.out.println(String.format("%s님 (ID: %S) 회원가입을 축하합니다.", memberEntity.getName(), generatedId));
        System.out.println();
        System.out.println("회원가입이 완료되었습니다.");
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


    public void getCartList(MemberDTO member) {
        // 회원 존재 여부 확인
        MemberDTO foundMember = memberService.findByMemberId(member.getMemberId());
        if (foundMember == null) {
            System.out.println("해당 회원을 찾을 수 없습니다.");
            return;
        }

        // 회원 ID로 장바구니 목록 조회
        List<CartDTO> cartList = cartService.getCartList(member);

        // 장바구니 목록이 비어 있는지 확인
        if (cartList.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

        // 장바구니 목록 출력
        System.out.println("=== 장바구니 목록 ===");
        for (CartDTO cart : cartList) {
            System.out.println("총 가격: " + cart.getTotalPrice());
            System.out.println("상품 목록:");

            // 장바구니에 담긴 상품 상세 정보 출력
            for (CartGoodsDTO cartGoods : cart.getCartGoods()) {
                GoodsDTO goods = cartGoods.getGoods();
                System.out.println(" - 상품명: " + goods.getName() +
                        ", 가격: " + goods.getCurrentValue().getAmount() + " " + goods.getCurrentValue().getCurrency() +
                        ", 수량: " + cartGoods.getQuantity());
            }
            System.out.println("-----------");
        }
    }
    public MemberDTO findMemberId(String memberId) {
        // MemberService를 통해 회원 찾기
        MemberDTO foundMember = memberService.findByMemberId(memberId);

        if (foundMember != null) {
            System.out.println("회원이 있습니다. 회원 ID: " + foundMember.getMemberId());
        } else {
            System.out.println("해당하는 회원이 없습니다.");
        }

        return foundMember;
    }

    public void placeOrder(MemberDTO member) {
        // 상품 목록 가져오기
        List<GoodsDTO> goodsList = getGoodsList();

        // 사용 가능한 상품 목록 표시
        getGoodsList(goodsList);

        // 선택한 물건에 대한 입력 받기
        System.out.print("선택할 상품 번호를 입력하세요: ");
        int selectedItemIndex = scanner.nextInt();
        scanner.nextLine();

        // 선택한 상품 번호 유효성 검사
        if (selectedItemIndex < 1 || selectedItemIndex > goodsList.size()) {
            System.out.println("유효하지 않은 상품 번호입니다.");
            return;
        }

        // 선택한 상품 가져오기 및 주문 개수 입력 받기
        GoodsDTO selectedGoods = goodsList.get(selectedItemIndex - 1);
        System.out.print("주문할 개수를 입력하세요: ");
        int orderQuantity = scanner.nextInt();
        scanner.nextLine();

        // 옵션 선택 (장바구니 추가/ 바로 구매)
        System.out.println("1: 장바구니 추가 , 2: 바로 구매");
        System.out.println("번호 선택 : ");
        int option = scanner.nextInt();
        scanner.nextLine();

        if(option == 1) {
            cartService.addToCart(member,selectedGoods, orderQuantity);
            System.out.println("장바구니에 상품이 추가되었습니다.");
            //장바구니에 getCartList로 보여주기? ex) 장바구니 바로 가시겠습니까?
            getCartList(member);
        } else if (option == 2) {
            // 바로 구매 로직
//            OrderDTO order = createNewOrder(member);
//            processOrderItem(selectedGoods, orderQuantity, order);
//            saveOrder(order);
            System.out.println("구매가 완료되었습니다.");
        } else {
            System.out.println("유효하지 않은 옵션입니다.");
        }
    }


    public void getGoodsList(List<GoodsDTO> goodsList) {

        System.out.println("=== 상품목록 === ");

        for (GoodsDTO goods : goodsList) {
            BigDecimal amount = goods.getCurrentValue().getAmount();
            Currency currency = goods.getCurrentValue().getCurrency();

            System.out.println("상품명: " + goods.getName() +
                    ", 상품 가격: " + (amount != null ? amount + " " + currency : "가격 정보 없음") +
                    ", 상품 개수:" + goods.getStockQuantity());
        }

    }
}


