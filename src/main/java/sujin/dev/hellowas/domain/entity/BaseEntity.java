package sujin.dev.hellowas.domain.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public class BaseEntity {

    private LocalDateTime regDate;

    private String reUser;

    private LocalDateTime upDate;

    private String upUser;
}
