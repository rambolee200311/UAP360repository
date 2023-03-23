package u8c.vo.goldentax;

import java.util.List;
/*
 * 20230323 lijianqiang
 * 获取发票列表或信息 result
 */
public class FPListResult {
	private String Result;
	private String Message;
	private int total;
	private List< FPListRow> rows;
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<FPListRow> getRows() {
		return rows;
	}
	public void setRows(List<FPListRow> rows) {
		this.rows = rows;
	}
	
}
