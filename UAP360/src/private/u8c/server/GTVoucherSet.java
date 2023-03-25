package u8c.server;

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
import com.alibaba.fastjson.serializer.PascalNameFilter;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.BusinessException;
import u8c.bs.exception.SecurityException;
import u8c.server.Base64Converter;
import u8c.server.HttpURLConnectionDemo;
import u8c.server.InvoiceUpdateSet;
import u8c.serverset.IGTVoucherSet;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import u8c.vo.goldentax.GTVoucher;
import u8c.vo.goldentax.GTVoucherItem;
import u8c.vo.goldentax.ResultVO;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.goldentax.TokenResult;
import u8c.vo.goldentax.Zyx3;
import u8c.vo.invoice.Invoice;
import u8c.vo.invoice.InvoiceData;
import u8c.vo.invoice.InvoiceDataRoot;
import u8c.vo.invoice.InvoiceHead;
import u8c.vo.invoice.InvoiceMessage;
import u8c.vo.invoice.InvoiceResult;
import u8c.vo.invoice.InvoiceResultData;
import u8c.vo.respmsg.RespMsg;
import u8c.vo.goldentax.SucessListVO;
import u8c.vo.goldentax.ErrListVO;
import u8c.vo.goldentax.GTFLBM;
import u8c.vo.invoice.FPFL;

