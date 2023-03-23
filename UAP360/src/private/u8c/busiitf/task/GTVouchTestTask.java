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
		gTVoucher.setKHMC("中国人民财产保险股份有限公司天津市分公司");
		gTVoucher.setKHSH("91120000803062678Y"); 
		gTVoucher.setXTLSH("gTVoucher");
		gTVoucher.setKHDZ("天津市河东区十一经路61号 022-24148888");
		gTVoucher.setKHKHYHZH("中国工商银行天津市十一经路支行0302040609118225850"); 
		gTVoucher.setFPZL("004");
		gTVoucher.setBZ("中国航空器材有限责任公司 仓储财产一切险");
		gTVoucher.setKPR("刘薇薇"); 
		gTVoucher.setSKR("邵婵");
		gTVoucher.setFHR("平萍");
		gTVoucher.setQYKHYHZH("招商银行方庄支行866680381810001"); 
		gTVoucher.setQYDZDH("北京市东城区东直门南大街中青旅大厦9层58157023"); 
		item.setCPMC("仓储财产一切险 货物运输险经纪费");
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
