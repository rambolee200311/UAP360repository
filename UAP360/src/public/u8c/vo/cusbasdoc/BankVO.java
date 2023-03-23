package u8c.vo.cusbasdoc;

public class BankVO {

    private String pk_corp;
    private String accname;
    private String account;
    private String accountname;
    private boolean defflag;
    private String pk_currtype;
    private String currtypename;
    private String status;
    public void setPk_corp(String pk_corp) {
         this.pk_corp = pk_corp;
     }
     public String getPk_corp() {
         return pk_corp;
     }

    public void setAccname(String accname) {
         this.accname = accname;
     }
     public String getAccname() {
         return accname;
     }

    public void setAccount(String account) {
         this.account = account;
     }
     public String getAccount() {
         return account;
     }

    public void setAccountname(String accountname) {
         this.accountname = accountname;
     }
     public String getAccountname() {
         return accountname;
     }

    public void setDefflag(boolean defflag) {
         this.defflag = defflag;
     }
     public boolean getDefflag() {
         return defflag;
     }

    public void setCurrtypename(String currtypename) {
         this.currtypename = currtypename;
     }
     public String getCurrtypename() {
         return currtypename;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }
	public String getPk_currtype() {
		return pk_currtype;
	}
	public void setPk_currtype(String pk_currtype) {
		this.pk_currtype = pk_currtype;
	}

}