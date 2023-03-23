package u8c.busiitf.task;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import u8c.server.HttpURLConnectionDemo;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.goldentax.TokenResult;

public class GTTokenTestTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		Logger.init("hanglianAPI");
		LinkedHashMap<String, Object> para = param.getKeyMap();
		strResult = (String) para.get("temp");
		//strResult=u8c.server.XmlConfig.getUrl(strResult);
		TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(strResult);
		//strResult=JSON.toJSONString(tokenGetVO);
		Logger.debug("tokenGetVO:"+JSON.toJSONString(tokenGetVO));
		String data="";
		Map<String, Object> headparams = new HashMap<String, Object>();
		headparams.put("UserName", tokenGetVO.getGTUserName());
		headparams.put("Password1", tokenGetVO.getGTPassword1());
		try {
			strResult=HttpURLConnectionDemo.goldenTax(tokenGetVO.getGTTokeUrl(), tokenGetVO.getGTTokeContentType(), headparams, data);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.debug("tokenGetResult:"+strResult);
		//JSONObject parameJson = JSONObject.parseObject(strResult);
		//TokenResult tokenResult=JSON.toJavaObject(parameJson,TokenResult.class);
		TokenResult tokenResult=JSONObject.parseObject(strResult,TokenResult.class);
		strResult=tokenResult.getID();
		Logger.debug("tokenGetID:"+strResult);
		return strResult;
	}
}
