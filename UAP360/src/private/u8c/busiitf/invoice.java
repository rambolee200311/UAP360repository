package u8c.busiitf;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDateTime;
import u8c.bs.APIConst;
import u8c.bs.exception.ConfigException;
import u8c.bs.exception.SecurityException;
import u8c.bs.utils.FileUtils;
import u8c.pubitf.action.IAPICustmerDevelop;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.pub.APIMessageVO;
import u8c.server.HttpURLConnectionDemo;
import u8c.server.XmlConfig;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CostsubjVO;
import u8c.vo.entity.CustVO;
import u8c.vo.goldentax.TokenGetVO;
import u8c.vo.applyInvoice.*;

import u8c.bs.convert.JSONConvertor;

public class invoice  implements IAPICustmerDevelop{
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	@Override
	public String doAction(HttpServletRequest request)  throws BusinessException, ConfigException{
		// ��һ������������
		String obj = "";
		String strBody = "";
		String strTemp = "";
		APIMessageVO messageVO = new APIMessageVO();
		try {
			// ��ʼ������	
			obj = this.getRequestPostStr(request.getInputStream());
			strTemp+=obj;
			// �ڶ�������������֮����������Ŀ���Լ���ҵ����
			JSONObject parameJson = JSONObject.parseObject(obj);
						
			ApplyInvoiceMessage message= JSON.toJavaObject(parameJson, ApplyInvoiceMessage.class);
			ApplyInvoiceData data=message.getMessage();
			obj=data.getData();
			
			//����������
			EncryptHelper encryptHelper=new EncryptHelper();
			strBody=encryptHelper.decrypt(obj);
			strTemp+=obj+"\n \n "+strBody; 
			// д�������м��ļ�
			writeMiddleFile(APIConst.INDOCPATH + "u8c.busiitf.invoice", strTemp);
			
			List<ApplyInvoiceBody> bodys=JSON.parseArray(strBody,ApplyInvoiceBody.class);
			
			List<PostResult> listPostResult=new ArrayList();//���ؽ��
			ApplyInvoiceData dataResult=new ApplyInvoiceData();
			
			for(ApplyInvoiceBody body:bodys){
				PostResult postResult=setPostResult(body,body.getBusiType());
				listPostResult.add(postResult);
			}
			// �����������ؽ��
			obj=JSON.toJSONString(listPostResult);
			strTemp+="\n \n "+obj;
			strBody=encryptHelper.encrypt(obj);
			dataResult.setData(strBody);
			//encryptHelper.decrypt(strBody);
			strBody=JSON.toJSONString(dataResult);
			strTemp+="\n \n "+strBody;
			// д�������м��ļ�
			writeMiddleFile(APIConst.RETURNDATAPATH + "u8c.busiitf.invoice",strTemp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return strBody;
	}
	//����data


	private PostResult setPostResult(ApplyInvoiceBody body,String strDjlxbm){
		Logger.init("hanglianAPI");
		PostResult postResult=new PostResult();
		postResult.setBillID(body.getAdviceNote());		
		String sql3="";
		String strBody="";
		CostsubjVO costsubjVO;
		try{
			// ��һ������װ����
			BillRootVO billRootVO=new BillRootVO();			
			List<BillVO> listBillVO=new ArrayList();			
			BillVO billVO=new BillVO();		
			//����ͷ
			ParentVO parentvo=new ParentVO();			
			parentvo.setDjlxbm(strDjlxbm);
			parentvo.setDjrq(body.getAdviceDate());
			
			parentvo.setDwbm(body.getComCode());			
			parentvo.setLrr("13501036623");
			parentvo.setPrepay(false);
			parentvo.setQcbz(false);
			parentvo.setScomment(body.getZyx1());
			parentvo.setXslxbm("arap");
			parentvo.setZyx1(body.getAdviceNote());//�Զ���1 ��Ʊ���뵥��
			parentvo.setZyx4("��Ʊ");//�Զ���4 ��������
			parentvo.setZyx5(body.getPmName());//�Զ���5 ��Ŀ
			parentvo.setZyx6(body.getSmName());//�Զ���6 ҵ��Ա
			//parentvo.setZyx7(String.valueOf(body.getBusiType()));//�Զ���7 BusiType
			
			//�ͻ�
			sql3="select custcode,custname,conaddr,phone1,taxpayerid,def1,def2,def3 from bd_cubasdoc where custcode='"+body.getPayerCode()+"'";
			CustVO custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));		
			if (custVO==null) {
				sql3="select custcode,custname,conaddr,phone1,taxpayerid,def1,def2,def3 from bd_cubasdoc where custname='"+body.getPayerName()+"'";
				custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));	
			}
			if (custVO==null) {
				postResult.setStatus("fail");
				postResult.setU8cCode(body.getPayerCode()+"_"+body.getPayerName()+" ���̵���������");
				return postResult;
			}
			
