package sujin.dev.mem;

import sujin.dev.mem.controller.RestController;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.domain.service.MemberService;
import sujin.dev.mem.infra.repo.impl.MemRepository;

import java.util.ArrayList;

public class MemMain {
    public static void main(String[] args) {
        MemberService memberService = new MemberService.MemberServiceImpl(new MemRepository(new ArrayList<>()));
        RestController restController = new RestController(memberService);

        MemberDTO memberDTO = MemberDTO.builder()
                .name("dssdfs")
                .phone("123123").build();
        restController.registerMember(memberDTO);


    }
}
