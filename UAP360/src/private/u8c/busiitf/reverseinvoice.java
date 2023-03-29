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
		// ��һ������������
		String obj = "";
		String strBody = "";
		APIMessageVO messageVO = new APIMessageVO();
		try {
			// ��ʼ������
			obj = this.getRequestPostStr(request.getInputStream());
			strResult = obj;
			// �ڶ�������������֮����������Ŀ���Լ���ҵ����
			JSONObject parameJson = JSONObject.parseObject(obj);
			ReverseInvoiceMessage message = JSON.toJavaObject(parameJson, ReverseInvoiceMessage.class);
			ReverseInvoiceData data = message.getMessage();
			obj = data.getData();

			// ����������
			EncryptHelper encryptHelper = new EncryptHelper();
			strBody = encryptHelper.decrypt(obj);
			strTemp += obj + "\n \n " + strBody;
			// д�������м��ļ�
			writeMiddleFile(APIConst.INDOCPATH + "u8c.busiitf.reverseinvoice", strTemp);
			List<ReverseInvoiceBody> bodys = JSON.parseArray(strBody, ReverseInvoiceBody.class);
			List<PostResult> listPostResult = new ArrayList();// ���ؽ��
			ApplyInvoiceData dataResult = new ApplyInvoiceData();

			for (ReverseInvoiceBody body : bodys) {
				PostResult postResult = setPostResult(body);
				listPostResult.add(postResult);
			}
			// �����������ؽ��
			obj = JSON.toJSONString(listPostResult);
			strTemp += "\n \n " + obj;
			strBody = encryptHelper.encrypt(obj);
			dataResult.setData(strBody);
			// encryptHelper.decrypt(strBody);
			strBody = JSON.toJSONString(dataResult);
			strTemp += "\n \n " + strBody;
			// д�������м��ļ�
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
		// �ӱ� vob
		ArrayList<DJZBItemVO> vobs = (ArrayList<DJZBItemVO>) getDao().executeQuery(sql2,new BeanListProcessor(DJZBItemVO.class));
		if (vobs.size() <= 0) {
			postResult.setStatus("fail");
			postResult.setU8cCode("δ�ҵ���Ʊ��" + body.getReverseInvoiceNo());
			return postResult;
		}

		sql1 = "select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] "
				+ " from arap_djzb " +
				// " where djdl='ys' and djlxbm='F0-01' and dr=0 and
				// isnull(zyx2,'')='"+body.getReverseInvoiceNo()+"'";
				"where vouchid='" + vobs.get(0).getVouchid() + "';";
		// ����vo
		DJZBHeaderVO vos = (DJZBHeaderVO) getDao().executeQuery(sql1, new BeanProcessor(DJZBHeaderVO.class));
		String strBody = "";
		try {
			// ��һ������װ����
			BillRootVO billRootVO = new BillRootVO();
			List<BillVO> listBillVO = new ArrayList();
			BillVO billVO = new BillVO();
			// ����ͷ
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
			parentvo.setZyx1(body.getAdviceNote());// �Զ���1 ��Ʊ���뵥��
			//parentvo.setZyx2(body.getReverseInvoiceNo());//�Զ���8 ����ԭ��Ʊ��
			parentvo.setZyx4("��Ʊ");// �Զ���3 ��������
			/*
			 * parentvo.setZyx5(body.getPmName());//�Զ���5 ��Ŀ
			 * parentvo.setZyx6(body.getSmName());//�Զ���6 ҵ��Ա
			 */
			//parentvo.setZyx7(body.getBusiType());// �Զ���7 ҵ��Ա
			parentvo.setZyx8(body.getReverseInvoiceNo());// �Զ���8 ����ԭ��Ʊ��
			//�ͻ����� zyx9
			parentvo.setZyx9(vos.getZyx9());//�Զ���3 ��Ŀ
			//�ͻ�˰��  zyx14
			parentvo.setZyx14(vos.getZyx14());//�ͻ�˰��
			//�ͻ���Ʊ��ַ�绰  zyx10
			parentvo.setZyx10(vos.getZyx10());//�Զ���1 ��Ŀ
			//�ͻ������˺�  zyx13
			parentvo.setZyx13(vos.getZyx13());//�Զ���2 ��Ŀ			
			//��Ʊ���� zyx15
			parentvo.setZyx15(vos.getZyx15());

			
			billVO.setParentvo(parentvo);
			// ������
			List<ChildrenVO> children = new ArrayList();
			for (DJZBItemVO vob : vobs) {
				ChildrenVO childrenvo = new ChildrenVO();
				/*
				 * �ͻ�����
				 */
				sql1 = "select pk_corp,pk_cubasdoc,custcode,custname" + " from bd_cubasdoc" + " where pk_cubasdoc='"
						+ vob.getHbbm() + "';";
				CustBasVO custBasVO = (CustBasVO) getDao().executeQuery(sql1, new BeanProcessor(CustBasVO.class));
				childrenvo.setHbbm(custBasVO.getCustcode());
				/*
				 * if(!body.getCurrency().equals("CNY")){
				 * childrenvo.setBbhl(vobs.get(0).getBbhl().doubleValue());
				 * childrenvo.setBzbm(vobs.get(0).getBzbm());
				 * childrenvo.setZyx1(body.getCurrency().toString() +"ԭ���:"
				 * +body.getInclusiveRMB() +"����:" +body.getCurRate() ); }
				 * //childrenvo.setSl(body.getTaxRate());
				 * childrenvo.setJfbbje(Double.toString(body.getReverseInclusiveRMB()));
				 * childrenvo.setJfybje(Double.toString(body.getReverseInclusiveRMB()));
				 */
				 childrenvo.setBbhl(vob.getBbhl().doubleValue());
				 //childrenvo.setBzbm(vob.getBzbm());				 
				 childrenvo.setSl(vob.getSl().toDouble());
				 childrenvo.setJfbbje(Double.toString(vob.getJfbbje().toDouble()*-1));
				 childrenvo.setJfybje(Double.toString(vob.getJfybje().toDouble()*-1));
				 childrenvo.setZyx1(vob.getZyx1());// �Զ���1 ���ֱ���
				 childrenvo.setZyx2(vob.getZyx2());// �Զ���2 ��������
				 childrenvo.setZyx3(vob.getZyx3());// �Զ���2 ��������
				 //String zyx3=""+vob.getZyx3();
				 //if( (vob.getZyx3()!=null)||(!vob.getZyx3().isEmpty())||(!vob.getZyx3().equals(""))||(vob.getZyx3().trim().length()!=0)) {
				//	 zyx3=vob.getZyx3();
				 //}
				 if (vob.getZyx3()==null)				 {
					 parentvo.setZyx16(vob.getFb_oid()+"_");//ԭ��Ʊϵͳ��ˮ��
				 }else {
					 parentvo.setZyx16(vob.getFb_oid()+"_"+vob.getZyx3());//ԭ��Ʊϵͳ��ˮ��
				 }
				 
				// childrenvo.setJfybje(detail.getInclusiveMoney());
				/*
				 * ��֧��Ŀ
				 */
					
					  CostsubjVO costsubjVO = (CostsubjVO) dmo.queryByPrimaryKey(CostsubjVO.class, vobs.get(0).getSzxmid()); 
					  childrenvo.setSzxmid(costsubjVO.getCostcode());
					 
					  DeptdocVO deptdocVO=(DeptdocVO) dmo.queryByPrimaryKey(DeptdocVO.class, vobs.get(0).getDeptid()); 
					  childrenvo.setDeptid(deptdocVO.getDeptcode());
				 //childrenvo.setSzxmid(vob.getSzxmid());//��֧��Ŀ
				 
				children.add(childrenvo);
			}
			billVO.setChildren(children);

			listBillVO.add(billVO);
			billRootVO.setBillvo(listBillVO);

			// �ڶ������ύ��API
			// ���������ʵ�ַ���˿�,���� http://ip:port
			String serviceUrl = u8c.server.XmlConfig.getUrl("u8carapysinsert");
			// "http://127.0.0.1:9099/u8cloud/api/arap/ys/insert";
			// ʹ��U8cloudϵͳ�����ã�����ڵ�·��Ϊ��
			// Ӧ�ü��� - ϵͳ����ƽ̨ - ϵͳ��Ϣ����
			// ������Ϣ�о������ԵĶ��չ�ϵ���£�
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trantype", "code"); // �������뷽ʽ��ö��ֵΪ��������¼�� code�� ������¼�� name�� ������¼�� pk
			map.put("system", "busiitf"); // ϵͳ����
			map.put("usercode", "busiuser"); // �û�
			map.put("password", "bbbed85aa52a7dc74fc4b4bca8423394"); // ����1qazWSX����Ҫ MD5 ���ܺ�¼��
			map.put("uniquekey", body.getAdviceNote() + body.getZyx1());
			strBody = HttpURLConnectionDemo.operator(serviceUrl, map, JSON.toJSONString(billRootVO));

			// ��������������
			JSONObject jsonResult = JSON.parseObject(strBody);
			u8c.vo.applyInvoice.DataResponse dataResponse = JSON.toJavaObject(jsonResult,
					u8c.vo.applyInvoice.DataResponse.class);
			if (dataResponse.getStatus().equals("success")) {// �����ķ���
				postResult.setStatus(dataResponse.getStatus());
				List<BillVO> billvoResult = JSON.parseArray(dataResponse.getData(), BillVO.class);
				postResult.setU8cCode(billvoResult.get(0).getParentvo().getDjbh());
			} else {// �쳣�ķ���
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