			//�ͻ����� zyx9
			parentvo.setZyx9(custVO.getDef3());//�Զ���3 ��Ŀ
			//�ͻ�˰��  zyx14
			parentvo.setZyx14(custVO.getTaxpayerid());//�ͻ�˰��
			//�ͻ���Ʊ��ַ�绰  zyx10
			parentvo.setZyx10(custVO.getDef1());//�Զ���1 ��Ŀ
			//�ͻ������˺�  zyx13
			parentvo.setZyx13(custVO.getDef2());//�Զ���2 ��Ŀ
			//��˾
			
			TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(body.getComCode());
			if (tokenGetVO==null) {
				postResult.setStatus("fail");
				postResult.setU8cCode(body.getComCode()+" ��˰Ĭ����Ϣδ����");
				return postResult;
			}
			//��Ʊ���� zyx15
			parentvo.setZyx15("ֽ��רƱ");
			if (tokenGetVO!=null) {
				parentvo.setZyx15(tokenGetVO.getGTFPZL());
			}
			
			if (body.getFpflName()!=null) {
				parentvo.setZyx15(body.getFpflName());
			}
			
			billVO.setParentvo(parentvo);
			int iRow=0;
			//������
			List<ChildrenVO> children=new ArrayList();
			for(ApplyInvoiceDetail detail:body.getDetail()){
				Double dAmount=u8c.server.XmlConfig.getInvoiceAmount(body.getComCode().toString());
				Double inclusiveAmount=detail.getInclusiveRMB();
				BigDecimal inclusiveAmountD1=new BigDecimal(inclusiveAmount);
				inclusiveAmount=inclusiveAmountD1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				
				
				ChildrenVO childrenvo=getChildrenVO(inclusiveAmount,body,detail,iRow);
				children.add(childrenvo);
				//iRow++;
				
				
				
			}
			billVO.setChildren(children);
			
			listBillVO.add(billVO);
			billRootVO.setBillvo(listBillVO);
			
			// �ڶ������ύ��API				
			// ���������ʵ�ַ���˿�,���� http://ip:port
			String serviceUrl =u8c.server.XmlConfig.getUrl("u8carapysinsert");
			//"http://127.0.0.1:9099/u8cloud/api/arap/ys/insert";
			// ʹ��U8cloudϵͳ�����ã�����ڵ�·��Ϊ��
			// Ӧ�ü��� - ϵͳ����ƽ̨ - ϵͳ��Ϣ����
			// ������Ϣ�о������ԵĶ��չ�ϵ���£�
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trantype", "code"); // �������뷽ʽ��ö��ֵΪ��������¼�� code�� ������¼�� name�� ������¼�� pk
			map.put("system", "busiitf"); // ϵͳ����
			map.put("usercode", "busiuser"); // �û�
			map.put("password", "bbbed85aa52a7dc74fc4b4bca8423394"); // ����1qazWSX����Ҫ MD5 ���ܺ�¼��				
			map.put("uniquekey", body.getAdviceNote());
			strBody=HttpURLConnectionDemo.operator(serviceUrl, map,JSON.toJSONString(billRootVO));
			
