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
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import u8c.bs.exception.SecurityException;
import u8c.serverset.IArrivalAPISet;
import u8c.vo.arrival.Arrival;
import u8c.vo.arrival.ArrivalData;
import u8c.vo.arrival.ArrivalDataRoot;
import u8c.vo.arrival.ArrivalHeader;
import u8c.vo.arrival.ArrivalMessage;
import u8c.vo.arrival.ArrivalResult;
import u8c.vo.arrival.ArrivalResultData;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import u8c.vo.respmsg.RespMsg;

public class ArrivalAPISet implements IArrivalAPISet {
	private BaseDAO dao; 
	public ArrivalAPISet() {		
			dao = new BaseDAO();
	}
	@Override
	public String uploadBusiSys (String vouchid,String strPkCorp,String pk_user,String strDdate){
		String strResult="";
		String zyx1="";
		String zyx2="";
		String vVouchid="";
		Logger.init("hanglianAPI");
		String strTemp = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String strDate = dateFormat.format(new Date());
			String strDHMS = dateFormat1.format(new Date());
			
			String sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
						" from arap_djzb " +
						" where isnull(zyx11,'')!='0' and djdl='sk' and dr=0";
			sql1+=" and vouchid='"+vouchid+"'";		
			
			//初始化报文
			Arrival arrival=new Arrival();
			ArrivalHeader arrivalHeader=new ArrivalHeader();
			arrivalHeader.setUserName("username");
			arrivalHeader.setPassWord("password");
			if ((strDdate == null) || (strDdate.equals("")) || (strDdate.isEmpty())) {
				strDdate = strDate;
			}
			arrivalHeader.setRequestDate(strDdate);
			arrivalHeader.setSeqNO(strDHMS);
			arrival.setHeader(arrivalHeader);
			ArrivalMessage arrivalMessage=new ArrivalMessage();
			
			ArrivalDataRoot arrivalDataRoot=new ArrivalDataRoot();
			List<ArrivalData> listArrivalData=new ArrayList();
			
			//主表vo
			ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>)dao.executeQuery(sql1, new BeanListProcessor(DJZBHeaderVO.class));
			
