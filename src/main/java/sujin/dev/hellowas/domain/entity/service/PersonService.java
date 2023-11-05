package sujin.dev.hellowas.domain.entity.service;

import ch.qos.logback.classic.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sujin.dev.hellowas.domain.entity.Person;
import sujin.dev.hellowas.domain.entity.PersonRole;
import sujin.dev.hellowas.domain.entity.repository.PersonRepository;

import java.util.List;

public interface PersonService {

    List<Person> personsList();

    Person getPerson(Long id);

    void personAdd(Person person);

    void personEdit(Long id, Person person);

    void personDelete(Long id);

    @Service
    @RequiredArgsConstructor
    class PersonServiceImpl implements PersonService {
        private final PersonRepository personRepository;

        @PostConstruct
        public void init() {
            for (int i = 0; i < 10; i++) {
                PersonRole personRole = PersonRole.USER;
                if (i % 2 == 0) {
                    personRole = PersonRole.USER;
                } else if (i % 3 == 1) {
                    personRole = PersonRole.GUEST;
                } else {
                    personRole = PersonRole.ADMIN;
                }
                personRepository.save(Person.createPerson("name", "address" + i, 13 * i, personRole));

            }

        }

        @Override
        public List<Person> personsList() {
            return personRepository.findAll();
        }

        @Override
        public Person getPerson(Long id) {
            return personRepository.findById(id)
                    .orElse(null); // ID에 해당하는 Person 엔티티를 찾지 못하면 null을 반환합니다.
        }

        @Override
        public void personAdd(Person person) {
            personRepository.save(person);
        }

        @Override
        public void personEdit(Long id, Person person) {

            // id에 해당하는 Person 엔티티를 조회
            Person existingPerson = personRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

            // Perosn 필드를 업데이트합니다.
            existingPerson.setName(person.getName());
            existingPerson.setAge(person.getAge());
            existingPerson.setAddress(person.getAddress());

            // 업데이트된 엔티티를 저장합니다.
            personRepository.save(existingPerson);
        }

        @Override
        public void personDelete(Long id) {
            personRepository.deleteById(id);
        }
    }

}
