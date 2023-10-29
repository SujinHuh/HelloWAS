package sujin.dev.hellowas.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {

    private String name;
    private int age;
    private Address address;
}