public class GTVoucherSet implements IGTVoucherSet{
	private BaseDAO dao; 
	public GTVoucherSet() {		
			dao = new BaseDAO();
		
	}
	@Override
	public String uploadGTVoucher(String vouchid,String strPkCorp,String pk_user,String YFPHM,String XXBBH){
		String strResult="";
		Logger.init("hanglianAPI");
		try {
			String sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
						" from arap_djzb " +
						" where djdl='ys' and dr=0 and isnull(zyx1,'')!=''";			
				sql1+=" and vouchid='"+vouchid+"'";
				
			//主表vo
			ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>)dao.executeQuery(sql1, new BeanListProcessor(DJZBHeaderVO.class));	
			
			//公司
			String sql2="select unitcode,unitname from bd_corp where pk_corp='"+strPkCorp+"'";
			CorpVO corpVO=(CorpVO)dao.executeQuery(sql2, new BeanProcessor(CorpVO.class));	
			TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(corpVO.getUnitcode());
			
			
			for(DJZBHeaderVO vo : vos){
				vouchid=vo.getVouchid();
				List<GTVoucher> gTVouchers=new ArrayList();
				sql2="select isnull(zyx3,'') zyx3 from arap_djfb where vouchid='"+vouchid+"' group by zyx3";
				ArrayList<Zyx3> zyx3s =(ArrayList<Zyx3>)dao.executeQuery(sql2, new BeanListProcessor(Zyx3.class));
				//2023-03-01 合并号zyx3
				for(Zyx3 cZyx3:zyx3s) {
					
					GTVoucher gTVoucher=new GTVoucher();
					List<GTVoucherItem> items=new ArrayList();
					
					//gTVoucher.setFPZL(tokenGetVO.getGTFPZL());
					//20230303 发票分类 zyx15
					sql2="select doccode,docname from bd_defdoc where pk_defdoclist in (select pk_defdoclist from bd_defdoclist where doclistcode='FPZL') and docname='"+vo.getZyx15()+"'";
					FPFL fPFL=(FPFL)dao.executeQuery(sql2, new BeanProcessor(FPFL.class));	
					gTVoucher.setFPZL("004");
					if (fPFL!=null) {
						gTVoucher.setFPZL(fPFL.getDoccode());
					}
					
					
					gTVoucher.setKPR(tokenGetVO.getGTKPR()); 
					gTVoucher.setSKR(tokenGetVO.getGTSKR());
					gTVoucher.setFHR(tokenGetVO.getGTFHR());
					gTVoucher.setQYKHYHZH(tokenGetVO.getGTQYKHYHZH()); 
					gTVoucher.setQYDZDH(tokenGetVO.getGTQYDZDH()); 
					
					//20220303
					//客户名称 zyx9
					gTVoucher.setKHMC(vo.getZyx9());
					//客户税号  zyx14
					gTVoucher.setKHSH(vo.getZyx14());
					//客户开票地址电话  zyx10
					gTVoucher.setKHDZ(vo.getZyx10());
					//客户银行账号  zyx13
					gTVoucher.setKHKHYHZH(vo.getZyx13());
					//gTVoucher.setBZ(vob.getZyx2());
					
					/*
					 * 20230325 lijianqiang
					 * 红票申请
					 */
					if (vo.getZyx4().equals("红票")) {
						gTVoucher.setYFPHM(YFPHM);
						gTVoucher.setXXBBH(XXBBH);
					}
					
					sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] " +
							"from arap_djfb " +
							"where vouchid='"+vouchid+"' and isnull(zyx13,'')!='success' and isnull(zyx3,'')='"+cZyx3.getZyx3()+"'";
					ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>)dao.executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class));
					//zyx3 合并号
					
					for(nc.vo.ep.dj.DJZBItemVO vob : vobs){		
						//客户
						String sql3="select custcode,custname,conaddr,phone1,taxpayerid,def1,def2,def3 from bd_cubasdoc where pk_cubasdoc='"+vob.getHbbm()+"'";
						CustVO custVO=(CustVO)dao.executeQuery(sql3, new BeanProcessor(CustVO.class));						
						
						//gTVoucher.setXTLSH(vob.getFb_oid());
						//2023-03-01 合并号zyx3
						gTVoucher.setXTLSH(vouchid+"_"+cZyx3.getZyx3());
						/*
						 * gTVoucher.setKHMC(custVO.getDef3());
						 * gTVoucher.setKHSH(custVO.getTaxpayerid()); //gTVoucher.setXTLSH("gTVoucher");
						 * gTVoucher.setKHDZ(custVO.getDef1()); gTVoucher.setKHKHYHZH(custVO.getDef2());
						 */
						
						
						GTVoucherItem item=new GTVoucherItem();
						
						item.setCPXH("");
						item.setCPDW(vob.getZyx4());//Zyx4 单位
						item.setSL(vob.getSl().toString());
						item.setCPSL(vob.getZyx5());//Zyx4 数量
						item.setBHSJE(vob.getJfybwsje().toString());
						item.setSE(vob.getJfbbsj().toString());
						
						//item.setFLBM(tokenGetVO.getGTFLBM());
						//item.setCPMC(vob.getZyx2());
						
						String zyx2="保险经纪费";
						String flbm="304080299";
						GTFLBM gTFLBM=u8c.server.XmlConfig.getGTFLBM(corpVO.getUnitcode(), vo.getDjlxbm());
						if (gTFLBM!=null) {
							strResult="税收分类:"+gTFLBM.getVal();
							if ((!gTFLBM.getDef().equals(""))&&(gTFLBM.getDef().length()>0)&&(!gTFLBM.getDef().isEmpty())){
								zyx2=gTFLBM.getDef();
							}else {
								zyx2=vob.getZyx2()+gTFLBM.getAdd();
							}
							flbm=gTFLBM.getVal();
						}
						item.setFLBM(flbm);
						item.setCPMC(zyx2);
						
						item.setKCJE("0");
						
						items.add(item);
						gTVoucher.setITEM(items);
						
					}
					
					gTVouchers.add(gTVoucher);
				}
				
				
				strResult=JSON.toJSONString(gTVouchers,new PascalNameFilter());			
				Logger.debug("GTVouchers json:"+strResult);
				
				
				strResult=Base64Converter.encode(strResult);
				//Logger.debug("GTVouchers jsonBase64:"+strResult);
				Map<String, Object> headparams = new HashMap<String, Object>();
				String strToken=getGTToken(tokenGetVO);
				headparams.put("token", strToken);
				//headparams.put("data", "["+strResult+"]");
				headparams.put("spid", tokenGetVO.getGTSPID());
				Logger.debug("GTVouchers map:"+JSON.toJSONString(headparams));
				String data="data="+"["+strResult+"]";
				Logger.debug("GTVouchers data:"+data);
				try {
					strResult=HttpURLConnectionDemo.goldenTax(tokenGetVO.getGTFPKJUrl1(), tokenGetVO.getGTTokeContentType(), headparams, data);
					Logger.debug("GTVouchers result:"+JSON.toJSONString(strResult));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.error("GTVouchers error:"+e.getMessage());
				}
				
				ResultVO result=JSONObject.parseObject(strResult,ResultVO.class);
				strResult=result.getMessage();
				InvoiceUpdateSet invoiceUpdateSet=new InvoiceUpdateSet();
				
				for(SucessListVO sucessList:result.getSucessList()){
					//sql1="update arap_djfb set zyx13='success',zyx14='发票代码:"+sucessList.getFpdm()+"_发票号码:"+sucessList.getFphm()+"',zyx15='"+sucessList.getKprq()+"'"
					sql1="update arap_djfb set zyx13='success',zyx14='"+sucessList.getFpdm()+sucessList.getFphm()+"',zyx15='"+sucessList.getKprq()+"'"
							+" where vouchid+'_'+isnull(zyx3,'')='"+sucessList.getXtlsh()+"' and isnull(zyx13,'')!='success'";
					try {
						dao.executeUpdate(sql1);					
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Logger.error("GTVouchers success:"+e.getMessage());
					}
					invoiceUpdateSet.updateInvoiceData(sucessList.getXtlsh(),
							pk_user,
							sucessList.getFpdm()+sucessList.getFphm(),
							sucessList.getKprq());
				}
				for(ErrListVO errList:result.getErrList()){
					sql1="update arap_djfb set zyx13='err',zyx14='错误信息:"+errList.getErrMsg()+"'"
							+" where vouchid+'_'+isnull(zyx3,'')='"+errList.getXTLSH()+"' and isnull(zyx13,'')!='success'";
					try {
						dao.executeUpdate(sql1);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Logger.error("GTVouchers err:"+e.getMessage());
					}
				}
			
			
			}
		
		}catch(Exception e) {
			Logger.error("uploadGTVoucher err:"+e.getMessage());
			strResult=e.getMessage();
		}
		
		return strResult;
	}
	
	@Override
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
	
	@Override
	public void updateInvoice(String strResult,String vouchid){
		JSONObject parameJson = JSONObject.parseObject(strResult);
		InvoiceResultData arrivalResultData=JSON.toJavaObject(parameJson, InvoiceResultData.class);
		for(InvoiceResult invoiceResult:arrivalResultData.getInvoiceData()){
			//String vouchid=invoiceResult.getInvoiceNo();
			String sql="update arap_djzb set zyx11='"+invoiceResult.getResultCode()+"',zyx12='"+invoiceResult.getResultDesc()+"'"
					+" where vouchid='"+vouchid+"'";
			try {
				dao.executeUpdate(sql);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
