package u8c.vo.cusbasdoc;
public class ParentVO {

    private String pk_corp;
    private String corp_code;
    private String pk_cubasdoc;
    private String custcode;
    private String custname;
    private String custshortname;
    private boolean freecustflag;
    private boolean drpnodeflag;
    private boolean isconnflag;
    private String custprop;
    private String pk_areacl;
    private String conaddr;
    private String phone1;
    private String phone2;
    private String phone3;
    private String taxpayerid;
    public void setPk_corp(String pk_corp) {
         this.pk_corp = pk_corp;
     }
     public String getPk_corp() {
         return pk_corp;
     }

    public String getCorp_code() {
		return corp_code;
	}
	public void setCorp_code(String corp_code) {
		this.corp_code = corp_code;
	}
	public void setPk_cubasdoc(String pk_cubasdoc) {
         this.pk_cubasdoc = pk_cubasdoc;
     }
     public String getPk_cubasdoc() {
         return pk_cubasdoc;
     }

    public void setCustcode(String custcode) {
         this.custcode = custcode;
     }
     public String getCustcode() {
         return custcode;
     }

    public void setCustname(String custname) {
         this.custname = custname;
     }
     public String getCustname() {
         return custname;
     }

    public void setCustshortname(String custshortname) {
         this.custshortname = custshortname;
     }
     public String getCustshortname() {
         return custshortname;
     }

    public void setFreecustflag(boolean freecustflag) {
         this.freecustflag = freecustflag;
     }
     public boolean getFreecustflag() {
         return freecustflag;
     }

    public void setDrpnodeflag(boolean drpnodeflag) {
         this.drpnodeflag = drpnodeflag;
     }
     public boolean getDrpnodeflag() {
         return drpnodeflag;
     }

    public void setIsconnflag(boolean isconnflag) {
         this.isconnflag = isconnflag;
     }
     public boolean getIsconnflag() {
         return isconnflag;
     }

    public void setCustprop(String custprop) {
         this.custprop = custprop;
     }
     public String getCustprop() {
         return custprop;
     }

    public void setPk_areacl(String pk_areacl) {
         this.pk_areacl = pk_areacl;
     }
     public String getPk_areacl() {
         return pk_areacl;
     }

    public void setConaddr(String conaddr) {
         this.conaddr = conaddr;
     }
     public String getConaddr() {
         return conaddr;
     }

    public void setPhone1(String phone1) {
         this.phone1 = phone1;
     }
     public String getPhone1() {
         return phone1;
     }
     
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getTaxpayerid() {
		return taxpayerid;
	}
	public void setTaxpayerid(String taxpayerid) {
		this.taxpayerid = taxpayerid;
	}

}