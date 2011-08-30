package net.aihat.exception;

public class AihatException extends Exception {
	public AihatException(String detail) {
		super(detail);
	}
	
	public AihatException(String detail, Throwable ex) {
		super(detail, ex);
	}
}
