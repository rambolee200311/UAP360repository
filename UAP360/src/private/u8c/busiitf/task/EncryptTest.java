package u8c.busiitf.task;
import java.util.LinkedHashMap;

import u8c.bs.exception.SecurityException;
import u8c.vo.arrival.EncryptHelper;
import com.alibaba.fastjson.JSON;

import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;

public class EncryptTest implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		Logger.init("hanglianAPI");
		LinkedHashMap<String, Object> para = param.getKeyMap();
		//strResult = (String) para.get("temp");
		
		//strResult="[{'bank':[{'bkCode':'104874100018','bkName':'中国银行股份有限公司固原支行','acCode':'123456789012345'}],'comCode':'A0101','comName':'航联保险经纪有限公司','conAddr':'北京市朝阳区航安路23号','custCode':'DWKHWU8D20221021100534','custName':'北京首都机场物业管理有限公司','phone1':'4455677','phone2':'334244242','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''}]";
		//strResult="[{'apCode':'05000093','apName':'中华联合财产保险股份有限公司北京分公司','arrivalAmount':5979.19,'arrivalRMB':40843.25,'arrivalRegiCode':'1001A31000000000447D','billDate':'2023-02-09','billID':'ZFRLWG4F20230113110037P20230113110155','busiType':'F1-02','comCode':'A01','comName':'航联保险经纪有限公司','curRate':6.8309,'currency':'USD','dpName':'','operatorCode':'B000269','payerCode':'DWKHWKJK20230110090110','payerName':'110-1','pmName':'','smName':'','zyx1':'','zyx10':'','zyx2':'','zyx3':'','zyx4':'','zyx5':'','zyx6':'','zyx7':'','zyx8':'','zyx9':''}]";
		//strResult="[{'bank': [{'bkCode': '103437605216','bkName': '中国农业银行乐安县支行','acCode': '123456789012'}],'comCode': 'A01','comName': '航联保险经纪有限公司','conAddr': '北京','custCode': 'DWKHODTE20221111092612','custName': '测试客户111101','phone1': '13501023321','phone2': '13601089967','zyx1': '','zyx2': '','zyx3': '','zyx4': '','zyx5': ''}]";
		//strResult=" [{'bank':[{'bkCode': '103437605216','bkName': '中国农业银行乐安县支行','acCode': '123456789014'},{'bkCode': '103437605216','bkName': '中国农业银行乐安县支行','acCode': '123456789013'}],'comCode':'A0101','comName':'航联保险经纪有限公司','conAddr':'北京市朝阳区航安路23号','custCode':'DWKHWU8D20221021100534','custName':'北京首都机场物业管理有限公司','phone1':'4455677','phone2':'334244242','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''}]";
		//strResult=" [{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A01','comName':'航联保险经纪有限公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''},{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A0101','comName':'航联保险经纪有限公司上海分公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''},{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A0102','comName':'航联保险经纪有限公司广州分公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''},{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A0103','comName':'航联保险经纪有限公司成都分公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''},{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A0106','comName':'航联保险经纪有限公司深圳分公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''},{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A0105','comName':'航联保险经纪有限公司陕西分公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''},{'bank':[{'acCode':'2222222222222','bkCode':'308651020040','bkName':'招商银行成都分行高新支行'}],'comCode':'A0104','comName':'航联保险经纪有限公司云南分公司','conAddr':'','custCode':'DWKH8XHX20221021100534','custName':'深圳富翔航空俱乐部有限公司','phone1':'','phone2':'','zyx1':'','zyx2':'','zyx3':'','zyx4':'','zyx5':''}]";
		//strResult=" [{'adviceDate':'2023-03-17','adviceNote':'TZS1678168174273','comCode':'A01','comName':'航联保险经纪有限公司','currency':'CNY','inclusiveMoney':10831.84,'inclusiveRMB':124912.80,'operatorCode':'B000398','reverseInclusiveMoney':-124912.80,'reverseInclusiveRMB':-124912.80,'reverseInvoiceNo':'310022213055399658','zyx1':'ZF1679039221394','zyx2':'','zyx3':'','zyx4':'','zyx5':''}]";
		strResult="{'sAccountCheckDate':'2023-02-01','eAccountCheckDate':'2023-12-31','type': '3'}";
		Logger.debug("EncryptTest:"+strResult);
		//jia密
		EncryptHelper encryptHelper=new EncryptHelper();
		try {
			strResult=encryptHelper.encrypt(strResult);
		} catch (SecurityException e) {
			Logger.error(e.getMessage(),e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.debug("EncryptTest:"+strResult);
		return strResult;
	}
}