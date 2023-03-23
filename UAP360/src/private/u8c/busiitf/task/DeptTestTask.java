package u8c.busiitf.task;

import java.util.LinkedHashMap;

import nc.bs.bd.CorpBO;
import nc.bs.dao.BaseDAO;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.bd.CorpVO;
import nc.vo.bm.bm_009.DeptVO;
import nc.vo.pub.BusinessException;
public class DeptTestTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	private BaseDAO dao;

	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String corpID="";
		String deptID="";
		String strResult="";
		LinkedHashMap<String, Object> para = param.getKeyMap();
		corpID = (String) para.get("corp");
		deptID= (String) para.get("dept");
		corpID=param.getPk_corp().toString();
		//strResult=u8c.server.XmlConfig.getUrl(strResult);
		//strResult=u8c.server.XmlConfig.getDeptXml(corpID, deptID);
		//strResult=JSON.toJSONString(busiXml);
		CorpBO corpBO=new CorpBO();
		CorpVO [] corpvos=corpBO.getSelfAndAllChildCorp(corpID);
		
		for (CorpVO corpvo:corpvos) {
			if (corpvo.getPk_corp().equals(corpID)) {
				strResult=corpvo.getUnitcode()+"_"+corpvo.getUnitname()+"_"+corpvo.getPk_corp();
			}
			
		}
		String sql1="select pk_deptdoc,deptcode,deptname from bd_deptdoc where pk_corp='"+corpID+"' and deptcode='"+deptID+"'";		
		DeptVO deptvo=(DeptVO)(getDao().executeQuery(sql1, new BeanProcessor(DeptVO.class)));
		strResult=deptvo.getAttributeValue("deptcode")+"_"+deptvo.getAttributeValue("deptname")+"_"+deptvo.getAttributeValue("pk_deptdoc");
		return strResult;
	}
}
