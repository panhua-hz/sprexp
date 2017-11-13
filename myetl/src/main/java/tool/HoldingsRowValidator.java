package tool;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.base.Strings;

import vo.RowVO;

public class HoldingsRowValidator implements RowValidator<RowVO>, DataValidator<RowVO> {

	@Override
	public boolean valid(RowVO row) {
		if (Objects.isNull(row) || Objects.isNull(row.getRowContent())){
			return false;
		}
		return vbIgnore(row) && vbColCount(row) && vbNotNull(row) && vbDataType(row);
	}

	@Override
	public boolean vbIgnore(RowVO row) {
		if (row.getLineNo() == 1)
			return false;
		return true;
	}

	@Override
	public boolean vbColCount(RowVO row) {
		if (row.getRowContent().length == 10)
			return true;
		return false;
	}
	private static final int[] NOT_NULL_IDX = new int[]{0,1,2,3,4}; 
	@Override
	public boolean vbNotNull(RowVO row) {
		for (int i : NOT_NULL_IDX){
			if (Strings.isNullOrEmpty(row.getRowContent()[i])){
				return false;
			}
		}
		return true;
	}
	private static final Pattern[] COL_PAT = new Pattern[]{Pattern.compile("\\w{9}"), Pattern.compile("\\w{4}"), Pattern.compile("\\d{8}"), null};

	@Override
	public boolean vbDataType(RowVO row) {
		for (int i = 0; i < COL_PAT.length; i++){
			if (COL_PAT[i] == null){
				continue;
			}
			Matcher m = COL_PAT[i].matcher(row.getRowContent()[i]);
			if (!m.matches()){
				return false;
			}
		}
		return true;
	}

}
