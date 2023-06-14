package u8c.vo.busiqry;
/*
 * 20230613
 * 转付对账接口明细
 */
public class TransferQryRepDetail {
	public int rowNo;//行号，1开始自增
	public String transferBusinType;//转付类型：1-转付保费,2-再保转付保费,3-理赔转付保费,4-再保理赔转付保费
	public String transferApplyNo;//转付申请号
	public String paymentReviewNo;//划拨单号
	public String insuranceCode;//客商编码
	public String insuranceName;//客商名称
	public String currency;//币种
	public Double transferAmount;//转付金额
	public String transferComCode;//转付机构编码
	public String transferComName;//转付机构名称
	public String transferDate;//转付日期
	public String acceptDate;//划拨日期
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
	public String getTransferBusinType() {
		return transferBusinType;
	}
	public void setTransferBusinType(String transferBusinType) {
		this.transferBusinType = transferBusinType;
	}
	public String getTransferApplyNo() {
		return transferApplyNo;
	}
	public void setTransferApplyNo(String transferApplyNo) {
		this.transferApplyNo = transferApplyNo;
	}
	public String getPaymentReviewNo() {
		return paymentReviewNo;
	}
	public void setPaymentReviewNo(String paymentReviewNo) {
		this.paymentReviewNo = paymentReviewNo;
	}
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public String getTransferComCode() {
		return transferComCode;
	}
	public void setTransferComCode(String transferComCode) {
		this.transferComCode = transferComCode;
	}
	public String getTransferComName() {
		return transferComName;
	}
	public void setTransferComName(String transferComName) {
		this.transferComName = transferComName;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
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
