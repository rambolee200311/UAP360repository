package u8c.vo.goldentax;
import java.util.List;
public class KPJGResult {

    private String Result;
    private String Code;
    private String Message;
    private String TEXT;
    private String XTLSH;
    private String FPZL;
    private String FPHM;
    private String KPRQ;
    private String MW;
    private String XYM;
    private String JQBH;
    private String SWQM;
    private String URL;
    private String PDF;
    private String FPZT;
    private List<KPJGRow> Rows;
    public void setResult(String Result) {
         this.Result = Result;
     }
     public String getResult() {
         return Result;
     }

    public void setCode(String Code) {
         this.Code = Code;
     }
     public String getCode() {
         return Code;
     }

    public void setMessage(String Message) {
         this.Message = Message;
     }
     public String getMessage() {
         return Message;
     }

    public void setTEXT(String TEXT) {
         this.TEXT = TEXT;
     }
     public String getTEXT() {
         return TEXT;
     }

    public void setXTLSH(String XTLSH) {
         this.XTLSH = XTLSH;
     }
     public String getXTLSH() {
         return XTLSH;
     }

    public void setFPZL(String FPZL) {
         this.FPZL = FPZL;
     }
     public String getFPZL() {
         return FPZL;
     }

    public void setFPHM(String FPHM) {
         this.FPHM = FPHM;
     }
     public String getFPHM() {
         return FPHM;
     }

    public void setKPRQ(String KPRQ) {
         this.KPRQ = KPRQ;
     }
     public String getKPRQ() {
         return KPRQ;
     }

    public void setMW(String MW) {
         this.MW = MW;
     }
     public String getMW() {
         return MW;
     }

    public void setXYM(String XYM) {
         this.XYM = XYM;
     }
     public String getXYM() {
         return XYM;
     }

    public void setJQBH(String JQBH) {
         this.JQBH = JQBH;
     }
     public String getJQBH() {
         return JQBH;
     }

    public void setSWQM(String SWQM) {
         this.SWQM = SWQM;
     }
     public String getSWQM() {
         return SWQM;
     }

    public void setURL(String URL) {
         this.URL = URL;
     }
     public String getURL() {
         return URL;
     }

    public void setPDF(String PDF) {
         this.PDF = PDF;
     }
     public String getPDF() {
         return PDF;
     }

    public void setFPZT(String FPZT) {
         this.FPZT = FPZT;
     }
     public String getFPZT() {
         return FPZT;
     }
	public List<KPJGRow> getRows() {
		return Rows;
	}
	public void setRows(List<KPJGRow> rows) {
		Rows = rows;
	}
     
}