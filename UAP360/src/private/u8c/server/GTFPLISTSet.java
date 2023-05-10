package u8c.server;
/*
 * 2023-04-11
 * lijianqiang
 * 获取金税开票列表
 */
import u8c.vo.goldentax.FPListResult;
import u8c.vo.goldentax.KPJGResult;
import u8c.vo.goldentax.KPJGRow;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.goldentax.TokenResult;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.logging.Logger;
public class GTFPLISTSet {
	String url;
	String contentType;
	//返回发票清单列表
	public List<FPListResult> getFPLISTS(String xtlsh,String spid,String token,String fpzl){
		String strResult="";
		Logger.init("hanglianAPI");	
		List<FPListResult> fplists=new ArrayList();
		try {
			url="http://mycst.cn/NEWKP/KPGL/KPJG";
			Map<String, Object> headparams = new HashMap<String, Object>();		
			headparams.put("xtlsh", xtlsh);
			headparams.put("spid", spid);
			headparams.put("token", token);
			headparams.put("fpzl",fpzl);
			Logger.debug("getFPLISTS headparam:"+JSON.toJSONString(headparams));
			String data="";
			contentType="application/x-www-form-urlencoded;charset=UTF-8";
			try {
				strResult=HttpURLConnectionDemo.goldenTax(url, contentType, headparams, data);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.error("getFPLISTS",e);
			}
			Logger.debug("getFPLISTS result:"+strResult);
			KPJGResult kPJGResult=JSONObject.parseObject(strResult,KPJGResult.class);
			//FPListResult fplist;
			if (kPJGResult.getResult().equals("1")) {//成功
				FPListResult fplist1=getFPLIST(kPJGResult.getXTLSH(),spid,token);
				fplists.add(fplist1);
			}else if (kPJGResult.getResult().equals("0")) {
				if (kPJGResult.getRows().size()>0) {
					for(KPJGRow row:kPJGResult.getRows()) {
						FPListResult fplist2=getFPLIST(row.getXTLSH(),spid,token);
						fplists.add(fplist2);
					}
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			Logger.error("getFPLISTS",e);
		}
		return fplists;
	}
	//返回单个发票清单
	public FPListResult getSingleFPlist(String xtlsh,String spid,String token,String fpzl,String fphm) {
		String strResult="";
		Logger.init("hanglianAPI");	
		FPListResult fplist=new FPListResult();
		try {
			url="http://mycst.cn/NEWKP/KPGL/KPJG";
			Map<String, Object> headparams = new HashMap<String, Object>();		
			headparams.put("xtlsh", xtlsh);
			headparams.put("spid", spid);
			headparams.put("token", token);
			headparams.put("fpzl",fpzl);
			Logger.debug("getFPLISTS headparam:"+JSON.toJSONString(headparams));
			String data="";
			contentType="application/x-www-form-urlencoded;charset=UTF-8";
			try {
				strResult=HttpURLConnectionDemo.goldenTax(url, contentType, headparams, data);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.error("getFPLISTS",e);
			}
			Logger.debug("getFPLISTS result:"+strResult);
			KPJGResult kPJGResult=JSONObject.parseObject(strResult,KPJGResult.class);
			//FPListResult fplist;
			if (kPJGResult.getResult().equals("1")) {//成功
				FPListResult fplist1=getFPLIST(kPJGResult.getXTLSH(),spid,token);
				fplist=fplist1;
			}else if (kPJGResult.getResult().equals("0")) {
				if (kPJGResult.getRows().size()>0) {
					for(KPJGRow row:kPJGResult.getRows()) {
						if (row.getFPHM().equals(fphm)) {
							FPListResult fplist2=getFPLIST(row.getXTLSH(),spid,token);
							fplist=fplist2;
						}
					}
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			Logger.error("getSingleFPlist",e);
		}
		return fplist;
	}
	
	public FPListResult getFPLIST(String xtlsh,String spid,String token) {
		String strResult="";
		Logger.init("hanglianAPI");	
		FPListResult fplist=null;
		try {
			url="http://mycst.cn/NEWKP/KPGL/FPLIST";
			Map<String, Object> headparams = new HashMap<String, Object>();		
			headparams.put("xtlsh", xtlsh);
			headparams.put("spid", spid);
			headparams.put("token", token);
			headparams.put("mxbz","1");
			Logger.debug("getFPLIST headparam:"+JSON.toJSONString(headparams));
			String data="";
			contentType="application/x-www-form-urlencoded;charset=UTF-8";
			try {
				strResult=HttpURLConnectionDemo.goldenTax(url, contentType, headparams, data);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.error("getFPLISTS",e);
			}
			fplist=JSONObject.parseObject(strResult,FPListResult.class);
		}catch(Exception e) {
			e.printStackTrace();
			Logger.error("getFPLISTS",e);
		}
		return fplist;
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
		Logger.debug("FPListResult token :"+strResult);
		//JSONObject parameJson = JSONObject.parseObject(strResult);
		//TokenResult tokenResult=JSON.toJavaObject(parameJson,TokenResult.class);
		TokenResult tokenResult=JSONObject.parseObject(strResult,TokenResult.class);
		strResult=tokenResult.getID();
		//Logger.debug("FPListResult tokenID :"+strResult);
		return strResult;
	}
}
