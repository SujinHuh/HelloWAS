package sujin.dev.hellowas.domain.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sujin.dev.hellowas.domain.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
