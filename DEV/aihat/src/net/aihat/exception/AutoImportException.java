package net.aihat.exception;

public class AutoImportException extends AihatException {
	public AutoImportException(String detail) {
		super(detail);
	}
	public AutoImportException(String detail, Throwable ex) {
		super(detail, ex);
	}
}
