package u8c.vo.goldentax;

import java.util.List;
/*
 * 20230323 lijianqiang
 * ���ַ�Ʊ���� data
 */
public class FPSQData {
	private String fpdm;//ԭ��Ʊ����
	private String fphm;//ԭ��Ʊ����
	private int sqlx;//�������ͣ�0������1����
	private String dslbz;//��˰�ʱ�־������Ƕ�˰�ʣ��Ϳգ�������Ƕ�˰�ʣ�����ʵ��˰��
	private String gfsh;//����˰��
	private String gfmc;//��������
	private String xfsh;//����˰��
	private String xfmc;//��������
	private String sqly;//   * ��������
	/*
	 * ��������
     * ��������
     * 0: �ѵֿ�
     * 7: δ�ֿ��޷���֤
     *    δ�ֿ�˰����֤����
     *    δ�ֿ۴��������֤����
     *    ���ﲻ������ֵ˰��Ŀ��Χ
     *
     * ��������
     * 8: ��Ʊ�����򷽾���
     *    ��Ʊ�����ԭ����δ����     
	 */
	private int zsfs;//��˰��ʽ��0: ��ͨ��˰ 2: �����˰
	private String kprq;//ԭ��Ʊ�Ŀ�Ʊ���� ��ʽ YYYYMMDD
	private String zhsl;//�ۺ�˰��
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
