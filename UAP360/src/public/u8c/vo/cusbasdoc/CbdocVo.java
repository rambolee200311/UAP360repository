package u8c.vo.cusbasdoc;
import java.util.List;
public class CbdocVo {
	private ParentVO parentvo;
	private List<AddrVO> addrs;
	private List<BankVO> banks;
	public ParentVO getParentvo() {
		return parentvo;
	}
	public void setParentvo(ParentVO parentvo) {
		this.parentvo = parentvo;
	}
	public List<AddrVO> getAddrs() {
		return addrs;
	}
	public void setAddrs(List<AddrVO> addrs) {
		this.addrs = addrs;
	}
	public List<BankVO> getBanks() {
		return banks;
	}
	public void setBanks(List<BankVO> banks) {
		this.banks = banks;
	}	
}
