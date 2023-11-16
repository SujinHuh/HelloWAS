package sujin.dev.mem.domain.model;

import lombok.*;

@Builder @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class ResponseResult<T> {
    int code;
    String msg;
    T body;
}
