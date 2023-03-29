package u8c.busiitf.task;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

import nc.bs.framework.common.NCLocator;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.pub.taskcenter.IBackgroundWorkPlugin;
import nc.itf.bd.currinfo.ICurrinfoQueryService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.bd.b20.CurrtypeVO;
import nc.vo.bd.b21.CurrinfoVO;
import nc.vo.bd.b21.CurrrateVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import net.sf.json.JSONObject;
import nc.bs.dao.BaseDAO;
import nc.vo.bd.b21.CurrRateObject;
public class CurrencyRateTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
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
		try {
			LinkedHashMap<String, Object> para = param.getKeyMap();
			String code=(String)para.get("code");
			String beginDate=(String)para.get("beginDate");
			String endDate=(String)para.get("endDate");
			
			String sql = "select currtypecode,currtypename,pk_currtype from bd_currtype where dr=0"+" and";
			StringBuilder where = new StringBuilder(sql);
            if (code != null && code.trim().length() > 0) {
                where.append(" currtypecode='" + code + "' and");
            }
            int index = where.lastIndexOf("and");
            if (index > 0) {
                where = where.delete(index, index + 3);
            }
            CurrinfoVO currType=(CurrinfoVO)getDao().executeQuery(where.toString(), new BeanProcessor(CurrinfoVO.class));
            if (currType!=null) {
            	ArrayList<CurrRateObject> cro=null;
				ICurrinfoQueryService service = (ICurrinfoQueryService)NCLocator.getInstance().lookup(ICurrinfoQueryService.class.getName());
				
				String pk_currtype=currType.getPk_currtype();
				
				
				String currinfo = "select pk_currinfo,pk_corp,oppcurrtype,pk_currtype,ratedigit,pk_exratescheme from bd_currinfo where dr=0 and pk_currtype='"+pk_currtype+"'";
	            CurrinfoVO result = (CurrinfoVO)getDao().executeQuery(currinfo, new BeanProcessor(CurrinfoVO.class));
	            if (result!=null) {
	            	String pk_exratescheme=result.getPk_exratescheme();
	            	String oppcurrtype=result.getOppcurrtype();
	            	cro=service.getCurrRate(pk_exratescheme, pk_currtype,oppcurrtype, UFDate.getDate(beginDate), UFDate.getDate(endDate));
	            	//strResult=JSON.toJSONString(cro);
	            	strResult=""+cro.get(0).getRate();
	            }			
				
	            //strResult=JSON.toJSONString(cro);
			
            }
			
		}catch(Exception e) {
			strResult=e.getMessage();
		}
		return strResult;
	}
}
