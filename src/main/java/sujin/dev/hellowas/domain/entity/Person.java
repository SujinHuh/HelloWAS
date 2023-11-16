package sujin.dev.hellowas.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "address"}))
public class Person extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false) // 유니크 키
    private String name;

    private String address;

    @Column(name = "age", length = 3, nullable = false)
    private int age;

    private String userId;

    private String password;


    @Enumerated(EnumType.STRING)
    private PersonRole role;
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdult(){
        return age>= 20;
    }


    private Person(String name, String address, int age, PersonRole role) {
        this.name = name;
        this.address = address;
        this.age  = age;
        this.role = role;
    }
    public static Person createPerson(String name, String address, int age,PersonRole role) {
        return new Person(name, address, age,role);
    }
}
