package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

public interface MemberService {
    void registerMember(MemberEntity member);

    List<sujin.dev.mem.domain.model.MemberDTO> getMembers();
    sujin.dev.mem.domain.model.MemberDTO mapToDTO(MemberEntity member);

    sujin.dev.mem.domain.model.MemberDTO findMemberByName(String memberName);

    @RequiredArgsConstructor
    class MemberServiceImpl implements MemberService {
        private final DataRepository<MemberEntity> repository;
        @Override
        public void registerMember(MemberEntity member) {
            try {
                MemberEntity byId = this.repository.findById(member.getId());
                if (byId == null) {
                    this.repository.insert(member);
                }
                this.repository.update(member);
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }

        @Override
        public List<sujin.dev.mem.domain.model.MemberDTO> getMembers() {
            return this.repository.findAll().stream().map(m -> {
                sujin.dev.mem.domain.model.MemberDTO memberDTO = new sujin.dev.mem.domain.model.MemberDTO();
                memberDTO.setName(m.getName());
                memberDTO.setPhone(m.getPhone());
                return memberDTO;
            }).toList();
        }

        @Override
        public sujin.dev.mem.domain.model.MemberDTO mapToDTO(MemberEntity member) {
            if (member == null) {
                return null;
            }

            return sujin.dev.mem.domain.model.MemberDTO.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    .build();
        }

        @Override
        public sujin.dev.mem.domain.model.MemberDTO findMemberByName(String memberName) {

            MemberEntity member = repository.findByMemberName(memberName);

            // MemberEntity를 MemberDTO로 매핑
            return member != null
                    ? sujin.dev.mem.domain.model.MemberDTO.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    // 필요한 다른 정보들도 매핑
                    .build()
                    : null; // 찾지 못한 경우 null 반환
        }

        private sujin.dev.mem.domain.model.MemberDTO convertToDTO(MemberEntity memberEntity){
            return sujin.dev.mem.domain.model.MemberDTO.builder()
                    .name(memberEntity.getName())
                    .phone(memberEntity.getPhone())
                    .build();
        }
    }
}
