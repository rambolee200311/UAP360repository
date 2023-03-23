package u8c.vo.goldentax;

import com.alibaba.fastjson.annotation.JSONField;

public class GTVoucherItem {
	@JSONField(name = "CPMC")
    private String CPMC;
	@JSONField(name = "CPXH")
    private String CPXH;
	@JSONField(name = "CPDW")
    private String CPDW;
	@JSONField(name = "SL")
    private String SL;
	@JSONField(name = "CPSL")
    private String CPSL;
	@JSONField(name = "BHSJE")
    private String BHSJE;
	@JSONField(name = "SE")
    private String SE;
	@JSONField(name = "FLBM")
    private String FLBM;
	@JSONField(name = "KCJE")
    private String KCJE;
    public void setCPMC(String CPMC) {
         this.CPMC = CPMC;
     }
     public String getCPMC() {
         return CPMC;
     }

    public void setCPXH(String CPXH) {
         this.CPXH = CPXH;
     }
     public String getCPXH() {
         return CPXH;
     }

    public void setCPDW(String CPDW) {
         this.CPDW = CPDW;
     }
     public String getCPDW() {
         return CPDW;
     }

    public void setSL(String SL) {
         this.SL = SL;
     }
     public String getSL() {
         return SL;
     }

    public void setCPSL(String CPSL) {
         this.CPSL = CPSL;
     }
     public String getCPSL() {
         return CPSL;
     }

    public void setBHSJE(String BHSJE) {
         this.BHSJE = BHSJE;
     }
     public String getBHSJE() {
         return BHSJE;
     }

    public void setSE(String SE) {
         this.SE = SE;
     }
     public String getSE() {
         return SE;
     }

    public void setFLBM(String FLBM) {
         this.FLBM = FLBM;
     }
     public String getFLBM() {
         return FLBM;
     }

    public void setKCJE(String KCJE) {
         this.KCJE = KCJE;
     }
     public String getKCJE() {
         return KCJE;
     }

}