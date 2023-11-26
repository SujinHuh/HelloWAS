package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

public interface MemberService {
    void registerMember(MemberEntity member);

    List<MemberDTO> getMembers();
    MemberDTO mapToDTO(MemberEntity member);
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
        public List<MemberDTO> getMembers() {
            return this.repository.findAll().stream().map(m -> {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setName(m.getName());
                memberDTO.setPhone(m.getPhone());
                return memberDTO;
            }).toList();
        }

        @Override
        public MemberDTO mapToDTO(MemberEntity member) {
            if (member == null) {
                return null;
            }

            return MemberDTO.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    .build();
        }

        private MemberDTO convertToDTO(MemberEntity memberEntity){
            return MemberDTO.builder()
                    .name(memberEntity.getName())
                    .phone(memberEntity.getPhone())
                    .build();
        }
    }
}
