package u8c.busiitf.task;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.alibaba.fastjson.util.TypeUtils;

import u8c.server.Base64Converter;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import u8c.vo.goldentax.GTVoucher;
import u8c.vo.goldentax.GTVoucherItem;

public class GTVouchTestTask  implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		Logger.init("hanglianAPI");
		GTVoucher gTVoucher=new GTVoucher();
		List<GTVoucherItem> items=new ArrayList();
		GTVoucherItem item=new GTVoucherItem();
		gTVoucher.setXTLSH("gTVoucher");
		gTVoucher.setKHMC("�й�����Ʋ����չɷ����޹�˾����зֹ�˾");
		gTVoucher.setKHSH("91120000803062678Y"); 
		gTVoucher.setXTLSH("gTVoucher");
		gTVoucher.setKHDZ("����кӶ���ʮһ��·61�� 022-24148888");
		gTVoucher.setKHKHYHZH("�й��������������ʮһ��·֧��0302040609118225850"); 
		gTVoucher.setFPZL("004");
		gTVoucher.setBZ("�й����������������ι�˾ �ִ��Ʋ�һ����");
		gTVoucher.setKPR("��ޱޱ"); 
		gTVoucher.setSKR("���");
		gTVoucher.setFHR("ƽƼ");
		gTVoucher.setQYKHYHZH("�������з�ׯ֧��866680381810001"); 
		gTVoucher.setQYDZDH("�����ж�������ֱ���ϴ�������ô���9��58157023"); 
		item.setCPMC("�ִ��Ʋ�һ���� ���������վ��ͷ�");
		item.setCPXH("");
		item.setCPDW("");
		item.setSL( "6");
		item.setCPSL("");
		item.setBHSJE("2,411.33");
		item.setSE("144.68");
		item.setFLBM("304080299");
		item.setKCJE("0");
		items.add(item);
		gTVoucher.setITEM(items);
		//TypeUtils.compatibleWithJavaBean =false;
		strResult=JSON.toJSONString(gTVoucher,new PascalNameFilter());
		Logger.debug("GTVoucher json:"+strResult);
		strResult=Base64Converter.encode(strResult);
		Logger.debug("GTVoucher jsonBase64:"+strResult);
		return strResult;
	}
}
