package sujin.dev.hellowas.domain.entity.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sujin.dev.hellowas.domain.entity.Person;
import sujin.dev.hellowas.domain.entity.PersonRole;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PersonServiceTest_orgin {



    @Autowired
    private PersonService personService;

    @BeforeEach //공통적으로 필요한 초기화 작업
    void setUp() {
        for (int i = 0; i < 10; i++){
            PersonRole personRole = PersonRole.USER;
            if(i % 2 == 0){
                personRole = PersonRole.USER;
            } else if(i % 3 == 1){
                personRole = PersonRole.GUEST;
            }else {
                personRole = PersonRole.ADMIN;
            }
            Person person = Person.createPerson("name"+i,"address"+i, 13*i,personRole);

            // 로그를 출력
            log.info("로그 출력 >>> ");
            log.info("Adding person: {}", person);

            personService.personAdd(person);
        }
    }


    @Test
    @DisplayName("회원 목록조회")
    @Order(1)
    void personList() {
        System.out.println(personService.personsList());
    }

    @Test
    @DisplayName("회원 조회")
    @Order(2)
    void getPerson() {
        System.out.println(personService.getPerson(1L));
    }

    @Test
    @DisplayName("회원 추가")
    @Order(3)
    void personAdd() {
        setUp();
//        personService.personAdd(null);
    }


    @Test
    @DisplayName("회원 수정")
    @Order(4)
    void personEdit() {
        // 수정할 Person 엔티티의 ID를 얻어옵니다.
        Long id = 1L; // 예시로 ID가 1인 Person을 수정하도록 설정

        // 데이터베이스에서 해당 ID에 해당하는 Person을 가져옵니다.
        Person existingPerson = personService.getPerson(id);

        if (existingPerson != null) {
            // 수정할 내용을 담은 Person 객체를 생성
            Person updatedPerson = new Person();
            updatedPerson.setName("Updated Name");
            updatedPerson.setAge(30);
            updatedPerson.setAddress("Updated Address");
            updatedPerson.setRole(PersonRole.ADMIN);

            // 기존 Person 엔티티의 필드를 업데이트합니다.
            existingPerson.setName(updatedPerson.getName());
            existingPerson.setAge(updatedPerson.getAge());
            existingPerson.setAddress(updatedPerson.getAddress());
            existingPerson.setRole(updatedPerson.getRole());

            // 수정 메서드 호출
            personService.personEdit(id,existingPerson);

            // 수정된 Person 엔티티를 다시 불러와서 확인
            Person modifiedPerson = personService.getPerson(id);

            // 수정된 내용이 올바르게 반영되었는지 확인
            assertNotNull(modifiedPerson);
            assertEquals("Updated Name", modifiedPerson.getName());
            assertEquals(30, modifiedPerson.getAge());
            assertEquals("Updated Address", modifiedPerson.getAddress());
        }
    }


    @Test
    @DisplayName("회원 삭제")
    @Order(5)
    void personDelete(){
        Long id = 1L;
        personService.personDelete(id);
        log.info("id가 삭제 되었습니다. : {}",id);
    }

}