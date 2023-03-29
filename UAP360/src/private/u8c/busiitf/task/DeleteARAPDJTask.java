package u8c.busiitf.task;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.pub.taskcenter.IBackgroundWorkPlugin;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.pub.BusinessException;
import u8c.vo.arrival.TaskParameterVO;
import nc.itf.arap.api.iARAPVoucher;
import nc.jdbc.framework.processor.BeanListProcessor;

public class DeleteARAPDJTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
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
			LinkedHashMap<String, Object> para = param.getKeyMap();
			String strbilltype = (String) para.get("billtype");
			String strDdate = (String) para.get("ddate");
			String strDjbh = (String) para.get("djbh");
			
			String sql="select vouchid from arap_djzb where dr=0 and";
			StringBuilder where = new StringBuilder(sql);
            if (strbilltype != null && strbilltype.trim().length() > 0) {
                where.append(" djlxbm='" + strbilltype + "' and");
            }
            if (strDjbh != null && strDjbh.trim().length() > 0) {
                where.append(" djbh='" + strDjbh + "' and");
            }
            if (strDdate != null && strDdate.trim().length() > 0) {
                where.append(" djrq='" + strDdate + "' and");
            }
            if (strPkCorp != null && strPkCorp.trim().length() > 0) {
                where.append(" dwbm='" + strPkCorp + "' and");
            }
            int index = where.lastIndexOf("and");
            if (index > 0) {
                where = where.delete(index, index + 3);
            }
            List<String> list = new ArrayList<String>();
            ArrayList<TaskParameterVO> taskParameterVOs=(ArrayList<TaskParameterVO>)getDao().executeQuery(where.toString(), new BeanListProcessor(TaskParameterVO.class));
            for (TaskParameterVO tpvo:taskParameterVOs) {
            	String vouchid=tpvo.getVouchid();
            	list.add(vouchid);
            }
			String[] IDs=list.toArray(new String[list.size()]);
			iARAPVoucher itfARAP=(iARAPVoucher)NCLocator.getInstance().lookup(iARAPVoucher.class);
			DJZBVO[] djzbVOs=itfARAP.queryByID(IDs);
			for(DJZBVO djzbVO:djzbVOs) {
				strResult=djzbVO.getHeadVO().m_zyx5;
				//Logger.debug(JSON.toJSONString(djzbVO.getHeadVO()));
				//Logger.debug(JSON.toJSONString(djzbVO.getChildrenVO()));
				itfARAP.delete(djzbVO.getHeadVO().getVouchid());
			}
			
		}catch(Exception e) {
			strResult=e.getMessage();
			Logger.error(e);
		}
		return strResult;
	}
}
