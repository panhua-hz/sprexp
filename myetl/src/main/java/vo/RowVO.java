package vo;

public class RowVO {
	private String sessionID;
	private long lineNo;
	private String[] rowContent;
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public long getLineNo() {
		return lineNo;
	}
	public void setLineNo(long lineNo) {
		this.lineNo = lineNo;
	}
	public String[] getRowContent() {
		return rowContent;
	}
	public void setRowContent(String[] rowContent) {
		this.rowContent = rowContent;
	}
}
