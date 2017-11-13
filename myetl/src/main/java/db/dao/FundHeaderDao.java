package db.dao;

import java.util.List;

import db.JdbcBuilder;

public class FundHeaderDao {
	public static class FundHeaderVO{
		private String fundID;
		private String fundCusip;
		private String coreFund;
		private String cso;
		private String fundType;
		private int lineNo;
		@Override
		public String toString() {
			return "["+lineNo+","+fundID+","+fundCusip+","+coreFund+","+cso+","+fundType+"]";
			//return Objects.
		}
		public FundHeaderVO(int lineNo, String fundID, String fundCusip, String coreFund, String cso, String fundType) {
			this.fundID = fundID;
			this.fundCusip = fundCusip;
			this.coreFund = coreFund;
			this.cso = cso;
			this.fundType = fundType;
			this.lineNo = lineNo;
		}
		public String getFundID() {
			return fundID;
		}
		public void setFundID(String fundID) {
			this.fundID = fundID;
		}
		public String getFundCusip() {
			return fundCusip;
		}
		public void setFundCusip(String fundCusip) {
			this.fundCusip = fundCusip;
		}
		public String getCoreFund() {
			return coreFund;
		}
		public void setCoreFund(String coreFund) {
			this.coreFund = coreFund;
		}
		public String getCso() {
			return cso;
		}
		public void setCso(String cso) {
			this.cso = cso;
		}
		public String getFundType() {
			return fundType;
		}
		public void setFundType(String fundType) {
			this.fundType = fundType;
		}
		public int getLineNo() {
			return lineNo;
		}
		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
		
		
		
	}
	
	public static List<FundHeaderVO> queryFundHeaders(){
		return JdbcBuilder.getODSJdbcTemplate().query("select * from C_FUND_HEADER", (rs,rnum)->{
			FundHeaderVO vo = new FundHeaderVO(rnum, 
					rs.getString("FUND_ID"), 
					rs.getString("FUND_CUSIP"), 
					rs.getString("CORE_FUND"), 
					rs.getString("CSO"), 
					rs.getString("FUND_TYPE"));
			return vo;
		});
	}
	
	public static void main(String[] args) {
		List<FundHeaderVO> result = queryFundHeaders();
		System.out.println(result);
	}

}
