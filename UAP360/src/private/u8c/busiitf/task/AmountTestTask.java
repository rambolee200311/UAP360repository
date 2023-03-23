package u8c.busiitf.task;

import java.util.LinkedHashMap;

import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;

public class AmountTestTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String corpID="";		
		LinkedHashMap<String, Object> para = param.getKeyMap();
		corpID = (String) para.get("corp");		
		//strResult=u8c.server.XmlConfig.getUrl(strResult);
		String strResult=Double.toString( u8c.server.XmlConfig.getInvoiceAmount(corpID));
		//strResult=JSON.toJSONString(busiXml);
		return strResult;
	}
}
