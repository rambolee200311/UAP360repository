package u8c.vo.busiqry;
/*
 * 20230613
 * 开票对账接口明细
 */
public class InvoiceQryRepDetail {
	public int rowNo;//行号，1开始自增
	public String busiType;//业务类型编码 F0-01 经纪费应收单 F0-02 再保经纪费应收单  F0-03 咨询费应收单
	public String adviceNote;//通知书编号
	public String invoiceNo;//发票号
	public String invoiceTime;//开票日期
	public String currency;//币种
	public double invoiceMoney;//开票金额
	public String comCode;//开票机构编码
	public String comName;//开票机构名称
	public String payerCode;//支付方编号
	public String payerName;//支付方名称
	public String invoicType;//发票类型（1-蓝票，2-红票）
	public String isPush;//是否推送业务系统（0-未推送，1-已推送）
	public String zyx1;//扩展字段1
	public String zyx2;//扩展字段2
	public String zyx3;//扩展字段3
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getAdviceNote() {
		return adviceNote;
	}
	public void setAdviceNote(String adviceNote) {
		this.adviceNote = adviceNote;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceTime() {
		return invoiceTime;
	}
	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getInvoiceMoney() {
		return invoiceMoney;
	}
	public void setInvoiceMoney(double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
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
	public String getInvoicType() {
		return invoicType;
	}
	public void setInvoicType(String invoicType) {
		this.invoicType = invoicType;
	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
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

}
