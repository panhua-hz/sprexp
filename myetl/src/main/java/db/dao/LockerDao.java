package db.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import db.JdbcBuilder;

public class LockerDao {
	private JdbcTemplate jdbcTmp = JdbcBuilder.getODSJdbcTemplate();

	public boolean acquireKey(String lockObj, String lockOwner) {
		List<Integer> isKeyFreeList = jdbcTmp.query(
				"select count(1) from AA_LOCK where LOCK_OBJ=? and LOCK_OWNER is null", new Object[] { lockObj },
				(rs, rn) -> {
					return new Integer(rs.getInt(1));
				});
		int cunt = isKeyFreeList.get(0).intValue();
		if (cunt > 0) {
			String tn = Thread.currentThread().getName();
			System.out.println(tn+" is acquireKey!");
			int result = jdbcTmp.update("update AA_LOCK set LOCK_OWNER=? where LOCK_OBJ=? and LOCK_OWNER is null",
					lockOwner, lockObj);
			if (result > 0) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		boolean getKey = new LockerDao().acquireKey("load_file1", "lockDaoTest");
		System.out.println("GetKey: " + getKey);
	}

}
