package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.infra.repo.DataRepository;
import sujin.dev.mem.infra.repo.impl.MemRepository;

import java.util.List;

public interface MemberService {
    void registerMember(MemberEntity member);
    boolean isUserIdTaken(String userId);
    List<MemberDTO> getMembers();
    MemberDTO mapToDTO(MemberEntity member);
    MemberDTO findByMemberId(String memberId);
    MemberEntity findMemberEntityByMemberId(String memberId);

    @RequiredArgsConstructor @Slf4j
    class MemberServiceImpl implements MemberService {
        private final DataRepository<MemberEntity> repository;
        @Override
        public void registerMember(MemberEntity member) {
            String userId = member.getMemberId();

            // userId의 중복 여부를 체크합니다.
            if (isUserIdTaken(userId)) {
                // 중복된 userId가 있는 경우 예외를 던집니다.
                throw new IllegalArgumentException("이미 가입되어있는 회원입니다.");
            }
            log.info("st>>>>");
            log.info(member.getPassword());
            // 중복된 userId가 없는 경우, 새 멤버를 데이터베이스에 추가합니다.
            repository.insert(member);
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

        // findByMemberId 메서드 구현
        @Override
        public MemberDTO findByMemberId(String memberId) {
            // repository가 MemRepository 인스턴스인지 확인 후 타입 캐스팅
            if (repository instanceof MemRepository) {
                MemRepository memRepository = (MemRepository) repository;
                MemberEntity member = memRepository.findByMemberId(memberId);

                // MemberEntity를 MemberDTO로 매핑
                return member != null
                        ? MemberDTO.builder()
                        .memberId(memberId)
                        .password(member.getPassword())
                        .name(member.getName())
                        .phone(member.getPhone())
                        .address(member.getAddress())
                        .build()
                        : null;
            } else {
                // MemRepository가 아닌 경우, 적절한 처리 (예: 예외 발생)
                throw new IllegalStateException("Unsupported repository type");
            }
        }

        @Override
        public MemberEntity findMemberEntityByMemberId(String memberId) {
            //repository에서 MemberEntity조회
            return repository.findAll().stream()
                    .filter(member -> memberId.equals(member.getMemberId()))
                    .findFirst()
                    .orElse(null);
        }
        private MemberDTO convertToDTO(MemberEntity memberEntity){
            return MemberDTO.builder()
                    .name(memberEntity.getName())
                    .phone(memberEntity.getPhone())
                    .build();
        }

        public boolean isUserIdTaken(String userId) {
            return findByMemberId(userId) != null;
        }

    }
}
