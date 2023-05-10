package u8c.busiitf.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import u8c.server.GTFPLISTSet;
import u8c.vo.goldentax.FPListResult;

public class FPlistTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	private BaseDAO dao;

	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		Logger.init("hanglianAPI");
		String strResult = "FPlistTask";
		
		LinkedHashMap<String, Object> para = param.getKeyMap();
		String xtlsh = (String) para.get("xtlsh");
		String spid = (String) para.get("spid");
		String token = (String) para.get("token");
		String fpzl = (String) para.get("fpzl");
		GTFPLISTSet gTFPLISTSet=new GTFPLISTSet();
		List<FPListResult> fplists=gTFPLISTSet.getFPLISTS(xtlsh, spid, token, fpzl);
		
		
		Logger.debug("FPlistTask"+JSON.toJSONString(fplists));
		if (fplists==null || fplists.size()<=0) {
			strResult="empty list";
		}
		
		return strResult;
	}
}
