package db;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcBuilder {

	//private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
//	private static final String DB_URL = "jdbc:oracle:thin:@HZ27P4993473.corp.statestr.com:1521:xe";
//	private static final String DB_USER = "my_lc";
//	private static final String DB_PWD = "mylc";
	
	//jdbc.url=jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE
	private static final String DB_URL = "jdbc:mysql://localhost:3306/andrew?serverTimezone=UTC";
	private static final String DB_USER = "andrew";
	private static final String DB_PWD = "andrew";
	
	private static final DataSource ORC_DS = new DriverManagerDataSource(DB_URL,DB_USER,DB_PWD);
	private static final JdbcTemplate ORC_JDBC_TMP = new JdbcTemplate(ORC_DS);

	public static JdbcTemplate getODSJdbcTemplate(){
		return ORC_JDBC_TMP;
	}
}