			//if(strbilltype.equals("F2-01")){
				int iRow=0;
				for(DJZBHeaderVO vo : vos){
					vVouchid=vo.getVouchid();
					zyx1=vo.getZyx1();
					String sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] " +
							"from arap_djfb " +
							"where vouchid='"+vVouchid+"';";
					//子表 vob
					ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>)dao.executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class));
					for(nc.vo.ep.dj.DJZBItemVO vob : vobs){
						
						
						//客户
						String hbbm=vob.getHbbm();
						//到款退回客户
						if (vo.getDjlxbm().equals("F2-08")) {
							zyx2=vo.getZyx2();
							sql2="select hbbm from arap_djfb where vouchid in (select vouchid from arap_djzb where vouchid='"+zyx2+"' and djdl='sk' and dr=0)";
							hbbm=(String)dao.executeQuery(sql2, new ColumnProcessor());							
						}
						String sql3="select custcode,custname,conaddr,phone1,taxpayerid from bd_cubasdoc where pk_cubasdoc='"+hbbm+"'";
						CustVO custVO=(CustVO)dao.executeQuery(sql3, new BeanProcessor(CustVO.class));		
						//公司
						sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+"'";
						CorpVO corpVO=(CorpVO)dao.executeQuery(sql3, new BeanProcessor(CorpVO.class));
						//操作员
						//sql3="select user_name from sm_user where cuserid='"+vo.getLrr()+"'";
						sql3="select user_name from sm_user where cuserid='"+pk_user+"'";
						String userCode=(String)dao.executeQuery(sql3, new ColumnProcessor());
						//币种
						sql3="select currtypecode from bd_currtype where pk_currtype='"+vob.getBzbm()+"'";
						String currency=(String)dao.executeQuery(sql3, new ColumnProcessor());
						
						
						switch(vo.getDjlxbm().toString()) {							
							case "F2-01": 
								//到账日期
								sql3="select rq from arap_dztz where pk_dztz='"+vob.getDdhh()+"'";
								break;
							case "F2-02":
								//到账日期
								sql3="select rq from arap_dztz where pk_dztz='"+vob.getDdhh()+"'";
								break;
							case "F2-08":
								//付款日期
								sql3="select paydate from arap_djzb where zyx1='"+zyx1+"' and zyx2='"+zyx2+"' and djdl='fk' and dr=0";
								break;
							case "F2-09":
								//单据日期
								sql3="select djrq from arap_djzb where vouchid='"+vVouchid+"'";
								break;
							default:
								//单据日期
								sql3="select djrq from arap_djzb where vouchid='"+vVouchid+"'";
								break;
						}
						/*
						//到账日期
						sql3="select rq from arap_dztz where pk_dztz='"+vob.getDdhh()+"'";
						
						//到款退回日期
						if (vo.getDjlxbm().equals("F2-08")) {
							sql3="select paydate from arap_djzb where zyx1='"+zyx1+"' and zyx2='"+zyx2+"' and djdl='fk' and dr=0";
						}
						if (vo.getDjlxbm().equals("F2-09")) {
							sql3="select djrq from arap_djzb where vouchid='"+vVouchid+"'";
						}
						//Logger.info("sql rq:"+sql3);
						 * 
						 */
						String rq=(String)dao.executeQuery(sql3, new ColumnProcessor());
						
						String acCode="";
						String bkName="";
						if (vob.getDfyhzh()!=null && vob.getDfyhzh().trim().length()!=0) {
							sql3="select accountcode from bd_bankaccbas where pk_bankaccbas='"+vob.getDfyhzh()+"'";
							acCode=(String)dao.executeQuery(sql3, new ColumnProcessor());
							sql3="select pk_bankdoc from bd_bankaccbas where pk_bankaccbas='"+vob.getDfyhzh()+"'";
							String pk_bankdoc=(String)dao.executeQuery(sql3, new ColumnProcessor());
							if (pk_bankdoc!=null && pk_bankdoc.trim().length()!=0) {
								sql3="select bankdocname from bd_bankdoc where pk_bankdoc='"+pk_bankdoc+"'";
								bkName=(String)dao.executeQuery(sql3, new ColumnProcessor());
							}
						}
						
						
						//数据data
						ArrivalData arrivalData=new ArrivalData();
						arrivalData.setArrivalRegiCode(vob.getDdhh());
						arrivalData.setPayerCode(custVO.getCustcode());
						arrivalData.setPayerName(custVO.getCustname());
						arrivalData.setArrivalPurpose(vob.getZy());
						arrivalData.setArrivalAmount(vob.getYbye().doubleValue());
						arrivalData.setComCode(corpVO.getUnitcode());
						arrivalData.setComName(corpVO.getUnitname());
						arrivalData.setOperatorCode(userCode);
						arrivalData.setCurrency(currency);
						arrivalData.setArrivalRegiCode(vo.getVouchid());
						arrivalData.setArrivalDate(rq);
						arrivalData.setAcCode(acCode);
						arrivalData.setBkName(bkName);
						listArrivalData.add(arrivalData);
						
						
					}			
					
					//2023-06-29 到账转户，根据银行账号性质返回djlxbm
					String Djlxbm=vo.getDjlxbm();
					
					
					if (Djlxbm.trim().equals("F2-08")) {
						if (vobs.get(0).getBfyhzh()!=null&&vobs.get(0).getBfyhzh().trim().length()!=0) {
							String sql4="select def1 from bd_bankaccbas where pk_bankaccbas='"+vobs.get(0).getBfyhzh()+"'";
							String def1=(String)dao.executeQuery(sql4, new ColumnProcessor());
							if (def1!=null && def1.trim().length()!=0) {
								Djlxbm=def1.trim();
							}
						}
					}
					
					arrivalDataRoot.setArrivalBusinType(Djlxbm);
					iRow++;
				}
				
				arrivalDataRoot.setTotalNum(iRow);
				/*
				switch(strbilltype){
					case "F2-01":
						arrivalDataRoot.setArrivalBusinType("2");
						break;
					case "F2-02":
						arrivalDataRoot.setArrivalBusinType("1");
						break;	
				}
				*/
				/*
				 * 2023-02-15
				 * arrivalBusinType 字符串类型不变 取值范围改变
				 */
				
				
				arrivalDataRoot.setArrivalData(listArrivalData);
				
				//加密
				EncryptHelper encryptHelper=new EncryptHelper();
				String encryptString=JSON.toJSONString(arrivalDataRoot);
				Logger.info("arrivalDataRoot:"+encryptString);
				strTemp = encryptString;
				try {
					encryptString = encryptHelper.encrypt(encryptString);
					
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arrivalMessage.setData(encryptString);
				//解密
				/*try {
					encryptString=encryptHelper.decrypt(encryptString);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//httpPost
				
				arrival.setMessage(arrivalMessage);
				strResult=HttpURLConnectionDemo.httpPostWithJson(
						//"http://10.0.0.107:38030/api/agent/ArrivalApi",
						u8c.server.XmlConfig.getUrl("ArrivalApi"),
						JSON.toJSONString(arrival));
				JSONObject parameJson = JSONObject.parseObject(strResult);
				RespMsg respMsg=JSON.toJavaObject(parameJson, RespMsg.class);
				
				try {
					strTemp=encryptHelper.decrypt(respMsg.getMessage().getData());					
					parameJson = JSONObject.parseObject(strTemp);
					
					
					/*
					 * 20220320
					 * 回写结果到zyx11,zyx12
					 */
					strResult=updateArrival(strTemp,vVouchid);
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
	public String updateArrival(String strResult,String vouchid) {
		
		JSONObject parameJson = JSONObject.parseObject(strResult);
		ArrivalResultData arrivalResultData = JSON.toJavaObject(parameJson, ArrivalResultData.class);
		for (ArrivalResult arrivalResult : arrivalResultData.getArrivalData()) {
			String cvouchid = arrivalResult.getArrivalRegiCode();
			String sql = "update arap_djzb set zyx11='" + arrivalResult.getResultCode() + "',zyx12='"
					+ arrivalResult.getResultDesc() + "'" + " where vouchid='" + cvouchid + "'";
			strResult=arrivalResult.getResultDesc();
			try {
				dao.executeUpdate(sql);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				strResult=e.getMessage();
				e.printStackTrace();
			}
		}
		return strResult;
	}
}
