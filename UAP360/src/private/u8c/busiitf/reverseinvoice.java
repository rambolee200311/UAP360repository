package u8c.busiitf;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.bd.b04.DeptdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.b28.CostsubjVO;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDateTime;
import u8c.bs.APIConst;
import u8c.bs.exception.ConfigException;
import u8c.bs.exception.SecurityException;
import u8c.bs.utils.FileUtils;
import u8c.pubitf.action.IAPICustmerDevelop;

import u8c.vo.applyInvoice.ApplyInvoiceData;
import u8c.vo.applyInvoice.BillRootVO;
import u8c.vo.applyInvoice.BillVO;
import u8c.vo.applyInvoice.ChildrenVO;
import u8c.vo.applyInvoice.ParentVO;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.pub.APIMessageVO;
import u8c.server.HttpURLConnectionDemo;
import u8c.vo.reverseInvoice.PostResult;
import u8c.vo.reverseInvoice.ReverseInvoiceMessage;
import u8c.vo.reverseInvoice.ReverseInvoiceData;
import u8c.vo.reverseInvoice.ReverseInvoiceBody;

public class reverseinvoice implements IAPICustmerDevelop {
	private BaseDAO dao;

	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	@Override
	public String doAction(HttpServletRequest request) throws BusinessException, ConfigException {
		String strResult = "";
		String strTemp = "";
		// 第一步：解析数据
		String obj = "";
		String strBody = "";
		APIMessageVO messageVO = new APIMessageVO();
		try {
			// 初始化参数
			obj = this.getRequestPostStr(request.getInputStream());
			strResult = obj;
			// 第二步：解析数据之后做后续项目上自己的业务处理
			JSONObject parameJson = JSONObject.parseObject(obj);
			ReverseInvoiceMessage message = JSON.toJavaObject(parameJson, ReverseInvoiceMessage.class);
			ReverseInvoiceData data = message.getMessage();
			obj = data.getData();

			// 解密数据体
			EncryptHelper encryptHelper = new EncryptHelper();
			strBody = encryptHelper.decrypt(obj);
			strTemp += obj + "\n \n " + strBody;
			// 写入输入中间文件
			writeMiddleFile(APIConst.INDOCPATH + "u8c.busiitf.reverseinvoice", strTemp);
			List<ReverseInvoiceBody> bodys = JSON.parseArray(strBody, ReverseInvoiceBody.class);
			List<PostResult> listPostResult = new ArrayList();// 返回结果
			ApplyInvoiceData dataResult = new ApplyInvoiceData();

			for (ReverseInvoiceBody body : bodys) {
				PostResult postResult = setPostResult(body);
				listPostResult.add(postResult);
			}
			// 第三步：返回结果
			obj = JSON.toJSONString(listPostResult);
			strTemp += "\n \n " + obj;
			strBody = encryptHelper.encrypt(obj);
			dataResult.setData(strBody);
			// encryptHelper.decrypt(strBody);
			strBody = JSON.toJSONString(dataResult);
			strTemp += "\n \n " + strBody;
			// 写入输入中间文件
			writeMiddleFile(APIConst.RETURNDATAPATH + "u8c.busiitf.reverseInvoice", strTemp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strBody;
	}

	private PostResult setPostResult(ReverseInvoiceBody body) throws BusinessException {
		PostResult postResult = new PostResult();
		postResult.setAdviceNote(body.getAdviceNote());
		HYPubBO dmo = new HYPubBO();
		String sql1 = "";
		String sql2 = "";

		sql2 = "select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] "
				+ "from arap_djfb " +
				// "where vouchid='"+vos.get(0).getVouchid()+"';";
				"where zyx14='" + body.getReverseInvoiceNo() + "';";
		// 子表 vob
		ArrayList<DJZBItemVO> vobs = (ArrayList<DJZBItemVO>) getDao().executeQuery(sql2,new BeanListProcessor(DJZBItemVO.class));
		if (vobs.size() <= 0) {
			postResult.setStatus("fail");
			postResult.setU8cCode("未找到发票号" + body.getReverseInvoiceNo());
			return postResult;
		}

		sql1 = "select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] "
				+ " from arap_djzb " +
				// " where djdl='ys' and djlxbm='F0-01' and dr=0 and
				// isnull(zyx2,'')='"+body.getReverseInvoiceNo()+"'";
				"where vouchid='" + vobs.get(0).getVouchid() + "';";
		// 主表vo
		DJZBHeaderVO vos = (DJZBHeaderVO) getDao().executeQuery(sql1, new BeanProcessor(DJZBHeaderVO.class));
		String strBody = "";
		try {
			// 第一步：组装数据
			BillRootVO billRootVO = new BillRootVO();
			List<BillVO> listBillVO = new ArrayList();
			BillVO billVO = new BillVO();
			// 单据头
			ParentVO parentvo = new ParentVO();
			//parentvo.setDjlxbm(body.getBusiType());
			parentvo.setDjlxbm(vos.getDjlxbm());
			parentvo.setDjrq(body.getAdviceDate());
			parentvo.setDwbm(body.getComCode());
			parentvo.setLrr("13501036623");
			parentvo.setPrepay(false);
			parentvo.setQcbz(false);
			parentvo.setScomment(body.getZyx1());
			parentvo.setXslxbm("arap");
			parentvo.setZyx1(body.getAdviceNote());// 自定义1 发票申请单号
			//parentvo.setZyx2(body.getReverseInvoiceNo());//自定义8 冲销原发票号
			parentvo.setZyx4("红票");// 自定义3 操作类型
			/*
			 * parentvo.setZyx5(body.getPmName());//自定义5 项目
			 * parentvo.setZyx6(body.getSmName());//自定义6 业务员
			 */
			//parentvo.setZyx7(body.getBusiType());// 自定义7 业务员
			parentvo.setZyx8(body.getReverseInvoiceNo());// 自定义8 冲销原发票号
			//客户名称 zyx9
			parentvo.setZyx9(vos.getZyx9());//自定义3 项目
			//客户税号  zyx14
			parentvo.setZyx14(vos.getZyx14());//客户税号
			//客户开票地址电话  zyx10
			parentvo.setZyx10(vos.getZyx10());//自定义1 项目
			//客户银行账号  zyx13
			parentvo.setZyx13(vos.getZyx13());//自定义2 项目			
			//发票种类 zyx15
			parentvo.setZyx15(vos.getZyx15());

			
			billVO.setParentvo(parentvo);
			// 单据体
			List<ChildrenVO> children = new ArrayList();
			for (DJZBItemVO vob : vobs) {
				ChildrenVO childrenvo = new ChildrenVO();
				/*
				 * 客户档案
				 */
				sql1 = "select pk_corp,pk_cubasdoc,custcode,custname" + " from bd_cubasdoc" + " where pk_cubasdoc='"
						+ vob.getHbbm() + "';";
				CustBasVO custBasVO = (CustBasVO) getDao().executeQuery(sql1, new BeanProcessor(CustBasVO.class));
				childrenvo.setHbbm(custBasVO.getCustcode());
				/*
				 * if(!body.getCurrency().equals("CNY")){
				 * childrenvo.setBbhl(vobs.get(0).getBbhl().doubleValue());
				 * childrenvo.setBzbm(vobs.get(0).getBzbm());
				 * childrenvo.setZyx1(body.getCurrency().toString() +"原金额:"
				 * +body.getInclusiveRMB() +"汇率:" +body.getCurRate() ); }
				 * //childrenvo.setSl(body.getTaxRate());
				 * childrenvo.setJfbbje(Double.toString(body.getReverseInclusiveRMB()));
				 * childrenvo.setJfybje(Double.toString(body.getReverseInclusiveRMB()));
				 */
				 childrenvo.setBbhl(vob.getBbhl().doubleValue());
				 //childrenvo.setBzbm(vob.getBzbm());				 
				 childrenvo.setSl(vob.getSl().toDouble());
				 childrenvo.setJfbbje(Double.toString(vob.getJfbbje().toDouble()*-1));
				 childrenvo.setJfybje(Double.toString(vob.getJfybje().toDouble()*-1));
				 childrenvo.setZyx1(vob.getZyx1());// 自定义1 险种编码
				 childrenvo.setZyx2(vob.getZyx2());// 自定义2 险种名称
				 childrenvo.setZyx3(vob.getZyx3());// 自定义2 险种名称
				 //String zyx3=""+vob.getZyx3();
				 //if( (vob.getZyx3()!=null)||(!vob.getZyx3().isEmpty())||(!vob.getZyx3().equals(""))||(vob.getZyx3().trim().length()!=0)) {
				//	 zyx3=vob.getZyx3();
				 //}
				 if (vob.getZyx3()==null)				 {
					 parentvo.setZyx16(vob.getFb_oid()+"_");//原发票系统流水号
				 }else {
					 parentvo.setZyx16(vob.getFb_oid()+"_"+vob.getZyx3());//原发票系统流水号
				 }
				 
				// childrenvo.setJfybje(detail.getInclusiveMoney());
				/*
				 * 收支项目
				 */
					
					  CostsubjVO costsubjVO = (CostsubjVO) dmo.queryByPrimaryKey(CostsubjVO.class, vobs.get(0).getSzxmid()); 
					  childrenvo.setSzxmid(costsubjVO.getCostcode());
					 
					  DeptdocVO deptdocVO=(DeptdocVO) dmo.queryByPrimaryKey(DeptdocVO.class, vobs.get(0).getDeptid()); 
					  childrenvo.setDeptid(deptdocVO.getDeptcode());
				 //childrenvo.setSzxmid(vob.getSzxmid());//收支项目
				 
				children.add(childrenvo);
			}
			billVO.setChildren(children);

			listBillVO.add(billVO);
			billRootVO.setBillvo(listBillVO);

			// 第二步：提交到API
			// 服务器访问地址及端口,例如 http://ip:port
			String serviceUrl = u8c.server.XmlConfig.getUrl("u8carapysinsert");
			// "http://127.0.0.1:9099/u8cloud/api/arap/ys/insert";
			// 使用U8cloud系统中设置，具体节点路径为：
			// 应用集成 - 系统集成平台 - 系统信息设置
			// 设置信息中具体属性的对照关系如下：
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trantype", "code"); // 档案翻译方式，枚举值为：编码请录入 code， 名称请录入 name， 主键请录入 pk
			map.put("system", "busiitf"); // 系统编码
			map.put("usercode", "busiuser"); // 用户
			map.put("password", "bbbed85aa52a7dc74fc4b4bca8423394"); // 密码1qazWSX，需要 MD5 加密后录入
			map.put("uniquekey", body.getAdviceNote() + body.getZyx1());
			strBody = HttpURLConnectionDemo.operator(serviceUrl, map, JSON.toJSONString(billRootVO));

			// 第三步：处理结果
			JSONObject jsonResult = JSON.parseObject(strBody);
			u8c.vo.applyInvoice.DataResponse dataResponse = JSON.toJavaObject(jsonResult,
					u8c.vo.applyInvoice.DataResponse.class);
			if (dataResponse.getStatus().equals("success")) {// 正常的返回
				postResult.setStatus(dataResponse.getStatus());
				List<BillVO> billvoResult = JSON.parseArray(dataResponse.getData(), BillVO.class);
				postResult.setU8cCode(billvoResult.get(0).getParentvo().getDjbh());
			} else {// 异常的返回
					// postResult.setStatus(dataResponse.getStatus());
				postResult.setStatus("fail");
				postResult.setU8cCode(dataResponse.getErrorcode() + "-" + dataResponse.getErrormsg());
			}
		} catch (Exception e) {
			postResult.setStatus("fail");
			postResult.setU8cCode(e.getMessage());
			e.printStackTrace();
		}
		return postResult;
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

	protected void writeMiddleFile(String path, String info) throws IOException, UnsupportedEncodingException {
		String[] date = new UFDateTime(System.currentTimeMillis()).toString().split(" ");
		String fileName = path + "-" + date[0] + "-" + date[1].replaceAll(":", "-") + ".txt";
		FileUtils util = new FileUtils();
		info = date[0] + " " + date[1] + "\n \n " + info;
		if (info != null) {
			util.writeBytesToFile(info.getBytes("UTF-8"), fileName);
		}
	}
}
