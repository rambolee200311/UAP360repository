package u8c.busiitf.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.jfree.util.Log;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;

import java.util.ArrayList;
import u8c.bs.exception.SecurityException;
import u8c.vo.transfer.Transfer;
import u8c.vo.transfer.TransferData;
import u8c.vo.transfer.TransferDataRoot;
import u8c.vo.transfer.TransferHeader;
import u8c.vo.transfer.TransferMessage;
import u8c.vo.transfer.TransferResult;
import u8c.vo.transfer.TransferResultData;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.arrival.TaskParameterVO;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import u8c.vo.respmsg.RespMsg;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.BusinessException;
import com.alibaba.fastjson.JSON;
import u8c.server.HttpURLConnectionDemo;
import u8c.server.TransferAPISet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TransferAPITask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
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
		String strTemp = "";
		String vouchid = "";
		String pk_user = "";
		// 拿到参数
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = dateFormat.format(new Date());
		String strDHMS = dateFormat1.format(new Date());
		String strPkCorp = param.getPk_corp();
		LinkedHashMap<String, Object> para = param.getKeyMap();
		String strbilltype = (String) para.get("billtype");
		String strDdate = (String) para.get("ddate");
		String strDjbh = (String) para.get("djbh");
		
		if ((strDdate == null) || (strDdate.equals("")) || (strDdate.isEmpty())) {
			strDdate = strDate;
		}
		String sql1 = "select vouchid from arap_djzb " +
		// " where paydate='"+strDdate+"' and djdl='fk' and djlxbm='"+strbilltype+"' and
		// dr=0 and djzt=3 and dwbm='"+strPkCorp+"'";
				"where djdl='fk' and djlxbm='" + strbilltype + "' and dr=0 and djzt=3 and dwbm='" + strPkCorp
				+ "' and djrq='"+strDdate+"'";
		
		if (!(strDjbh == null) && (!strDjbh.equals(null)) && (!strDjbh.isEmpty())) {
			sql1 += " and djbh='" + strDjbh + "'";
		}
		//vouchid = (String) getDao().executeQuery(sql1, new ColumnProcessor());
		ArrayList<TaskParameterVO> taskParameterVOs=(ArrayList<TaskParameterVO>)getDao().executeQuery(sql1, new BeanListProcessor(TaskParameterVO.class));
		TransferAPISet transferAPISet = new TransferAPISet();
		
		for (TaskParameterVO tpvo:taskParameterVOs) {
			strResult = transferAPISet.uploadBusiSys(tpvo.getVouchid(), strPkCorp, pk_user);
		}

		/*
		 * //主表vo ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>)
		 * getDao().executeQuery(sql1, new BeanListProcessor(DJZBHeaderVO.class));
		 * 
		 * //初始化报文 Transfer transfer=new Transfer(); TransferHeader transferHead=new
		 * TransferHeader(); transferHead.setUserName("username");
		 * transferHead.setPassWord("password"); transferHead.setRequestDate(strDate);
		 * transferHead.setSeqNO(strDHMS); transfer.setHeader(transferHead);
		 * TransferMessage transferMessage=new TransferMessage();
		 * 
		 * TransferDataRoot transferDataRoot=new TransferDataRoot(); List<TransferData>
		 * listTransferData=new ArrayList(); for(DJZBHeaderVO vo : vos){ String
		 * sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] "
		 * + "from arap_djfb " + "where vouchid='"+vo.getVouchid()+"';"; //子表 vob
		 * ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>)
		 * getDao().executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class)); //客户
		 * String
		 * sql3="select custcode,custname,conaddr,phone1,taxpayerid from bd_cubasdoc where pk_cubasdoc='"
		 * +vobs.get(0).getHbbm()+"'"; CustVO custVO=(CustVO)getDao().executeQuery(sql3,
		 * new BeanProcessor(CustVO.class)); //公司
		 * sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+
		 * "'"; CorpVO corpVO=(CorpVO)getDao().executeQuery(sql3, new
		 * BeanProcessor(CorpVO.class)); //操作员
		 * //sql3="select user_name from sm_user where cuserid='"+vo.getLrr()+"'";
		 * sql3="select user_name from sm_user where cuserid='"+param.getPk_user()+"'";
		 * String userCode=(String)getDao().executeQuery(sql3, new ColumnProcessor());
		 * //币种
		 * sql3="select currtypecode from bd_currtype where pk_currtype='"+vobs.get(0).
		 * getBzbm()+"'"; String currency=(String)getDao().executeQuery(sql3, new
		 * ColumnProcessor()); //数据data TransferData transferData=new TransferData();
		 * transferData.setTransferApplyNo(vo.getZyx1().toString());//由业务系统生成与业务系统一致
		 * transferData.setPaymentReviewNo(corpVO.getUnitcode()+"_"+vo.getDjlxbm()+"_"+
		 * vo.getDjbh());//U8C划拨记账流水号
		 * transferData.setInsuranceCode(custVO.getCustcode());//U8C系统客商编号
		 * transferData.setInsuranceName(custVO.getCustname());//U8C系统客商编号名称
		 * transferData.setCurrency(currency);//币种 人民币
		 * transferData.setTransferAmount(vobs.get(0).getJfbbje().doubleValue());//转付金额
		 * transferData.setAcceptDateTime(vo.getPaydate().toString());//付款日期
		 * transferData.setTransferComCode(corpVO.getUnitcode());//经办人归属机构 公司编码
		 * //transferData.setTransferComName("航联保险经纪有限公司");
		 * transferData.setTransferPersonCode(userCode);
		 * transferData.setRemark(vobs.get(0).getZy());//财务付款单备注
		 * transferData.setTransferState("0");//0-成功，1-失败，N-原因不明
		 * listTransferData.add(transferData); } transferDataRoot.setTotalNum(1);
		 * 
		 * 2023-02-15 transferBusinTyp 取值范围改变
		 * 
		 * transferDataRoot.setTransferBusinType(strbilltype);
		 * transferDataRoot.setTransferData(listTransferData);
		 * 
		 * //加密 EncryptHelper encryptHelper=new EncryptHelper(); String
		 * encryptString=""; try { strResult=JSON.toJSONString(transferDataRoot);
		 * Log.debug("transfer transferDataRoot:"+strResult); encryptString =
		 * encryptHelper.encrypt(strResult);
		 * Log.debug("transfer encryptString:"+encryptString);
		 * 
		 * } catch (SecurityException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); Log.error("transfer err:"+e.getMessage()); }
		 * transferMessage.setData(encryptString); transfer.setMessage(transferMessage);
		 * 
		 * //http post strResult=HttpURLConnectionDemo.httpPostWithJson(
		 * //"http://10.0.0.107:38030/api/agent/TransferApi",
		 * u8c.server.XmlConfig.getUrl("TransferApi"), JSON.toJSONString(transfer));
		 * Log.debug("transfer result:"+strResult); JSONObject parameJson =
		 * JSONObject.parseObject(strResult); RespMsg
		 * respMsg=JSON.toJavaObject(parameJson, RespMsg.class);
		 * 
		 * try { strResult=encryptHelper.decrypt(respMsg.getMessage().getData());
		 * 
		 * 20230303 回写结果到zyx11,zyx12
		 * 
		 * updateTransfer(strResult); } catch (SecurityException e) { // TODO
		 * Auto-generated catch block
		 * 
		 * e.printStackTrace(); }
		 */
		return strResult;
	}

}
