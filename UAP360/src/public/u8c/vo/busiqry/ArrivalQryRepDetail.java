package u8c.vo.busiqry;
/*
 * 20230613
 * 到账对账接口明细
 */
public class ArrivalQryRepDetail {
	public int rowNo;//行号，1开始自增
	public String arrivalBusinType;//到账业务类型 ： //1-经纪费  //2-保费 //F2-08 转户到账 F2-09 期初到账
	public String arrivalRegiCode;//到账登记号
	public String payerCode;//支付方编码
	public String payerName;//支付方名称
	public String arrivalDate;//到账日期
	public Double arrivalAmount;//到账金额
	public String currency;//到账币种
	public String operatorCode;//经办人
	public String arrivalPurpose;//款项用途
	public String isPush;//是否推送业务系统（0-未推送，1-已推送）
	public String zyx1;//扩展字段1
	public String zyx2;//扩展字段2
	public String zyx3;//扩展字段3
	public String comCode;//收款机构编码
	public String comName;//收款机构名称
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public String getArrivalBusinType() {
		return arrivalBusinType;
	}
	public void setArrivalBusinType(String arrivalBusinType) {
		this.arrivalBusinType = arrivalBusinType;
	}
	public String getArrivalRegiCode() {
		return arrivalRegiCode;
	}
	public void setArrivalRegiCode(String arrivalRegiCode) {
		this.arrivalRegiCode = arrivalRegiCode;
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
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Double getArrivalAmount() {
		return arrivalAmount;
	}
	public void setArrivalAmount(Double arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getArrivalPurpose() {
		return arrivalPurpose;
	}
	public void setArrivalPurpose(String arrivalPurpose) {
		this.arrivalPurpose = arrivalPurpose;
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

}
