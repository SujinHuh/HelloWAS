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
