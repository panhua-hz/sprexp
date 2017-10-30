package exceptions;

public class SchedRTException extends RuntimeException {
	private static final long serialVersionUID = -2029647638800319598L;

	public SchedRTException() {
	}

	public SchedRTException(String message) {
		super(message);
	}

	public SchedRTException(Throwable cause) {
		super(cause);
	}

	public SchedRTException(String message, Throwable cause) {
		super(message, cause);
	}

	public SchedRTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
