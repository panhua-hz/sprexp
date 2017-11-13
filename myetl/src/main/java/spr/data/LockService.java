package spr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class LockService {
	@Autowired
	private JdbcOperations jdbc;
	
	public boolean acquireKey(String lockObj, String lockOwner){
		int result = jdbc.update("update AA_LOCK set LOCK_OWNER=? where LOCK_OBJ=? and LOCK_OWNER is null", lockOwner, lockObj);
		if (result > 0){
			return true;
		}
		return false;
	}
	
}
