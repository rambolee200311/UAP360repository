package u8c.vo.reverseInvoice;
import java.util.List;
public class ReverseInvoiceBody {
	private String adviceNote;
    private String adviceDate;
    private String reverseInvoiceNo;
    private double inclusiveMoney;
    private double inclusiveRMB;
    private String currency;
    private double reverseInclusiveMoney;
    private double reverseInclusiveRMB;
    private String payerCode;//收票单位编码
	private String payerName;//收票单位名称
    private String operatorCode;
    private String comCode;
    private String comName;
    private String busiType;
    private String zyx1;
    private String zyx2;
    private String zyx3;
    private String zyx4;
    private String zyx5;
    private String dpName;//部门名称
    private List<ReverseInvoiceDetail> detail;
	public String getAdviceNote() {
		return adviceNote;
	}
	public void setAdviceNote(String adviceNote) {
		this.adviceNote = adviceNote;
	}
	public String getAdviceDate() {
		return adviceDate;
	}
	public void setAdviceDate(String adviceDate) {
		this.adviceDate = adviceDate;
	}
	public String getReverseInvoiceNo() {
		return reverseInvoiceNo;
	}
	public void setReverseInvoiceNo(String reverseInvoiceNo) {
		this.reverseInvoiceNo = reverseInvoiceNo;
	}
	public double getInclusiveMoney() {
		return inclusiveMoney;
	}
	public void setInclusiveMoney(double inclusiveMoney) {
		this.inclusiveMoney = inclusiveMoney;
	}
	public double getInclusiveRMB() {
		return inclusiveRMB;
	}
	public void setInclusiveRMB(double inclusiveRMB) {
		this.inclusiveRMB = inclusiveRMB;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getReverseInclusiveMoney() {
		return reverseInclusiveMoney;
	}
	public void setReverseInclusiveMoney(double reverseInclusiveMoney) {
		this.reverseInclusiveMoney = reverseInclusiveMoney;
	}
	public double getReverseInclusiveRMB() {
		return reverseInclusiveRMB;
	}
	public void setReverseInclusiveRMB(double reverseInclusiveRMB) {
		this.reverseInclusiveRMB = reverseInclusiveRMB;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getZyx1() {
		return zyx1;
	}
	public void setZyx1(String zyx1) {
		this.zyx1 = zyx1;
	}
	public String getZyx2() {
		return zyx2;
	}
	public void setZyx2(String zyx2) {
		this.zyx2 = zyx2;
	}
	public String getZyx3() {
		return zyx3;
	}
	public void setZyx3(String zyx3) {
		this.zyx3 = zyx3;
	}
	public String getZyx4() {
		return zyx4;
	}
	public void setZyx4(String zyx4) {
		this.zyx4 = zyx4;
	}
	public String getZyx5() {
		return zyx5;
	}
	public void setZyx5(String zyx5) {
		this.zyx5 = zyx5;
	}
	public List<ReverseInvoiceDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<ReverseInvoiceDetail> detail) {
		this.detail = detail;
	}
	public String getDpName() {
		return dpName;
	}
	public void setDpName(String dpName) {
		this.dpName = dpName;
	}
	public String getPayerCode() {
		return payerCode;
	}
	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
    
}
