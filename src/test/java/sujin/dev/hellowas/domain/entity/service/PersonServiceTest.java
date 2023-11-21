package sujin.dev.hellowas.domain.entity.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sujin.dev.hellowas.domain.entity.Person;
import sujin.dev.hellowas.domain.entity.PersonRole;
import sujin.dev.hellowas.domain.entity.service.PersonService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 5개의 스레드 풀 생성

    @BeforeEach
    void setUp() throws InterruptedException {
        // 10명의 Person을 병렬로 추가
        for (int i = 0; i < 10; i++) {
            final int index = i;

            executorService.submit(() -> {
                PersonRole personRole = PersonRole.USER;
                if (index % 2 == 0) {
                    personRole = PersonRole.USER;
                } else if (index % 3 == 1) {
                    personRole = PersonRole.GUEST;
                } else {
                    personRole = PersonRole.ADMIN;
                }

                Person person = Person.createPerson("name" + index, "address" + index, 13 * index, personRole);

                assertDoesNotThrow(() -> personService.personAdd(person));
            });
        }

        // 모든 스레드의 작업이 완료될 때까지 대기
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    @Test
    @DisplayName("회원 목록조회")
    @Order(1)
    void personList() {
        assertDoesNotThrow(() -> {
            System.out.println(personService.personsList());
        });
    }

    @Test
    @DisplayName("회원 조회")
    @Order(2)
    void getPerson() {
        assertDoesNotThrow(() -> {
            System.out.println(personService.getPerson(1L));
        });
    }

    @Test
    @DisplayName("회원 추가")
    @Order(3)
    void personAdd() {
        try {
            // Person 추가 작업이 모두 완료될 때까지 대기
            setUp();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태를 재설정
            fail("InterruptedException during setup: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("회원 수정")
    @Order(4)
    void personEdit() throws InterruptedException {
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
            personService.personEdit(id, existingPerson);

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
        //log.info("id가 삭제 되었습니다. : {}",id);
    }

}
