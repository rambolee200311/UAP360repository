package u8c.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.ep.dj.DJZBItemVO;
import u8c.bs.exception.SecurityException;
import u8c.vo.arrival.ArrivalResult;
import u8c.vo.arrival.ArrivalResultData;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import u8c.vo.goldentax.FPListResult;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.invoice.FPFL;
import u8c.vo.invoice.Invoice;
import u8c.vo.invoice.InvoiceData;
import u8c.vo.invoice.InvoiceDataRoot;
import u8c.vo.invoice.InvoiceHead;
import u8c.vo.invoice.InvoiceMessage;
import u8c.vo.invoice.InvoiceResultData;
import u8c.vo.invoice.InvoiceResult;

import u8c.vo.respmsg.RespMsg;

public class InvoiceUpdateSet {
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	public String updateInvoiceData(String fb_oid,String cUserId,String fpbh,String fprq) throws DAOException {
		Logger.init("hanglianAPI");	
		String strResult="";
		CustVO custVO=null;
		CorpVO corpVO=null;
		String userCode="";
		Double inclusiveMoney=(double) 0;
		Double inclusive=(double) 0;
		Double noInclusiveMoney=(double) 0;
		Double specialMoney=(double) 0;
		String vouchid="";
		
		//初始化报文		
		Invoice invoice=new Invoice();
		InvoiceHead invoiceHead=new InvoiceHead();
		invoiceHead.setUserName("username");
		invoiceHead.setPassWord("password");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate=dateFormat.format(new Date());
		String strDHMS=dateFormat1.format(new Date());
		invoiceHead.setRequestDate(strDate);
		invoiceHead.setSeqNO(strDHMS);
		invoice.setHeader(invoiceHead);
		InvoiceMessage invoiceMessage=new InvoiceMessage();
		
		InvoiceDataRoot invoiceDataRoot=new InvoiceDataRoot();
		try {
			List<InvoiceData> listInvoiceData=new ArrayList();
			int iRow=0;
			String sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] " +
					"from arap_djfb " +
					"where vouchid+'_'+isnull(zyx3,'')='"+fb_oid+"'";// and zyx13='success';";
			ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>) getDao().executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class));
			
			String sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
					" from arap_djzb " +
					" where vouchid='"+vobs.get(0).getVouchid()+"'";
			nc.vo.ep.dj.DJZBHeaderVO vo=(nc.vo.ep.dj.DJZBHeaderVO)getDao().executeQuery(sql1, new BeanProcessor(nc.vo.ep.dj.DJZBHeaderVO.class));
			
			vouchid=vo.getVouchid();
			//客户
			String sql3="select custcode,custname,conaddr,phone1,taxpayerid from bd_cubasdoc where pk_cubasdoc='"+vobs.get(0).getHbbm()+"'";
			custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));		
			//公司
			sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+"'";
			corpVO=(CorpVO)getDao().executeQuery(sql3, new BeanProcessor(CorpVO.class));
			TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(corpVO.getUnitcode());
			GTFPLISTSet gTFPLISTSet=new GTFPLISTSet();
			String token=gTFPLISTSet.getGTToken(tokenGetVO);
			String spid=tokenGetVO.getGTSPID();
			sql2="select doccode,docname from bd_defdoc where pk_defdoclist in (select pk_defdoclist from bd_defdoclist where doclistcode='FPZL') and docname='"+vo.getZyx15()+"'";
			FPFL fPFL=(FPFL)dao.executeQuery(sql2, new BeanProcessor(FPFL.class));	
			String fpfl="004";
			if (fPFL!=null) {
				fpfl=fPFL.getDoccode();
			}
			//操作员
			//sql3="select user_name from sm_user where cuserid='"+vo.getLrr()+"'";
			sql3="select user_name from sm_user where cuserid='"+cUserId+"'";
			userCode=(String)getDao().executeQuery(sql3, new ColumnProcessor());
			/*
			for (DJZBItemVO vob:vobs) {
				inclusive=vob.getSl().toDouble();
				inclusiveMoney+=vob.getJfbbje().toDouble();
				specialMoney+=vob.getJfbbsj().toDouble();
				noInclusiveMoney+=(vob.getJfybwsje().toDouble()*vob.getBbhl().toDouble());
				//inclusive=vob.getSl().toDouble();
				//inclusiveMoney=vob.getJfbbje().toDouble();
				//specialMoney=vob.getJfbbsj().toDouble();
				//noInclusiveMoney=(vob.getJfybwsje().toDouble()*vob.getBbhl().toDouble());
			}*/
			//数据data
			String operationType="1";
			List<FPListResult> fplists=gTFPLISTSet.getFPLISTS(fb_oid, spid, token, fpfl);
			/*
			 * 2023-04-11
			 * lijianqiang
			 * 获取金税开票列表
			 */
			
			for(FPListResult fplist:fplists) {
				InvoiceData invoiceData=new InvoiceData();
				int isVatInvoice=0;
				switch(vo.getZyx4()){
					case "蓝票":
						operationType="1";
						isVatInvoice=0;
						break;
					case "红票":
						operationType="2";
						invoiceData.setReverseInvoiceNo(vo.getZyx8());
						isVatInvoice=1;
						break;
					case "作废":
						operationType="3";
						invoiceData.setReverseInvoiceNo(vo.getZyx8());
						isVatInvoice=0;
						break;
					default:
						operationType="1";
				}
				invoiceData.setInvoiceSeqNo(vo.getDjbh());//发票流水号 U8C发票号
				//invoiceData.setInvoiceNo(vo.getZyx2());//发票号  金税发票号
				invoiceData.setAdviceNote(vo.getZyx1());//通知书编号 开票通知书编号（唯一）
				//invoiceData.setAdviceType(Integer.valueOf(vo.getZyx7()));//通知书类型 业务类型 (1-保司经纪费,2-客户经纪费,3-咨询经纪费,4-转付保费到账,5-再保结算到站,6-理赔款结算,7-再保理赔,8-退保经纪费）
				/*
				 * 2023-02-15
				 * adviceType 整数型改成字符串类型
				*/
				invoiceData.setAdviceType(vo.getDjlxbm());
				invoiceData.setInvoiceType("增值税");//发票类型 （普通发票、增值税）
				invoiceData.setCurrency("CNY");//币种 人民币
				
				inclusiveMoney=Double.valueOf(fplist.getRows().get(0).getJshj());
				invoiceData.setInclusiveMoney(inclusiveMoney);//发票含税金额 金额	
				inclusive=Double.valueOf(fplist.getRows().get(0).getZhsl());
				invoiceData.setInclusive(inclusive);//含税税率 整数（6，13）
				specialMoney=Double.valueOf(fplist.getRows().get(0).getHjse());
				invoiceData.setSpecialMoney(specialMoney);//增值税金额 税额
				noInclusiveMoney=Double.valueOf(fplist.getRows().get(0).getHjje());
				invoiceData.setNoInclusiveMoney(noInclusiveMoney);//不含税金额 金额
				
				invoiceData.setPayerCode(custVO.getCustcode());//支付方编号 u8c客商编号
				invoiceData.setPayerName(custVO.getCustname());//支付方名称 u8c客商名称
				//invoiceData.setInvoiceTime(vo.getZyx3());//发票日期
				
				invoiceData.setInvoiceNo(fplist.getRows().get(0).getFpdm()+fplist.getRows().get(0).getFphm());//(vobs.get(0).getZyx14());//发票号  金税发票号
				invoiceData.setInvoiceTime(fplist.getRows().get(0).getKprq());//(vobs.get(0).getZyx15());//发票日期
				invoiceData.setVatPayerId(fplist.getRows().get(0).getGhdwsbh());//纳税人识别号 u8c税号
				
				invoiceData.setIsVatInvoice(isVatInvoice);//是否红票 是否红票
				invoiceData.setRemark(vo.getScomment());				
				invoiceData.setComCode(corpVO.getUnitcode());//经办人归属机构 公司编码
				invoiceData.setComName(corpVO.getUnitname());
				invoiceData.setOperatorCode(userCode);	
				invoiceData.setOperationType(operationType);
				
				listInvoiceData.add(invoiceData);
				iRow++;
			}
			invoiceDataRoot.setTotalNum(iRow);
			//invoiceDataRoot.setInvoiceBusinType("1");
			invoiceDataRoot.setInvoiceData(listInvoiceData);
		}catch(Exception e) {
			Logger.error("Invoice err:"+e.getMessage(),e);	
			e.printStackTrace();
		}
		//加密
		EncryptHelper encryptHelper=new EncryptHelper();
		String encryptString="";
		try {
			strResult=JSON.toJSONString(invoiceDataRoot);
			Logger.debug("Invoice DataRoot:"+strResult);			
			encryptString = encryptHelper.encrypt(strResult);
			Logger.debug("Invoice encryptString:"+encryptString);	
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			
			Logger.error("Invoice err:"+e.getMessage(),e);			
			e.printStackTrace();
		}
		invoiceMessage.setData(encryptString);
		invoice.setMessage(invoiceMessage);
		
		//http post
		strResult=HttpURLConnectionDemo.httpPostWithJson(
				//"http://10.0.0.107:38030/api/agent/InvoiceApi",
				u8c.server.XmlConfig.getUrl("InvoiceApi"),
				JSON.toJSONString(invoice));
		Logger.debug("Invoice Result:"+strResult);
		JSONObject parameJson = JSONObject.parseObject(strResult);
		RespMsg respMsg=JSON.toJavaObject(parameJson, RespMsg.class);
		
		try {
			strResult=encryptHelper.decrypt(respMsg.getMessage().getData());
			Logger.debug("Invoice Resultencrypt:"+strResult);
			
			strResult=updateInvoice(strResult,vouchid);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error("Invoice err:"+e.getMessage(),e);				
			e.printStackTrace();
		}
		Logger.debug(strResult);
		return strResult;
	}
	
	public String updateInvoice(String strResult,String vouchid) {
		
		JSONObject parameJson = JSONObject.parseObject(strResult);
		InvoiceResultData arrivalResultData = JSON.toJavaObject(parameJson, InvoiceResultData.class);
		for (InvoiceResult arrivalResult : arrivalResultData.getInvoiceData()) {
			//String cvouchid = arrivalResult.getArrivalRegiCode();
			String sql = "update arap_djzb set zyx11='" + arrivalResult.getResultCode() + "',zyx12='"
					+ arrivalResult.getResultDesc() + "'" + " where vouchid='" + vouchid + "'";
			String sql3="update bd_cubasdoc set taxpayerid=b.zyx14,def1=b.zyx10,def2=b.zyx13,def3=b.zyx9"
					+" from bd_cubasdoc a inner join" 
					+" (select a.vouchid,a.dwbm,a.djbh,b.hbbm,a.zyx9,a.zyx10,a.zyx13,a.zyx14 from arap_djzb a" 
					+" inner join arap_djfb b on a.vouchid=b.vouchid " 
					+" where a.djdl='ys' and a.dr=0) b" 
					+" on a.pk_cubasdoc=b.hbbm where b.vouchid='"+vouchid+"'";
			strResult=arrivalResult.getResultDesc();
			try {
				dao.executeUpdate(sql);
				dao.executeUpdate(sql3);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				strResult=e.getMessage();
				e.printStackTrace();
			}
		}
		return strResult;
	}
}
