package sujin.dev.mem.common.exception;

public class MemberException extends RuntimeException {
    ErrorCode errorCode;
    public MemberException(String msg) {
        super(msg);
        System.out.println(errorCode.name());
    }
}
