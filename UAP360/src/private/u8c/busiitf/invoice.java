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
		// 第一步：解析数据
		String obj = "";
		String strBody = "";
		String strTemp = "";
		APIMessageVO messageVO = new APIMessageVO();
		try {
			// 初始化参数	
			obj = this.getRequestPostStr(request.getInputStream());
			strTemp+=obj;
			// 第二步：解析数据之后做后续项目上自己的业务处理
			JSONObject parameJson = JSONObject.parseObject(obj);
						
			ApplyInvoiceMessage message= JSON.toJavaObject(parameJson, ApplyInvoiceMessage.class);
			ApplyInvoiceData data=message.getMessage();
			obj=data.getData();
			
			//解密数据体
			EncryptHelper encryptHelper=new EncryptHelper();
			strBody=encryptHelper.decrypt(obj);
			strTemp+=obj+"\n \n "+strBody; 
			// 写入输入中间文件
			writeMiddleFile(APIConst.INDOCPATH + "u8c.busiitf.invoice", strTemp);
			
			List<ApplyInvoiceBody> bodys=JSON.parseArray(strBody,ApplyInvoiceBody.class);
			
			List<PostResult> listPostResult=new ArrayList();//返回结果
			ApplyInvoiceData dataResult=new ApplyInvoiceData();
			
			for(ApplyInvoiceBody body:bodys){
				PostResult postResult=setPostResult(body,body.getBusiType());
				listPostResult.add(postResult);
			}
			// 第三步：返回结果
			obj=JSON.toJSONString(listPostResult);
			strTemp+="\n \n "+obj;
			strBody=encryptHelper.encrypt(obj);
			dataResult.setData(strBody);
			//encryptHelper.decrypt(strBody);
			strBody=JSON.toJSONString(dataResult);
			strTemp+="\n \n "+strBody;
			// 写入输入中间文件
			writeMiddleFile(APIConst.RETURNDATAPATH + "u8c.busiitf.invoice",strTemp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return strBody;
	}
	//处理data


	private PostResult setPostResult(ApplyInvoiceBody body,String strDjlxbm){
		Logger.init("hanglianAPI");
		PostResult postResult=new PostResult();
		postResult.setBillID(body.getAdviceNote());		
		String sql3="";
		String strBody="";
		CostsubjVO costsubjVO;
		try{
			// 第一步：组装数据
			BillRootVO billRootVO=new BillRootVO();			
			List<BillVO> listBillVO=new ArrayList();			
			BillVO billVO=new BillVO();		
			//单据头
			ParentVO parentvo=new ParentVO();			
			parentvo.setDjlxbm(strDjlxbm);
			parentvo.setDjrq(body.getAdviceDate());
			
			parentvo.setDwbm(body.getComCode());			
			parentvo.setLrr("13501036623");
			parentvo.setPrepay(false);
			parentvo.setQcbz(false);
			parentvo.setScomment(body.getZyx1());
			parentvo.setXslxbm("arap");
			parentvo.setZyx1(body.getAdviceNote());//自定义1 发票申请单号
			parentvo.setZyx4("蓝票");//自定义4 操作类型
			parentvo.setZyx5(body.getPmName());//自定义5 项目
			parentvo.setZyx6(body.getSmName());//自定义6 业务员
			//parentvo.setZyx7(String.valueOf(body.getBusiType()));//自定义7 BusiType
			
			//客户
			sql3="select custcode,custname,conaddr,phone1,taxpayerid,def1,def2,def3 from bd_cubasdoc where custcode='"+body.getPayerCode()+"'";
			CustVO custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));		
			if (custVO==null) {
				sql3="select custcode,custname,conaddr,phone1,taxpayerid,def1,def2,def3 from bd_cubasdoc where custname='"+body.getPayerName()+"'";
				custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));	
			}
			if (custVO==null) {
				postResult.setStatus("fail");
				postResult.setU8cCode(body.getPayerCode()+"_"+body.getPayerName()+" 客商档案不存在");
				return postResult;
			}
			
			//客户名称 zyx9
			parentvo.setZyx9(custVO.getDef3());//自定义3 项目
			//客户税号  zyx14
			parentvo.setZyx14(custVO.getTaxpayerid());//客户税号
			//客户开票地址电话  zyx10
			parentvo.setZyx10(custVO.getDef1());//自定义1 项目
			//客户银行账号  zyx13
			parentvo.setZyx13(custVO.getDef2());//自定义2 项目
			//公司
			
			TokenGetVO tokenGetVO=u8c.server.XmlConfig.getTokenGetVO(body.getComCode());
			if (tokenGetVO==null) {
				postResult.setStatus("fail");
				postResult.setU8cCode(body.getComCode()+" 金税默认信息未设置");
				return postResult;
			}
			//发票种类 zyx15
			parentvo.setZyx15("纸质专票");
			if (tokenGetVO!=null) {
				parentvo.setZyx15(tokenGetVO.getGTFPZL());
			}
			
			if (body.getFpflName()!=null) {
				parentvo.setZyx15(body.getFpflName());
			}
			
			billVO.setParentvo(parentvo);
			int iRow=0;
			//单据体
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
			
			// 第二步：提交到API				
			// 服务器访问地址及端口,例如 http://ip:port
			String serviceUrl =u8c.server.XmlConfig.getUrl("u8carapysinsert");
			//"http://127.0.0.1:9099/u8cloud/api/arap/ys/insert";
			// 使用U8cloud系统中设置，具体节点路径为：
			// 应用集成 - 系统集成平台 - 系统信息设置
			// 设置信息中具体属性的对照关系如下：
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trantype", "code"); // 档案翻译方式，枚举值为：编码请录入 code， 名称请录入 name， 主键请录入 pk
			map.put("system", "busiitf"); // 系统编码
			map.put("usercode", "busiuser"); // 用户
			map.put("password", "bbbed85aa52a7dc74fc4b4bca8423394"); // 密码1qazWSX，需要 MD5 加密后录入				
			map.put("uniquekey", body.getAdviceNote());
			strBody=HttpURLConnectionDemo.operator(serviceUrl, map,JSON.toJSONString(billRootVO));
			
			// 第三步：处理结果	
			JSONObject jsonResult =JSON.parseObject(strBody);
			u8c.vo.applyInvoice.DataResponse dataResponse=JSON.toJavaObject(jsonResult, u8c.vo.applyInvoice.DataResponse.class);		
			if (dataResponse.getStatus().equals("success")){// 正常的返回
				postResult.setStatus(dataResponse.getStatus());		
				List<BillVO> billvoResult=JSON.parseArray(dataResponse.getData(),BillVO.class);
				postResult.setU8cCode(billvoResult.get(0).getParentvo().getDjbh());
			}else{// 异常的返回
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
				isr = new InputStreamReader(tInputStream, "UTF-8");// 字符流
				br = new BufferedReader(isr);// 缓冲流
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
					+"金额:"
					+detail.getInclusiveMoney().toString()
					+"汇率:"
					+detail.getCurRate().toString());
		}
		
		//收支项目按监管险种名称匹配
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
		//部门编码按对照xml
		childrenvo.setDeptid(XmlConfig.getDeptXml(body.getComCode(),  body.getDpName()));
		//默认税率6
		Double dSL=6.00;
		if (detail.getTaxRate()!=null) {
			if (detail.getTaxRate()!=0.00) {
				dSL=detail.getTaxRate();
			}
		}
		childrenvo.setSl(dSL);
		childrenvo.setJfbbje(Double.toString(amount));
		childrenvo.setJfybje(Double.toString(amount));
		//childrenvo.setZyx1(detail.getInsurTypeCode());//自定义1 险种编码
		childrenvo.setZyx2(detail.getInsurTypeName());//自定义2 业务险种名称
		if (iRow!=0) {
			childrenvo.setZyx3(Integer.toString(iRow));//自定义3发票合并号
		}
		return childrenvo;
	}
}
