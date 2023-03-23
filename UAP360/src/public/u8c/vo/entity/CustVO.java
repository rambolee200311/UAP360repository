package u8c.vo.entity;
import nc.vo.pub.SuperVO;

public class CustVO extends SuperVO {
	private static final long serialVersionUID = 1L;
	private String custcode;
	private String custname;
	private String conaddr;
	private String phone1;
	private String taxpayerid;
	private String def1;
	private String def2;
	private String def3;	
	public String getDef1() {
		return def1;
	}
	public void setDef1(String def1) {
		this.def1 = def1;
	}
	public String getDef2() {
		return def2;
	}
	public void setDef2(String def2) {
		this.def2 = def2;
	}
	public String getDef3() {
		return def3;
	}
	public void setDef3(String def3) {
		this.def3 = def3;
	}
	public String getCustcode() {
		return custcode;
	}
	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	
	public String getConaddr() {
		return conaddr;
	}
	public void setConaddr(String conaddr) {
		this.conaddr = conaddr;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getTaxpayerid() {
		return taxpayerid;
	}
	public void setTaxpayerid(String taxpayerid) {
		this.taxpayerid = taxpayerid;
	}
	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_cubasdoc";
	}
	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "bd_cubasdoc";
	}
}
