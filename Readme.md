# 와스 헬로우 서버 구축
was:8080 <-> nginx(/api/gw) <-> node.js:3000

# 구성
- controller
  - restful api
  - modelandview
- service
  - biz 로직
- repository
  - db접근
- model
  - dto, 도메인

# Entity VS DTO
- DTO : html <-> 자바 객체
- Entity : db <-> 자바 객체 # HelloWAS


- 레이어드 아키텍처
  - 컨트롤러 
    - RestController : JSON, XMl 
    - Controller : View-JSP, HTML
  - 도메인
    - 엔티티 : Table 
    - 모델 : DTO, VO
    - 서비스 : 비즈니스 로직 
  - 인프라
    - 레포 : 로우레벨 CRUD 정의 
      - 레포impl : 로우레빌 CRUD 구현 