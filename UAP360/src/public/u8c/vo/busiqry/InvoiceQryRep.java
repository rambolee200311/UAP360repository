package u8c.vo.busiqry;
import java.util.List;
/*
 * 20230613
 * ��Ʊ���˽ӿ�
 */
public class InvoiceQryRep {
	public String status;//�Ƿ�ɹ���success�ɹ���failʧ��
	public String sAccountCheckDate;//��������
	public String eAccountCheckDate;//����ֹ��
	public String u8cCode;//�ɹ���Ϣ��ʧ����Ϣ
	public List<InvoiceQryRepDetail> detail;//��Ʊ���˽ӿ���ϸ
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
	public List<InvoiceQryRepDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<InvoiceQryRepDetail> detail) {
		this.detail = detail;
	}
	
}
