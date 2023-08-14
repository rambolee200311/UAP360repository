package u8c.vo.applyInvoice;

import java.util.List;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;

public class U8CBillVO {
	private DJZBHeaderVO parentvo;
	private List<DJZBItemVO> children;
	public DJZBHeaderVO getParentvo() {
		return parentvo;
	}
	public void setParentvo(DJZBHeaderVO parentvo) {
		this.parentvo = parentvo;
	}
	public List<DJZBItemVO> getChildren() {
		return children;
	}
	public void setChildren(List<DJZBItemVO> children) {
		this.children = children;
	}
	
}
