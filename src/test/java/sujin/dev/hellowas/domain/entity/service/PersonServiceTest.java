package sujin.dev.hellowas.domain.entity.service;

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
class PersonServiceTest {



    @Autowired
    private PersonService personService;

    @BeforeEach
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
            personService.personAdd(Person.createPerson("name","address"+i, 13*i,personRole));

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
        personService.personAdd(null);
    }


    @Test
    @DisplayName("회원 수정")
    @Order(4)
    void personEdit(){
        personService.personEdit(1L, null);
    }

    @Test
    @DisplayName("회원 삭제")
    @Order(5)
    void personDelete(){
        personService.personDelete(1L);
    }

}