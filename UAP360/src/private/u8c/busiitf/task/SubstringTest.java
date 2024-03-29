package u8c.busiitf.task;

import java.util.LinkedHashMap;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;
import u8c.bs.exception.SecurityException;
import u8c.vo.arrival.EncryptHelper;
import u8c.server.XmlConfig;


public class SubstringTest  implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		Logger.init("hanglianAPI");
		LinkedHashMap<String, Object> para = param.getKeyMap();
		//strResult = (String) para.get("temp");
		String type=(String) para.get("type");
		String sql3="select user_name from sm_user where cuserid='"+param.getPk_user()+"'";
		String userCode=(String)getDao().executeQuery(sql3, new ColumnProcessor());
		strResult=param.getPk_user()+"_"+userCode;
		Logger.debug(strResult);
		//jie密
		//strResult="一二三四五六七八九十一二三四五六七八九十";
		
		try {
			//strResult=getSubString(strResult,30);
			strResult=XmlConfig.getQryParam(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		Logger.debug(strResult);
		return strResult;
	}
	public String getSubString(String str, int length) throws Exception {
		int i;	
		int n;	
		byte[] bytes = str.getBytes("Unicode");      //使用Unicode字符集将字符串编码成byte序列	
		i = 2;      //bytes的前两个字节是标志位，bytes[0] = -2, bytes[1] = -1, 故从第二位开始	
		n = 0;	
		for(i=2; i < bytes.length && n < length;i++) {	
			if(i % 2 == 1) {	
				n++;	
			} else {	
				if(bytes[i] != 0) {	
					n++;	
				}	
			}	
		}
	
		//去掉半个汉字
	
		if(i % 2 == 1) {	
			if(bytes[i-1] != 0) {	
				i = i -1;	
			} else {	
				i = i + 1;	
			}	
		}	 
	
		return new String(bytes, 0, i, "Unicode");

	}
}