			// ��������������	
			JSONObject jsonResult =JSON.parseObject(strBody);
			u8c.vo.applyInvoice.DataResponse dataResponse=JSON.toJavaObject(jsonResult, u8c.vo.applyInvoice.DataResponse.class);		
			if (dataResponse.getStatus().equals("success")){// �����ķ���
				postResult.setStatus(dataResponse.getStatus());		
				List<BillVO> billvoResult=JSON.parseArray(dataResponse.getData(),BillVO.class);
				postResult.setU8cCode(billvoResult.get(0).getParentvo().getDjbh());
			}else{// �쳣�ķ���
				//postResult.setStatus(dataResponse.getStatus());	
				postResult.setStatus("fail");
				postResult.setU8cCode(dataResponse.getErrorcode()+"-"+dataResponse.getErrormsg());
			}
		}catch(Exception e){
			postResult.setStatus("fail");			
			postResult.setU8cCode(e.getMessage());
			e.printStackTrace();
		}
		return postResult;
	}
	protected void writeMiddleFile(String path, String info) throws IOException,
    UnsupportedEncodingException {
	  String[] date =
	      new UFDateTime(System.currentTimeMillis()).toString().split(" ");
	  String fileName =
	      path + "-" + date[0] + "-" + date[1].replaceAll(":", "-") + ".txt";
	  FileUtils util = new FileUtils();
	  info=date[0]+" " +date[1]+"\n \n "+info;
	  if (info != null) {
	    util.writeBytesToFile(info.getBytes("UTF-8"), fileName);
	  }
}

	private String getRequestPostStr(InputStream tInputStream) throws IOException {
		String retStr = null;
		if (tInputStream != null) {
			BufferedReader br = null;
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(tInputStream, "UTF-8");// �ַ���
				br = new BufferedReader(isr);// ������
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = br.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}
				retStr = tStringBuffer.toString();
			} finally {
				if (null != br) {
					br.close();
				}
				if (null != isr) {
					isr.close();
				}
			}
		}
		return retStr;
	}
	
	private ChildrenVO getChildrenVO(Double amount,ApplyInvoiceBody body,ApplyInvoiceDetail detail,int iRow) throws DAOException {
		ChildrenVO childrenvo=new ChildrenVO();
		childrenvo.setHbbm(body.getPayerCode());
		if(!detail.getCurrency().equals("CNY")){
			//childrenvo.setBbhl(detail.getCurRate());
			//childrenvo.setBzbm(detail.getCurrency());
			childrenvo.setZyx1(detail.getCurrency().toString()
					+"���:"
					+detail.getInclusiveMoney().toString()
					+"����:"
					+detail.getCurRate().toString());
		}
		
		//��֧��Ŀ�������������ƥ��
		String sql3="select pk_costsubj,costcode,costname from bd_costsubj where pk_corp=(select pk_corp from bd_corp where unitcode='"+body.getComCode().toString()+"') and costname='"
		//+detail.getInsurTypeName().toString()+"'";
		+detail.getSupInsurTypeName().toString()+"'";		
		CostsubjVO costsubjVO=(CostsubjVO)getDao().executeQuery(sql3, new BeanProcessor(CostsubjVO.class));
		if (costsubjVO!=null){
			//childrenvo.setSzxmid(costsubjVO.getPk_costsubj());
			childrenvo.setSzxmid(costsubjVO.getCostcode());
			childrenvo.setSzxmid_code(costsubjVO.getCostcode());
			childrenvo.setSzxmid_name(costsubjVO.getCostname());
		}else {
			childrenvo.setSzxmid("A01001001");
		}
		//���ű��밴����xml
		childrenvo.setDeptid(XmlConfig.getDeptXml(body.getComCode(),  body.getDpName()));
		//Ĭ��˰��6
		Double dSL=6.00;
		if (detail.getTaxRate()!=null) {
			if (detail.getTaxRate()!=0.00) {
				dSL=detail.getTaxRate();
			}
		}
		childrenvo.setSl(dSL);
		childrenvo.setJfbbje(Double.toString(amount));
		childrenvo.setJfybje(Double.toString(amount));
		//childrenvo.setZyx1(detail.getInsurTypeCode());//�Զ���1 ���ֱ���
		childrenvo.setZyx2(detail.getInsurTypeName());//�Զ���2 ҵ����������
		if (iRow!=0) {
			childrenvo.setZyx3(Integer.toString(iRow));//�Զ���3��Ʊ�ϲ���
		}
		return childrenvo;
	}
}
