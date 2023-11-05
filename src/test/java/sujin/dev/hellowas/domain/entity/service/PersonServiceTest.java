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
class PersonServiceTest {



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
            Person person = Person.createPerson("name","address"+i, 13*i,personRole);

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
    void personEdit(){
        // 업데이트할 Person 객체 생성
        Person updatedPerson = new Person();
        updatedPerson.setName("Updated Name");
        updatedPerson.setAge(30);
        // 주소 설정 (Address 객체를 생성하고 설정해야 함)

        // personEdit 메서드 호출
        personService.personEdit(updatedPerson.getId(), updatedPerson);
    }

    @Test
    @DisplayName("회원 삭제")
    @Order(5)
    void personDelete(){
        personService.personDelete(1L);
    }

}