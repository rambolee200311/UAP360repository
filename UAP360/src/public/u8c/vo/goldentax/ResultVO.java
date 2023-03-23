package u8c.vo.goldentax;

import java.util.List;

public class ResultVO {
	private String Reulst;
	private String Message;
	private List<SucessListVO> SucessList;
	private List<ErrListVO> ErrList;
	public String getReulst() {
		return Reulst;
	}
	public void setReulst(String reulst) {
		Reulst = reulst;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public List<SucessListVO> getSucessList() {
		return SucessList;
	}
	public void setSucessList(List<SucessListVO> sucessList) {
		SucessList = sucessList;
	}
	public List<ErrListVO> getErrList() {
		return ErrList;
	}
	public void setErrList(List<ErrListVO> errList) {
		ErrList = errList;
	}
	
}
