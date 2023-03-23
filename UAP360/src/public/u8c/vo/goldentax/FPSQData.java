package u8c.vo.goldentax;

import java.util.List;
/*
 * 20230323 lijianqiang
 * 红字发票申请 data
 */
public class FPSQData {
	private String fpdm;//原发票代码
	private String fphm;//原发票号码
	private int sqlx;//申请类型：0正常，1逾期
	private String dslbz;//多税率标志，如果是多税率，就空，如果不是多税率，就用实际税率
	private String gfsh;//购方税号
	private String gfmc;//购方名称
	private String xfsh;//销方税号
	private String xfmc;//销方名称
	private String sqly;//   * 申请理由
	/*
	 * 申请理由
     * 购方申请
     * 0: 已抵扣
     * 7: 未抵扣无法认证
     *    未抵扣税号认证不符
     *    未抵扣代码号码认证不符
     *    货物不属于增值税项目范围
     *
     * 销方申请
     * 8: 开票有误购买方拒收
     *    开票有误等原因尚未交付     
	 */
	private int zsfs;//征税方式：0: 普通征税 2: 差额征税
	private String kprq;//原发票的开票日期 格式 YYYYMMDD
	private String zhsl;//综合税率
	private List<FPSQDataFyxm> fyxm;
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
	public int getSqlx() {
		return sqlx;
	}
	public void setSqlx(int sqlx) {
		this.sqlx = sqlx;
	}
	public String getDslbz() {
		return dslbz;
	}
	public void setDslbz(String dslbz) {
		this.dslbz = dslbz;
	}
	public String getGfsh() {
		return gfsh;
	}
	public void setGfsh(String gfsh) {
		this.gfsh = gfsh;
	}
	public String getGfmc() {
		return gfmc;
	}
	public void setGfmc(String gfmc) {
		this.gfmc = gfmc;
	}
	public String getXfsh() {
		return xfsh;
	}
	public void setXfsh(String xfsh) {
		this.xfsh = xfsh;
	}
	public String getXfmc() {
		return xfmc;
	}
	public void setXfmc(String xfmc) {
		this.xfmc = xfmc;
	}
	public String getSqly() {
		return sqly;
	}
	public void setSqly(String sqly) {
		this.sqly = sqly;
	}
	public int getZsfs() {
		return zsfs;
	}
	public void setZsfs(int zsfs) {
		this.zsfs = zsfs;
	}
	public String getKprq() {
		return kprq;
	}
	public void setKprq(String kprq) {
		this.kprq = kprq;
	}
	public String getZhsl() {
		return zhsl;
	}
	public void setZhsl(String zhsl) {
		this.zhsl = zhsl;
	}
	public List<FPSQDataFyxm> getFyxm() {
		return fyxm;
	}
	public void setFyxm(List<FPSQDataFyxm> fyxm) {
		this.fyxm = fyxm;
	}
	
}
