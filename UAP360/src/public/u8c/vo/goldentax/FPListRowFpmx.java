package u8c.vo.goldentax;
/*
 * 20230323 lijianqiang
 * 获取发票列表或信息 fpmx
 */
public class FPListRowFpmx {
	private String spmc;//商品名称
	private String spdj;//单价(是否含税根据HSBZ)
	private String dj;//单价(是否含税根据HSBZ)
	private String sl;//税率
	private String spsl;//数量
	private String je;//不含税金额
	private String se;//税额
	private String hsbz;//含税标记
	private String dw;//计量单位
	private String ggxh;//规格型号
	private String spbm;//分类编码
	private String flbm;
	private String zxbm;//产品代码
	private String yhzcbs;//税收优惠
	private String lslbs;//零税率标识
	private String zzstsgl;//优惠政策
	private String cpdm;
	
	public String getCpdm() {
		return cpdm;
	}
	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}
	public String getDj() {
		return dj;
	}
	public void setDj(String dj) {
		this.dj = dj;
	}
	public String getFlbm() {
		return flbm;
	}
	public void setFlbm(String flbm) {
		this.flbm = flbm;
	}
	public String getSpmc() {
		return spmc;
	}
	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}
	public String getSpdj() {
		return spdj;
	}
	public void setSpdj(String spdj) {
		this.spdj = spdj;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public String getSpsl() {
		return spsl;
	}
	public void setSpsl(String spsl) {
		this.spsl = spsl;
	}
	public String getJe() {
		return je;
	}
	public void setJe(String je) {
		this.je = je;
	}
	public String getSe() {
		return se;
	}
	public void setSe(String se) {
		this.se = se;
	}
	public String getHsbz() {
		return hsbz;
	}
	public void setHsbz(String hsbz) {
		this.hsbz = hsbz;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getGgxh() {
		return ggxh;
	}
	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}
	public String getSpbm() {
		return spbm;
	}
	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}
	public String getZxbm() {
		return zxbm;
	}
	public void setZxbm(String zxbm) {
		this.zxbm = zxbm;
	}
	public String getYhzcbs() {
		return yhzcbs;
	}
	public void setYhzcbs(String yhzcbs) {
		this.yhzcbs = yhzcbs;
	}
	public String getLslbs() {
		return lslbs;
	}
	public void setLslbs(String lslbs) {
		this.lslbs = lslbs;
	}
	public String getZzstsgl() {
		return zzstsgl;
	}
	public void setZzstsgl(String zzstsgl) {
		this.zzstsgl = zzstsgl;
	}
}
