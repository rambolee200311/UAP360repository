package u8c.vo.busiqry;
/*
 * 20230613
 * �������(body):
 */
public class QueryParam {
	public String sAccountCheckDate;//��������
	public String eAccountCheckDate;//����ֹ��
	public String type;//�ӿ����ͣ��ش����̶�ֵ��1-���� 2-��Ʊ 3-������
	public String zyx1;
	public String zyx2;
	public String zyx3;
	public String getsAccountCheckDate() {
		return sAccountCheckDate;
	}
	public void setsAccountCheckDate(String sAccountCheckDate) {
		this.sAccountCheckDate = sAccountCheckDate;
	}
	public String geteAccountCheckDate() {
		return eAccountCheckDate;
	}
	public void seteAccountCheckDate(String eAccountCheckDate) {
		this.eAccountCheckDate = eAccountCheckDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZyx1() {
		return zyx1;
	}
	public void setZyx1(String zyx1) {
		this.zyx1 = zyx1;
	}
	public String getZyx2() {
		return zyx2;
	}
	public void setZyx2(String zyx2) {
		this.zyx2 = zyx2;
	}
	public String getZyx3() {
		return zyx3;
	}
	public void setZyx3(String zyx3) {
		this.zyx3 = zyx3;
	}
	
}
