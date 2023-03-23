package u8c.busiitf.task;

import java.util.LinkedHashMap;

import com.alibaba.fastjson.JSON;

import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import u8c.vo.xmldata.BusiXml;

public class XmlConfigTestTask   implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		LinkedHashMap<String, Object> para = param.getKeyMap();
		strResult = (String) para.get("temp");
		//strResult=u8c.server.XmlConfig.getUrl(strResult);
		BusiXml busiXml=u8c.server.XmlConfig.getBusiXml(strResult);
		strResult=JSON.toJSONString(busiXml);
		return strResult;
	}
}
