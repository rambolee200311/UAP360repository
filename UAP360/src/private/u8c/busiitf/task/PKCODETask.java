package u8c.busiitf.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.bd.CorpVO;
import nc.vo.pub.BusinessException;
import nc.jdbc.framework.generator.IdGenerator;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.bs.pub.billcodemanage.BillcodeGenerater;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.bd.corp.ICorpQry;
import nc.ui.pub.beans.MessageDialog;

public class PKCODETask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	private ICorpQry iCorpQry = (ICorpQry)NCLocator.getInstance().lookup(ICorpQry.class.getName());
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="NULL";
		Logger.init("hanglianAPI");
		try {
			String strPkCorp = param.getPk_corp();
			BillcodeGenerater gene = new BillcodeGenerater();
			String[] pks = new SequenceGenerator().generate(strPkCorp, 1);//批量pk
			//String[] codes = gene.getBatchBillCodes("F0",strPkCorp, null, 1);//批量单据号
			//strResult=pks[0].toString()+"_"+codes[0].toString();
			String billNo=new HYPubBO().getBillNo("F0",strPkCorp, null, null);
			
			strResult=billNo;
			CorpVO[] corpVOs = this.iCorpQry.queryCorpVOByWhereSQL(" pk_corp ='1003'");
			if (corpVOs != null && corpVOs.length > 0) {
	            JSONObject json = new JSONObject();
	            JSONArray bills = new JSONArray();
	            CorpVO[] arr$ = corpVOs;
	            int len$ = corpVOs.length;
	            for(int i$ = 0; i$ < len$; ++i$) {
	                CorpVO corpVO = arr$[i$];
	                JSONArray bodys = new JSONArray();
	                JSONObject head = new JSONObject();
	                head.put("pk_corp", corpVO.getPk_corp());
	                head.put("unitcode", corpVO.getUnitcode());
	                head.put("unitname", corpVO.getUnitname());
	                head.put("father_pk_corp", corpVO.getFathercorp());
	                if (corpVO.getFathercorp()!=null &&corpVO.getFathercorp().trim().length()!=0) {
	                	CorpVO[] fathercorpVOs = this.iCorpQry.queryCorpVOByWhereSQL(" pk_corp ='"+corpVO.getFathercorp()+"'");
	                	JSONObject body = new JSONObject();
	                	body.put("father_pk_corp", fathercorpVOs[0].getPk_corp());
	                	body.put("father_unitcode", fathercorpVOs[0].getUnitcode());
	                	body.put("father_unitname", fathercorpVOs[0].getUnitname());
	                	bodys.add(body);	                	
	                }
	                head.put("fathercorp", bodys);
	                bills.add(head);	                
	            }
	            json.put("billnum", billNo);
                json.put("data", bills);
                json.put("key", pks[0].toString());
                Logger.debug("corps josn:"+JSON.toJSONString(json));
			}
		}catch(Exception e) {
			strResult=e.getMessage();
			Logger.error(e);
		}
		return strResult;
	}

}
