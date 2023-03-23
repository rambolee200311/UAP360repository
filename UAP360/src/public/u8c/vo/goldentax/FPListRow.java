package u8c.vo.goldentax;

import java.util.List;
/*
 * 20230323 lijianqiang
 * 获取发票列表或信息 row
 */
public class FPListRow {
	private String xtlsh;//单据号
	private String spid;//税盘ID
	private String qysh;//开票税号
	private String xfyh;//销方银行和账号
	private String xfdz;//销方地址电话
	private String xfmc;
	private String fplxdm;//发票类型代码
	private String fpdm;//发票代码
	private String fphm;//发票号码
	private String kprq;//开票日期
	private String ghdwsbh;//购货单位识别号
	private String ghdwmc;//购货单位名称
	private String ghdwdzdh;//购货单位地址和电话
	private String ghdwyhzh;//购货单位银行账号
	private String hjje;//整张发票的合计不含税金额
	private String hjse;//整张发票的合计税额
	private String jshj;//整张发票的价税合计
	private String url;//电子发票的URL
	private String skr;//收款人
	private String fhr;//复核人
	private String kpr;//开票人
	private String bz;//备注
	private String yfpdm;//原发票代码
	private String yfphm;//原发票号码
	private String tzdbh;//信息表编号
	private String chfpdm;//红冲发票代码
	private String chfphm;//红冲发票号码
	private String spr;//收件人（电子邮箱）
	private String fpdyzt;//发票打印状态
	private String qdbz;//清单标志
	private String xym;//校验码
	private String jqbh;//机器编号
	private String zfbz;//作废标志
	private String zfrq;//作废日期
	private String zhsl;//
	private List<FPListRowFpmx> fpmx;
	
	public String getZhsl() {
		return zhsl;
	}
	public void setZhsl(String zhsl) {
		this.zhsl = zhsl;
	}
	public String getXfmc() {
		return xfmc;
	}
	public void setXfmc(String xfmc) {
		this.xfmc = xfmc;
	}
	public String getXtlsh() {
		return xtlsh;
	}
	public void setXtlsh(String xtlsh) {
		this.xtlsh = xtlsh;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getQysh() {
		return qysh;
	}
	public void setQysh(String qysh) {
		this.qysh = qysh;
	}
	public String getXfyh() {
		return xfyh;
	}
	public void setXfyh(String xfyh) {
		this.xfyh = xfyh;
	}
	public String getXfdz() {
		return xfdz;
	}
	public void setXfdz(String xfdz) {
		this.xfdz = xfdz;
	}
	public String getFplxdm() {
		return fplxdm;
	}
	public void setFplxdm(String fplxdm) {
		this.fplxdm = fplxdm;
	}
	public String getFpdm() {
		return fpdm;
	}
	public void setFpdm(String fpdm) {
		this.fpdm = fpdm;
	}
	public String getFphm() {
		return fphm;
	}
	public void setFphm(String fphm) {
		this.fphm = fphm;
	}
	public String getKprq() {
		return kprq;
	}
	public void setKprq(String kprq) {
		this.kprq = kprq;
	}
	public String getGhdwsbh() {
		return ghdwsbh;
	}
	public void setGhdwsbh(String ghdwsbh) {
		this.ghdwsbh = ghdwsbh;
	}
	public String getGhdwmc() {
		return ghdwmc;
	}
	public void setGhdwmc(String ghdwmc) {
		this.ghdwmc = ghdwmc;
	}
	public String getGhdwdzdh() {
		return ghdwdzdh;
	}
	public void setGhdwdzdh(String ghdwdzdh) {
		this.ghdwdzdh = ghdwdzdh;
	}
	public String getGhdwyhzh() {
		return ghdwyhzh;
	}
	public void setGhdwyhzh(String ghdwyhzh) {
		this.ghdwyhzh = ghdwyhzh;
	}
	public String getHjje() {
		return hjje;
	}
	public void setHjje(String hjje) {
		this.hjje = hjje;
	}
	public String getHjse() {
		return hjse;
	}
	public void setHjse(String hjse) {
		this.hjse = hjse;
	}
	public String getJshj() {
		return jshj;
	}
	public void setJshj(String jshj) {
		this.jshj = jshj;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSkr() {
		return skr;
	}
	public void setSkr(String skr) {
		this.skr = skr;
	}
	public String getFhr() {
		return fhr;
	}
	public void setFhr(String fhr) {
		this.fhr = fhr;
	}
	public String getKpr() {
		return kpr;
	}
	public void setKpr(String kpr) {
		this.kpr = kpr;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getYfpdm() {
		return yfpdm;
	}
	public void setYfpdm(String yfpdm) {
		this.yfpdm = yfpdm;
	}
	public String getYfphm() {
		return yfphm;
	}
	public void setYfphm(String yfphm) {
		this.yfphm = yfphm;
	}
	public String getTzdbh() {
		return tzdbh;
	}
	public void setTzdbh(String tzdbh) {
		this.tzdbh = tzdbh;
	}
	public String getChfpdm() {
		return chfpdm;
	}
	public void setChfpdm(String chfpdm) {
		this.chfpdm = chfpdm;
	}
	public String getChfphm() {
		return chfphm;
	}
	public void setChfphm(String chfphm) {
		this.chfphm = chfphm;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getFpdyzt() {
		return fpdyzt;
	}
	public void setFpdyzt(String fpdyzt) {
		this.fpdyzt = fpdyzt;
	}
	public String getQdbz() {
		return qdbz;
	}
	public void setQdbz(String qdbz) {
		this.qdbz = qdbz;
	}
	public String getXym() {
		return xym;
	}
	public void setXym(String xym) {
		this.xym = xym;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getZfbz() {
		return zfbz;
	}
	public void setZfbz(String zfbz) {
		this.zfbz = zfbz;
	}
	public String getZfrq() {
		return zfrq;
	}
	public void setZfrq(String zfrq) {
		this.zfrq = zfrq;
	}
	public List<FPListRowFpmx> getFpmx() {
		return fpmx;
	}
	public void setFpmx(List<FPListRowFpmx> fpmx) {
		this.fpmx = fpmx;
	}
	
	
}
