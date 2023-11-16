package sujin.dev.mem.controller;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.domain.model.ResponseResult;
import sujin.dev.mem.domain.service.CartService;
import sujin.dev.mem.domain.service.MemberService;

import java.util.List;

import static sujin.dev.mem.domain.model.ResponseResult.builder;

@RequiredArgsConstructor
public class RestController {

    private final MemberService service;
    private final CartService cartService;


    private void registerCart(CartDTO cart) {

        // cart
        if(cart.getGoods() == null) {
            throw new IllegalArgumentException("장바구니가 비어있습니다.");
        }
        CartEntity entity = CartEntity.toEntity(cart);
        cartService.registerCart(entity);

        System.out.println(String.format("장바구니에 물건이 담겼습니다.%s" , cart.getGoods()));

    }


    public void registerMember(MemberDTO member) {
        // validation
        if (isNameLength(member)) {
            throw new IllegalArgumentException("이름은 20자를 초과할 수 없습니다.");
        }
        MemberEntity entity = MemberEntity.toEntity(member);
        service.registerMember(entity);

        System.out.println(String.format("회원 가입 완료 %s", member.getName()));
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
                (200, "success", service.getMembers());


    }

    private static boolean isNameLength(MemberDTO member) {
        return member.getName().length() > 20;
    }
}
