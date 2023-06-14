package u8c.vo.busiqry;
import java.util.List;
/*
 * 20230613
 * 转付对账接口
 */
public class TransferQryRep {
	public String status;//是否成功：success成功，fail失败
	public String sAccountCheckDate;//对账起期
	public String eAccountCheckDate;//对账止期
	public String u8cCode;//成功信息或失败信息
	public List<TransferQryRepDetail> detail;//转付对账接口明细
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getsAccountCheckDate() {
		return sAccountCheckDate;
	}
	public void setsAccountCheckDate(String sAccountCheckDate) {
		this.sAccountCheckDate = sAccountCheckDate;
	}
	public String geteAccountCheckDate() {
		return eAccountCheckDate;
	}
	public void seteAccountCheckDate(String eAccountCheckDate) {
		this.eAccountCheckDate = eAccountCheckDate;
	}
	public String getU8cCode() {
		return u8cCode;
	}
	public void setU8cCode(String u8cCode) {
		this.u8cCode = u8cCode;
	}
	public List<TransferQryRepDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<TransferQryRepDetail> detail) {
		this.detail = detail;
	}
	
}
