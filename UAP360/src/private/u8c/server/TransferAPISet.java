package u8c.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import u8c.bs.exception.SecurityException;
import u8c.serverset.ITransferAPISet;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import u8c.vo.respmsg.RespMsg;
import u8c.vo.transfer.Transfer;
import u8c.vo.transfer.TransferData;
import u8c.vo.transfer.TransferDataRoot;
import u8c.vo.transfer.TransferHeader;
import u8c.vo.transfer.TransferMessage;
import u8c.vo.transfer.TransferResult;
import u8c.vo.transfer.TransferResultData;

public class TransferAPISet implements ITransferAPISet{
	private BaseDAO dao; 
	public TransferAPISet() {		
			dao = new BaseDAO();
	}
	@Override
	public String uploadBusiSys (String vouchid,String strPkCorp,String pk_user){
		String strResult="";
		String zyx1="";
		Logger.init("hanglianAPI");
		String strTemp = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String strDate=dateFormat.format(new Date());
			String strDHMS=dateFormat1.format(new Date());
			
			String sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
					" from arap_djzb " +"where djdl='fk' and dr=0 and djzt=3 and vouchid='"+vouchid+"'";
			//主表vo
			ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>)dao.executeQuery(sql1, new BeanListProcessor(DJZBHeaderVO.class));
					
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
				zyx1=vo.getVouchid();
				String sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] " +
						"from arap_djfb " +
						"where vouchid='"+vo.getVouchid()+"';";
				//子表 vob
				ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>)dao.executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class));
				//客户
				String sql3="select custcode,custname,conaddr,phone1,taxpayerid from bd_cubasdoc where pk_cubasdoc='"+vobs.get(0).getHbbm()+"'";
				CustVO custVO=(CustVO)dao.executeQuery(sql3, new BeanProcessor(CustVO.class));		
				//公司
				sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+"'";
				CorpVO corpVO=(CorpVO)dao.executeQuery(sql3, new BeanProcessor(CorpVO.class));
				//操作员
				//sql3="select user_name from sm_user where cuserid='"+vo.getLrr()+"'";
				sql3="select user_name from sm_user where cuserid='"+pk_user+"'";
				String userCode=(String)dao.executeQuery(sql3, new ColumnProcessor());
				//币种
				sql3="select currtypecode from bd_currtype where pk_currtype='"+vobs.get(0).getBzbm()+"'";
				String currency=(String)dao.executeQuery(sql3, new ColumnProcessor());
				//数据data			
				TransferData transferData=new TransferData();		
				transferData.setTransferApplyNo(vo.getZyx1().toString());//由业务系统生成与业务系统一致
				transferData.setPaymentReviewNo(corpVO.getUnitcode()+"_"+vo.getDjlxbm()+"_"+vo.getDjbh());//U8C划拨记账流水号
				transferData.setInsuranceCode(custVO.getCustcode());//U8C系统客商编号
				transferData.setInsuranceName(custVO.getCustname());//U8C系统客商编号名称
				transferData.setCurrency(currency);//币种 人民币
				transferData.setTransferAmount(vobs.get(0).getJfbbje().doubleValue());//转付金额
				transferData.setAcceptDateTime(vo.getPaydate().toString());//付款日期
				transferData.setTransferComCode(corpVO.getUnitcode());//经办人归属机构 公司编码
				//transferData.setTransferComName("航联保险经纪有限公司");
				transferData.setTransferPersonCode(userCode);
				transferData.setRemark(vobs.get(0).getZy());//财务付款单备注
				transferData.setTransferState("0");//0-成功，1-失败，N-原因不明
				listTransferData.add(transferData);
				
				transferDataRoot.setTransferBusinType(vo.getDjlxbm());
			}
			transferDataRoot.setTotalNum(1);
			/*
			 * 2023-02-15
			 * transferBusinTyp 取值范围改变
			 */
			
			transferDataRoot.setTransferData(listTransferData);
			
			//加密
			EncryptHelper encryptHelper=new EncryptHelper();
			String encryptString="";
			try {
				strResult=JSON.toJSONString(transferDataRoot);
				Log.debug("transfer transferDataRoot:"+strResult);
				encryptString = encryptHelper.encrypt(strResult);
				Log.debug("transfer encryptString:"+encryptString);
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.error("transfer err:"+e.getMessage());
			}
			transferMessage.setData(encryptString);
			transfer.setMessage(transferMessage);
			
			//http post
			strResult=HttpURLConnectionDemo.httpPostWithJson(
					//"http://10.0.0.107:38030/api/agent/TransferApi",
					u8c.server.XmlConfig.getUrl("TransferApi"),
					JSON.toJSONString(transfer));
			Log.debug("transfer result:"+strResult);
			JSONObject parameJson = JSONObject.parseObject(strResult);
			RespMsg respMsg=JSON.toJavaObject(parameJson, RespMsg.class);
			
			try {
				strTemp=encryptHelper.decrypt(respMsg.getMessage().getData());
				/*
				 * 20230303
				 * 回写结果到zyx11,zyx12
				 */
				strResult=updateTransfer(strTemp,zyx1);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}catch(Exception e) {
			Logger.error("uploadBusiSys err:"+e.getMessage());
			strResult=e.getMessage();
		}
		
		return strResult;
	}
	@Override
	public String updateTransfer(String strResult,String zyx1) {
		JSONObject parameJson = JSONObject.parseObject(strResult);
		TransferResultData transferResultData = JSON.toJavaObject(parameJson, TransferResultData.class);
		for (TransferResult transferResult : transferResultData.getTransferData()) {
			String vouchid = transferResult.getTransferApplyNo();
			strResult=transferResult.getResultDesc();
			String sql = "update arap_djzb set zyx11='" + transferResult.getResultCode() + "',zyx12='"
					+ transferResult.getResultDesc() + "'" + " where vouchid='" + zyx1 + "'";
			try {
				dao.executeUpdate(sql);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				strResult=e.getMessage();
			}
		}
		return strResult;
	}
}
