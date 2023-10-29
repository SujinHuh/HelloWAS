package sujin.dev.hellowas.domain.entity.service;

import jakarta.annotation.PostConstruct;
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
    class PersonServiceImpl implements PersonService{
        private final PersonRepository personRepository;

        @PostConstruct
        public void init() {
            for (int i = 0; i < 10; i++){
                PersonRole personRole = PersonRole.USER;
                if(i % 2 == 0){
                    personRole = PersonRole.USER;
                } else if(i % 3 == 1){
                    personRole = PersonRole.GUEST;
                }else {
                    personRole = PersonRole.ADMIN;
                }
                personRepository.save(Person.createPerson("name","address"+i, 13*i,personRole));

            }

        }

        @Override
        public List<Person> personsList() {
            return personRepository.findAll();
        }

        @Override
        public Person getPerson(Long id) {
            return null;
        }

        @Override
        public void personAdd(Person person) {

        }

        @Override
        public void personEdit(Long id, Person person) {

        }

        @Override
        public void personDelete(Long id) {

        }
    }

}
