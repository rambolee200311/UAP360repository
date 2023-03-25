package u8c.vo.goldentax;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class GTVoucher {
	
	@JSONField(name = "XTLSH")
    private String XTLSH;
	@JSONField(name = "KHMC")
    private String KHMC;
	@JSONField(name = "KHSH")
    private String KHSH;
	@JSONField(name = "KHDZ")
    private String KHDZ;
	@JSONField(name = "KHKHYHZH")
    private String KHKHYHZH;
	@JSONField(name = "FPZL")
    private String FPZL;
	@JSONField(name = "BZ")
    private String BZ;
	@JSONField(name = "KPR")
    private String KPR;
	@JSONField(name = "SKR")
    private String SKR;
	@JSONField(name = "FHR")
    private String FHR;
	@JSONField(name = "QYKHYHZH")
    private String QYKHYHZH;
	@JSONField(name = "QYDZDH")
    private String QYDZDH;
	@JSONField(name = "YFPHM")
	private String YFPHM;
	@JSONField(name = "XXBBH")
	private String XXBBH;
	@JSONField(name = "ITEM")
    private List<GTVoucherItem> ITEM;
	
    public void setXTLSH(String XTLSH) {
         this.XTLSH = XTLSH;
     }
     public String getXTLSH() {
         return XTLSH;
     }

    public void setKHMC(String KHMC) {
         this.KHMC = KHMC;
     }
     public String getKHMC() {
         return KHMC;
     }

    public void setKHSH(String KHSH) {
         this.KHSH = KHSH;
     }
     public String getKHSH() {
         return KHSH;
     }

    public void setKHDZ(String KHDZ) {
         this.KHDZ = KHDZ;
     }
     public String getKHDZ() {
         return KHDZ;
     }

    public void setKHKHYHZH(String KHKHYHZH) {
         this.KHKHYHZH = KHKHYHZH;
     }
     public String getKHKHYHZH() {
         return KHKHYHZH;
     }

    public void setFPZL(String FPZL) {
         this.FPZL = FPZL;
     }
     public String getFPZL() {
         return FPZL;
     }

    public void setBZ(String BZ) {
         this.BZ = BZ;
     }
     public String getBZ() {
         return BZ;
     }

    public void setKPR(String KPR) {
         this.KPR = KPR;
     }
     public String getKPR() {
         return KPR;
     }

    public void setSKR(String SKR) {
         this.SKR = SKR;
     }
     public String getSKR() {
         return SKR;
     }

    public void setFHR(String FHR) {
         this.FHR = FHR;
     }
     public String getFHR() {
         return FHR;
     }

    public void setQYKHYHZH(String QYKHYHZH) {
         this.QYKHYHZH = QYKHYHZH;
     }
     public String getQYKHYHZH() {
         return QYKHYHZH;
     }

    public void setQYDZDH(String QYDZDH) {
         this.QYDZDH = QYDZDH;
     }
     public String getQYDZDH() {
         return QYDZDH;
     }

    public void setITEM(List<GTVoucherItem> ITEM) {
         this.ITEM = ITEM;
     }
     public List<GTVoucherItem> getITEM() {
         return ITEM;
     }
     public String toString() {
    	 return JSONObject.toJSONString(this);
     }
	public String getYFPHM() {
		return YFPHM;
	}
	public void setYFPHM(String yFPHM) {
		YFPHM = yFPHM;
	}
	public String getXXBBH() {
		return XXBBH;
	}
	public void setXXBBH(String xXBBH) {
		XXBBH = xXBBH;
	}
     
}