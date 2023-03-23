package u8c.busiitf.task;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.pub.BusinessException;
import u8c.server.HttpURLConnectionDemo;
import u8c.server.InvoiceUpdateSet;
import u8c.vo.entity.CorpVO;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.goldentax.TokenResult;
import u8c.vo.goldentax.Zyx3;
import u8c.vo.goldentax.KPJGResult;
import u8c.vo.invoice.FPFL;
/*
 * 20230306
 * 异步获取金税开票结果
 */
public class GTKPJGtask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException{
		String strResult="";
		String vouchid="";
		String sql1="";
		Logger.init("hanglianAPI");
		// 拿到参数
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate=dateFormat.format(new Date());
		LinkedHashMap<String, Object> para = param.getKeyMap();
		String strDjbh=(String) para.get("djbh");
		String strbilltype = (String) para.get("billtype");
		String strDdate = (String) para.get("ddate");
		String strPkCorp=param.getPk_corp();
		
		sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
				" from arap_djzb " +
				" where isnull(zyx11,'')!='0' and djdl='ys' and djlxbm='"+strbilltype+"' and dr=0 and isnull(zyx1,'')!='' and dwbm='"+strPkCorp+"'";
		if (!(strDjbh==null)&&(!strDjbh.equals(null))&&(!strDjbh.isEmpty())){
			sql1+=" and djbh='"+strDjbh+"'";
		}		
		if ((strDdate==null)||(strDdate.equals(null))||(strDdate.isEmpty())){
			 strDdate=strDate; 
		}
		sql1+=" and djrq='"+strDdate+"'";
		
		//应收单主表
		ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>) getDao().executeQuery(sql1, new BeanListProcessor(DJZBHeaderVO.class));	
		for(DJZBHeaderVO vo : vos){
			vouchid=vo.getVouchid();
			InvoiceUpdateSet invoiceUpdateSet=new InvoiceUpdateSet();
			String sql2="select vouchid+'_'+isnull(zyx3,'') zyx3 from arap_djfb where vouchid='"+vouchid+"' group by vouchid,isnull(zyx3,'')";
			ArrayList<Zyx3> zyx3s =(ArrayList<Zyx3>) getDao().executeQuery(sql2, new BeanListProcessor(Zyx3.class));
			for(Zyx3 cZyx3:zyx3s) {
				CorpVO corpVO=null;
				//公司
				String sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+"'";
				corpVO=(CorpVO)getDao().executeQuery(sql3, new BeanProcessor(CorpVO.class));
				TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(corpVO.getUnitcode());
				String url="http://mycst.cn/NEWKP/KPGL/KPJG";
				
				sql2="select doccode,docname from bd_defdoc where pk_defdoclist in (select pk_defdoclist from bd_defdoclist where doclistcode='FPZL') and docname='"+vo.getZyx15()+"'";
				FPFL fPFL=(FPFL)getDao().executeQuery(sql2, new BeanProcessor(FPFL.class));	
				
				
				Map<String, Object> headparams = new HashMap<String, Object>();
				
				headparams.put("xtlsh", cZyx3.getZyx3());
				headparams.put("spid", tokenGetVO.getGTSPID());
				headparams.put("fpzl", fPFL.getDoccode());
				
				Logger.debug("GTVoucherKpjg map:"+JSON.toJSONString(headparams));
				
				String strToken=getGTToken(tokenGetVO);
				String data="token="+strToken;
				Logger.debug("GTVoucherKpjg data:"+data);
				try {
					strResult=HttpURLConnectionDemo.goldenTax(url, tokenGetVO.getGTTokeContentType(), headparams, data);
					Logger.debug("GTVoucherKpjg result:"+JSON.toJSONString(strResult));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.error("GTVoucherKpjg error:"+e.getMessage());
				}
				
				String sql4="";
				KPJGResult kPJGResult=JSONObject.parseObject(strResult,KPJGResult.class);
				if (kPJGResult.getResult().equals("1")) {//成功
					invoiceUpdateSet.updateInvoiceData(kPJGResult.getXTLSH(), 
							param.getPk_user(),
							kPJGResult.getFPHM(),
							kPJGResult.getKPRQ());
					sql4="update arap_djfb set zyx13='success',zyx14='"+kPJGResult.getFPHM()+"',zyx15='"+kPJGResult.getKPRQ()+"'"
							+" where vouchid+'_'+isnull(zyx3,'')='"+kPJGResult.getXTLSH()+"' and isnull(zyx13,'')!='success'";
				}else if (kPJGResult.getResult().equals("0")) {//失败
					sql4="update arap_djfb set zyx13='err',zyx14='错误信息:"+kPJGResult.getMessage()+"'"
							+" where vouchid+'_'+isnull(zyx3,'')='"+kPJGResult.getXTLSH()+"' and isnull(zyx13,'')!='success'";
				}
				
				try {
					getDao().executeUpdate(sql4);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.error("GTVoucherKpjg err:"+e.getMessage());
				}
			}
			
			
		}
		
		Logger.debug("GTVoucherKpjg result:"+strResult);
		//Log.getInstance("hanglianAPI").debug(strResult);
		return strResult;
	}
	private String getGTToken(TokenGetVO tokenGetVO){
		String strResult="";
		Logger.init("hanglianAPI");		
		
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
		Logger.debug("GTVoucherKpjg :"+strResult);
		//JSONObject parameJson = JSONObject.parseObject(strResult);
		//TokenResult tokenResult=JSON.toJavaObject(parameJson,TokenResult.class);
		TokenResult tokenResult=JSONObject.parseObject(strResult,TokenResult.class);
		strResult=tokenResult.getID();
		Logger.debug("GTVoucherKpjg :"+strResult);
		return strResult;
	}
}
