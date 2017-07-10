package sprtmplt;

public interface PutMsgService {
	void putMsg(String message);
	String getMsg();
	String getCvtMsg();
	
	void autoGetMsg(String message);
}
