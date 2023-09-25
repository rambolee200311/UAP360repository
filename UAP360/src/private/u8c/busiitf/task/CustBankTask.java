package u8c.busiitf.task;

import java.util.LinkedHashMap;

import dm.jdbc.util.StringUtil;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;

public class CustBankTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
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
		String strResult = "";
		// 拿到参数
		LinkedHashMap<String, Object> para = param.getKeyMap();
		String accountcode=(String) para.get("accountcode");
		String custcode=(String) para.get("custcode");
		String custname=(String) para.get("custname");
		
		//获取银行账号主键
		String sql1="select pk_bankaccbas from bd_bankaccbas where dr=0"+" and";
		StringBuilder where1 = new StringBuilder(sql1);
		where1.append(" account='" + accountcode + "' and");
		where1.append(" accountcode='" + accountcode + "' and");
		int index1 = where1.lastIndexOf("and");
		if (index1 > 0) {
            where1 = where1.delete(index1, index1 + 3);
        }
		Logger.info("CustBankTask:"+where1.toString());	
		String pk_bankaccbas=(String)getDao().executeQuery(where1.toString(), new ColumnProcessor());
		if (StringUtil.isEmpty(pk_bankaccbas)) {
			strResult=accountcode+"银行账号不存在";
			Logger.info("CustBankTask:"+strResult);		
			return strResult;
		}
		
		//获取客商基本档案主键
		String sql2="select pk_cubasdoc from bd_cubasdoc where dr=0"+" and";
		StringBuilder where2 = new StringBuilder(sql2);
		where2.append(" custcode='" + custcode + "' and");
		where2.append(" custname='" + custname + "' and");
		int index2 = where2.lastIndexOf("and");
		if (index2 > 0) {
            where2 = where2.delete(index2, index2 + 3);
        }
		Logger.info("CustBankTask:"+where2.toString());	
		
		String pk_cubasdoc=(String)getDao().executeQuery(where2.toString(), new ColumnProcessor());
		if (StringUtil.isEmpty(pk_cubasdoc)) {
			strResult=custcode+" "+custname+"客商档案不存在";
			Logger.info("CustBankTask:"+strResult);		
			return strResult;
		}
		
		//更新客商银行账号
		String sql3="update bd_custbank set pk_cubasdoc='"+pk_cubasdoc+"' where pk_accbank='"+pk_bankaccbas+"'";
		try {
			dao.executeUpdate(sql3);
			strResult="客商银行账号关联成功，";
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			strResult=e.getMessage();
			Logger.error(e);
			e.printStackTrace();
		}
		
		//更新银行账号客商
		String sql4="update bd_bankaccbas set accountname='"+custname+"',unitname='"+custname+"' where pk_bankaccbas='"+pk_bankaccbas+"'";
		try {
			dao.executeUpdate(sql4);
			strResult="银行账号客商名称更新成功，";
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			strResult=e.getMessage();
			Logger.error(e);
			e.printStackTrace();
		}
		Logger.info("CustBankTask:"+strResult);		
		return strResult;
	}
	
}
