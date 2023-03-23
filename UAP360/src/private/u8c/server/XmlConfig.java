package u8c.server;
import nc.fi.arap.pubutil.RuntimeEnv;
import java.io.File;
import java.math.BigDecimal;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.alibaba.fastjson.JSON;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import u8c.vo.goldentax.GTFLBM;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.xmldata.BusiXml;

public class XmlConfig {
	private static String fileName=RuntimeEnv.getNCHome()
			+ File.separator+"resources"
			+ File.separator+"busiitf"
			+File.separator+"config.xml";
	
	public static String getUrl(String id){
		String strResult="";
		Logger.init("hanglianAPI");
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			//Element rootElement = document.getRootElement();
			Element e=(Element)document.selectSingleNode("//datas/data[@id='"+id+"']");
			if(e!=null){				
					strResult=e.element("url").getText();
				
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(),e);
			Log.getInstance("hanglianAPI").error(e.getMessage(),e);
			e.printStackTrace();
		}
		Logger.debug(strResult);
		Log.getInstance("hanglianAPI").debug(strResult);
		return strResult;
	}
	
	/*
	 * 2023-02-07 lijianqiang
	 * business type xml
	 */
	
	public static BusiXml getBusiXml(String id) {
		BusiXml busiXml=new BusiXml();
		Logger.init("hanglianAPI");
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//datas/data/BusiTypeCode[@id='"+id+"']");
			if(e!=null){				
				busiXml.setBusiTypeCode(id);
				busiXml.setBusiTypeName(e.attributeValue("dsc").toString());
				busiXml.setAppBusiTypeCode(e.element("AppBusiTypeCode").getText());
				busiXml.setAppBusiTypeName(e.element("AppBusiTypeName").getText());
				busiXml.setU8Ctype(e.element("U8Ctype").getText());
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(),e);
			busiXml=null;
			e.printStackTrace();
		}
		Logger.debug(JSON.toJSONString(busiXml));
		return busiXml;
	}
	
	public static String getDeptXml(String corpID,String deptID) {
		String strResult="A";
		Logger.init("hanglianAPI");
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//datas/data/Depts[@corp='"+corpID+"']/Dept[@id='"+deptID+"']");
			if(e!=null){
				strResult=e.element("U8CDept").attributeValue("id").toString();
				if ((strResult==null)||(strResult.equals(""))||(strResult.length()==0)){
					strResult="A";
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			Logger.error("getDeptXml err:"+e.getMessage(),e);			
			e.printStackTrace();
		}
		Logger.debug(JSON.toJSONString("getDeptXml result:"+corpID+"-"+deptID+"-"+strResult));
		return strResult;
	}
	public static Double getInvoiceAmount(String corpID) {
		Double dAmount=0.00;
		Logger.init("hanglianAPI");
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//datas/data/Invoice[@corp='"+corpID+"']");
			if(e!=null){
				BigDecimal bd = new BigDecimal(e.element("Amount").getText());
				dAmount=bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();				
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			Logger.error("getInvoiceAmount err:"+e.getMessage(),e);			
			e.printStackTrace();
		}
		Logger.debug("getInvoiceAmount result:"+Double.toString(dAmount));
		return dAmount;
	}
	public static TokenGetVO getTokenGetVO(String corpID) {
		TokenGetVO tokenGetVO=new TokenGetVO();
		Logger.init("hanglianAPI");
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//datas/data/Invoice[@corp='"+corpID+"']");
			if(e!=null){
				tokenGetVO.setGTTokeUrl(e.element("GTTokeUrl").getText());	
				tokenGetVO.setGTTokeContentType(e.element("GTTokeContentType").getText());	
				tokenGetVO.setGTUserName(e.element("GTUserName").getText());
				tokenGetVO.setGTPassword1(e.element("GTPassword1").getText());
				tokenGetVO.setGTFPZL(e.element("GTFPZL").getText());
				tokenGetVO.setGTQYKHYHZH(e.element("GTQYKHYHZH").getText());
				tokenGetVO.setGTQYDZDH(e.element("GTQYDZDH").getText());
				tokenGetVO.setGTKPR(e.element("GTKPR").getText());
				tokenGetVO.setGTSKR(e.element("GTSKR").getText());
				tokenGetVO.setGTFHR(e.element("GTFHR").getText());
				//tokenGetVO.setGTFLBM(e.element("GTFLBM").getText());
				tokenGetVO.setGTFLBM("");
				tokenGetVO.setGTFPKJUrl1(e.element("GTFPKJUrl1").getText());
				tokenGetVO.setGTSPID(e.element("GTSPID").getText());
				tokenGetVO.setGTFPKJUrl1(e.element("GTFPKJUrl1").getText());
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			Logger.error("getTokenGetVO err:"+e.getMessage(),e);			
			e.printStackTrace();
		}
		Logger.debug("getTokenGetVO result:"+JSON.toJSONString(tokenGetVO));
		
		return tokenGetVO;
	}
	//货物或应税劳务  税收分类
	public static GTFLBM getGTFLBM(String corpID,String billType) {
		GTFLBM gTFLBM=new GTFLBM();
		Logger.init("hanglianAPI");
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//datas/data/Invoice[@corp='"+corpID+"']/GTFLBM/FLBM[@id='"+billType+"']");
			if(e!=null){
				gTFLBM.setAdd(e.element("add").getText());
				gTFLBM.setDef(e.element("def").getText());
				gTFLBM.setVal(e.element("val").getText());
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			Logger.error("getGTFLBM err:"+e.getMessage(),e);			
			e.printStackTrace();
		}
		Logger.debug("getGTFLBM result:"+JSON.toJSONString(gTFLBM));
		return gTFLBM;
	}
}
