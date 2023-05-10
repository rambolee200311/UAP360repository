package u8c.busiitf.task;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;

import java.util.ArrayList;
import u8c.bs.exception.SecurityException;
import u8c.vo.reject.paymentApply.Transfer;
import u8c.vo.reject.paymentApply.TransferData;
import u8c.vo.reject.paymentApply.TransferDataRoot;
import u8c.vo.reject.paymentApply.TransferHeader;
import u8c.vo.reject.paymentApply.TransferMessage;
import u8c.vo.respmsg.BusiResult;
import u8c.vo.respmsg.RespMsg;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.itf.arap.api.iARAPVoucher;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.BusinessException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import u8c.server.HttpURLConnectionDemo;
public class RejectTransferAPITask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
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
		String vouchid="";
		Logger.init("hanglianAPI");
		// 拿到参数
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate=dateFormat.format(new Date());
		String strDHMS=dateFormat1.format(new Date());
		String strPkCorp=param.getPk_corp();
		LinkedHashMap<String, Object> para = param.getKeyMap();
		String strbilltype = (String) para.get("billtype");
		String strDjbh=(String) para.get("djbh");
		//
		
		/*
		 String strDdate = (String) para.get("ddate"); 
		 if ((strDdate==null)||(strDdate.equals(null))||(strDdate.isEmpty())){
			strDdate=strDate;
		}
		*/
		
		String sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
					" from arap_djzb " +
					" where djdl='fk' and dr=0  and djzt=1 and djlxbm='"+strbilltype+"' and dwbm='"+strPkCorp+"' and";// and djzt=3";
		StringBuilder where = new StringBuilder(sql1);
		if (strDjbh != null && strDjbh.trim().length() > 0) {
            where.append(" djbh='" + strDjbh + "' and");
        }
		int index = where.lastIndexOf("and");
		if (index > 0) {
            where = where.delete(index, index + 3);
        }
		//主表vo
		ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>) getDao().executeQuery(where.toString(), new BeanListProcessor(DJZBHeaderVO.class));
						
		//初始化报文		
		Transfer transfer=new Transfer();
		TransferHeader transferHead=new TransferHeader();
		transferHead.setUserName("username");
		transferHead.setPassWord("password");
		transferHead.setRequestDate(strDate);
		transferHead.setSeqNO(strDHMS);
		transfer.setHeader(transferHead);
		TransferMessage transferMessage=new TransferMessage();
		
		TransferDataRoot transferDataRoot=new TransferDataRoot();
		List<TransferData> listTransferData=new ArrayList();		
		for(DJZBHeaderVO vo : vos){
			vouchid=vo.getVouchid();
			String sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] " +
					"from arap_djfb " +
					"where vouchid='"+vouchid+"';";
			//子表 vob
			ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>) getDao().executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class));
			//客户
			String sql3="select custcode,custname,conaddr,phone1,taxpayerid from bd_cubasdoc where pk_cubasdoc='"+vobs.get(0).getHbbm()+"'";
			CustVO custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));		
			//公司
			sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+"'";
			CorpVO corpVO=(CorpVO)getDao().executeQuery(sql3, new BeanProcessor(CorpVO.class));
			//操作员
			//sql3="select user_name from sm_user where cuserid='"+vo.getLrr()+"'";
			sql3="select user_name from sm_user where cuserid='"+param.getPk_user()+"'";
			String userCode=(String)getDao().executeQuery(sql3, new ColumnProcessor());
			//币种
			sql3="select currtypecode from bd_currtype where pk_currtype='"+vobs.get(0).getBzbm()+"'";
			String currency=(String)getDao().executeQuery(sql3, new ColumnProcessor());
			
			
			//数据data
			TransferData transferData=new TransferData();		
			transferData.setTransferApplyNo(vo.getZyx1().toString());//由业务系统生成与业务系统一致
			transferData.setTransferApplyDate(vo.getDjrq().toString());
			transferData.setTransferBusinType(vo.getDjlxbm());
			transferData.setOperatorCode(userCode);
			transferData.setCurrency(currency);//币种 人民币
			transferData.setTransferAmount(vobs.get(0).getYbye().doubleValue());//转付金额		
			listTransferData.add(transferData);		
		}
		transferDataRoot.setTotalNum(1);
		transferDataRoot.setTransferBusinType(strbilltype);
		transferDataRoot.setTransferData(listTransferData);		
		//加密
		EncryptHelper encryptHelper=new EncryptHelper();
		String encryptString="";
		try {
			encryptString = encryptHelper.encrypt(JSON.toJSONString(transferDataRoot));
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transferMessage.setData(encryptString);
		transfer.setMessage(transferMessage);
		
		//http post
		strResult=HttpURLConnectionDemo.httpPostWithJson(
				//"http://10.0.0.107:38030/api/reject/PaymentApply",
				u8c.server.XmlConfig.getUrl("rejectPaymentApply"),
				JSON.toJSONString(transfer));
		
		Logger.debug(strResult);
		
		JSONObject parameJson = JSONObject.parseObject(strResult);
		RespMsg respMsg=JSON.toJavaObject(parameJson, RespMsg.class);
		
		try {
			strResult=encryptHelper.decrypt(respMsg.getMessage().getData());
			JSONObject resultJson = JSONObject.parseObject(strResult);
			BusiResult busiResult=JSON.toJavaObject(resultJson, BusiResult.class);
			//20230329 delete vouch
			if (busiResult.equals("0")) {
				iARAPVoucher itfARAP=(iARAPVoucher)NCLocator.getInstance().lookup(iARAPVoucher.class);
				itfARAP.delete(vouchid);
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(),e);			
			e.printStackTrace();
		}
		Logger.debug(strResult);
		return strResult;
	}

}
