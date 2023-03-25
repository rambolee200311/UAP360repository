package u8c.busiitf.task;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import u8c.server.Base64Converter;
import u8c.server.GTVoucherSet;
import u8c.server.HttpURLConnectionDemo;
import u8c.vo.entity.CorpVO;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.goldentax.TokenResult;
import u8c.vo.goldentax.FPListResult;
import u8c.vo.goldentax.FPListRow;
import u8c.vo.goldentax.FPListRowFpmx;
import u8c.vo.goldentax.FPSQData;
import u8c.vo.goldentax.FPSQDataFyxm;
import u8c.vo.goldentax.FPSQResult;

public class GTVoucherFPSQTask  implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
	private BaseDAO dao;

	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult = "";
		String xtlsh="";
		String sql1="";
		String vouchid="";
		Logger.init("hanglianAPI");
		try {
			String strPkCorp = param.getPk_corp();
			String pk_user=param.getPk_user();
			// 拿到参数
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String strDate=dateFormat.format(new Date());
			LinkedHashMap<String, Object> para = param.getKeyMap();			
			String strDjbh=(String) para.get("djbh");
			String strbilltype = (String) para.get("billtype");
			String strDdate = (String) para.get("ddate");

			sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
					" from arap_djzb " +
					" where isnull(zyx11,'')!='0' and djdl='ys' and djlxbm='"+strbilltype+"' and dr=0 and isnull(zyx1,'')!='' and dwbm='"+strPkCorp+"'";
			//if (!(strDjbh==null)&&(!strDjbh.equals(null))&&(!strDjbh.isEmpty())){
				sql1+=" and djbh='"+strDjbh+"'";
			//}		
			if ((strDdate==null)||(strDdate.equals(null))||(strDdate.isEmpty())){
				 strDdate=strDate; 
			}
			sql1+=" and djrq='"+strDdate+"'";
			
			//应收单主表
			ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>) getDao().executeQuery(sql1, new BeanListProcessor(DJZBHeaderVO.class));
			for(DJZBHeaderVO vo : vos){
				vouchid=""+vo.getVouchid();
				xtlsh=vo.getZyx16();
				
				//debug xtlsh
				//xtlsh="0001F810000000009BRH";
				
				FPListResult fPListResult=getFPListResult(strPkCorp,xtlsh);				
				if (fPListResult.getResult().equals("1")) {
					for (FPListRow row:fPListResult.getRows()) {
						//strResult=row.getFpdm()+"_"+row.getFphm();
						String YFPHM=""+row.getFpdm()+row.getFphm();
						strResult=getFPSQData(strPkCorp,row);
						FPSQResult fPSQResult=JSONObject.parseObject(strResult, FPSQResult.class);
						if (fPSQResult!=null) {
							if (fPSQResult.getResult().equals("1")) {
								/*
								 * debug
								 * */
								String XXBBH=""+fPSQResult.getXxbbh();
								sql1="update arap_djzb set zyx17='"+XXBBH+"',zyx8='"+YFPHM+"' where vouchid='" +vouchid+"' and isnull(zyx17,'')=''"; 								
								getDao().executeUpdate(sql1);
								strResult="红票申请成功,信息表编号:"+fPSQResult.getXxbbh();
								GTVoucherSet gTVoucherSet=new  GTVoucherSet();
								gTVoucherSet.uploadGTVoucher(vouchid, strPkCorp, pk_user,YFPHM,XXBBH);
								
							}
						}
						
					}
				}else if (fPListResult.getResult().equals("0")) {
					strResult=fPListResult.getMessage();
				}
			}
		}catch(Exception e) {
			strResult=e.getMessage();
			Logger.error("GTVoucherFPSQTask error:"+strResult,e);
		}
		return strResult;
	}
	
	private FPListResult getFPListResult(String strPkCorp,String xtlsh) {
		FPListResult fPListResult=new FPListResult();
		String strResult = "";
		try {
			//公司
			String sql2="select unitcode,unitname from bd_corp where pk_corp='"+strPkCorp+"'";
			CorpVO corpVO=(CorpVO)getDao().executeQuery(sql2, new BeanProcessor(CorpVO.class));	
			TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(corpVO.getUnitcode());
			
			//token
			String strToken=getGTToken(tokenGetVO);
			
			//post parameters
			Map<String, Object> headparams = new HashMap<String, Object>();
			headparams.put("token", strToken);
			headparams.put("mxbz", 1);
			headparams.put("spid", tokenGetVO.getGTSPID());
			headparams.put("xtlsh", xtlsh);//"0001F810000000009BR5");
			
			String data="";
			//http post
			try {
				strResult=HttpURLConnectionDemo.goldenTax("http://mycst.cn/NEWKP/KPGL/FPLIST", tokenGetVO.getGTTokeContentType(), headparams, data);
				Logger.debug("GTVoucherFPLIST result:"+JSON.toJSONString(strResult));
				fPListResult=JSONObject.parseObject(strResult, FPListResult.class);
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				strResult=e.getMessage();
				e.printStackTrace();
				Logger.error("GTVoucherFPLIST error:"+e.getMessage());
				fPListResult.setTotal(0);
				fPListResult.setResult("0");
				fPListResult.setMessage(strResult);
				return fPListResult;
			}
		}catch(Exception e) {
			strResult=e.getMessage();
			e.printStackTrace();
			Logger.error("GTVoucherFPLIST error:"+strResult,e);
			fPListResult.setTotal(0);
			fPListResult.setResult("0");
			fPListResult.setMessage(strResult);
			return fPListResult;
		}
		return fPListResult;
	}
	
	private String getFPSQData(String strPkCorp,FPListRow row) throws DAOException {
		String strResult="";
		Logger.init("hanglianAPI");
		//公司
		String sql2="select unitcode,unitname from bd_corp where pk_corp='"+strPkCorp+"'";
		CorpVO corpVO=(CorpVO)getDao().executeQuery(sql2, new BeanProcessor(CorpVO.class));	
		TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(corpVO.getUnitcode());
		
		//token
		String strToken=getGTToken(tokenGetVO);
		try {
			FPSQData fPSQData=new FPSQData();
			fPSQData.setFpdm(row.getFpdm());
			fPSQData.setFphm(row.getFphm());
			fPSQData.setSqlx(0);
			fPSQData.setDslbz("0");
			fPSQData.setGfsh(row.getGhdwsbh());
			fPSQData.setGfmc(row.getGhdwmc());
			fPSQData.setXfsh(row.getQysh());
			fPSQData.setXfmc(row.getXfmc());
			fPSQData.setSqly("8");//销方申请
			fPSQData.setZsfs(0);//普通征税
			fPSQData.setKprq(row.getKprq());
			fPSQData.setZhsl(row.getZhsl());
			List<FPSQDataFyxm> fyxms=new ArrayList();
			for(FPListRowFpmx fpmx:row.getFpmx()) {
				FPSQDataFyxm fyxm=new FPSQDataFyxm();
				fyxm.setSpmc(fpmx.getSpmc());
				fyxm.setSpbm(fpmx.getFlbm());
				fyxm.setSpdj(fpmx.getDj());
				fyxm.setSl(getTax(fpmx.getSl()));//除100
				fyxm.setSpsl(fpmx.getSpsl());				
				fyxm.setJe(getNegative(fpmx.getJe()));//负数
				fyxm.setSe(getNegative(fpmx.getSe()));//负数
				fyxm.setGgxh(fpmx.getGgxh());
				fyxm.setZxbm(fpmx.getCpdm());
				fyxm.setYhzcbs(0);
				fyxm.setLslbs(0);
				fyxm.setHsbz("N");
				fyxms.add(fyxm);
			}
			fPSQData.setFyxm(fyxms);
			
			//post parameters
			Map<String, Object> headparams = new HashMap<String, Object>();
			headparams.put("token", strToken);
			headparams.put("spid", tokenGetVO.getGTSPID());
			Logger.debug("FPSQData map:"+JSON.toJSONString(headparams));
			
			strResult=JSON.toJSONString(fPSQData);
			Logger.debug("FPSQData data:"+strResult);
			
			strResult=Base64Converter.encode(strResult);
			String data="data="+"["+strResult+"]";
			Logger.debug("FPSQData encode:"+data);
			
			try {
				strResult=HttpURLConnectionDemo.goldenTax("http://mycst.cn/NEWKP/HZFP/FPSQ", tokenGetVO.getGTTokeContentType(), headparams, data);
				Logger.debug("FPSQData result:"+JSON.toJSONString(strResult));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.error("FPSQData error:"+e.getMessage());
			}
			
		}catch(Exception e) {
			strResult=e.getMessage();
			Logger.error("FPSQData error:"+strResult,e);
		}
		return strResult;
	}
	
 	public String getGTToken(TokenGetVO tokenGetVO){
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
		Logger.debug("tokenGetResult:"+strResult);
		//JSONObject parameJson = JSONObject.parseObject(strResult);
		//TokenResult tokenResult=JSON.toJavaObject(parameJson,TokenResult.class);
		TokenResult tokenResult=JSONObject.parseObject(strResult,TokenResult.class);
		strResult=tokenResult.getID();
		Logger.debug("tokenGetID:"+strResult);
		return strResult;
	}
 	//得到负数
 	private String getNegative(String je) {
 		try {
 			Double dJe=Double.parseDouble(je);
 			BigDecimal bJe=new BigDecimal(dJe);
 			dJe=bJe.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 			return Double.toString(dJe*-1);
 		}catch(Exception e){
 			return "-"+je;
 		}	
 	}
 	//得到税率
 	private String getTax(String se) {
 		try {
 			Double dSe=Double.parseDouble(se);
 			dSe=dSe/100;
 			BigDecimal bSe=new BigDecimal(dSe);
 			dSe=bSe.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 			return Double.toString(dSe);
 		}catch(Exception e){
 			return se;
 		}	
 	}
}
