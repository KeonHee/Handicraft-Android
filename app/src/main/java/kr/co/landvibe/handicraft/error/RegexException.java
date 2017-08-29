package kr.co.landvibe.handicraft.error;


public class RegexException extends RuntimeException {
    public RegexException() {
        super();
    }

    public RegexException(String msg) {
        super(msg);
    }

    public RegexException(String msg, Throwable t) {
        super(msg, t);
    }
}
