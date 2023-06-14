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
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDateTime;
import u8c.bs.APIConst;
import u8c.bs.exception.ConfigException;
import u8c.bs.exception.SecurityException;
import u8c.bs.utils.FileUtils;
import u8c.pubitf.action.IAPICustmerDevelop;
import u8c.server.XmlConfig;
import u8c.vo.pub.APIMessageVO;
import u8c.vo.arrival.EncryptHelper;
import u8c.vo.busiqry.ArrivalQryRep;
import u8c.vo.busiqry.ArrivalQryRepDetail;
import u8c.vo.busiqry.QueryReq;
import u8c.vo.entity.CorpVO;
import u8c.vo.entity.CustVO;
import u8c.vo.busiqry.QueryMessage;
import u8c.vo.busiqry.QueryParam;
import u8c.vo.busiqry.QueryRep;
/*
 * 20230613
 * ���˶��˽ӿ�
 */
public class arrivalqry  implements IAPICustmerDevelop{
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	@Override
	public String doAction(HttpServletRequest request)  throws BusinessException, ConfigException{
		String obj = "";
		String strBody = "";
		String strTemp = "";
		String sDate="";
		String eDate="";
		QueryRep rep=new QueryRep();
		ArrivalQryRep report=new ArrivalQryRep();
		EncryptHelper encryptHelper=new EncryptHelper();
		Logger.init("hanglianAPI");
		try {
			// ��ʼ������	
			
			obj = this.getRequestPostStr(request.getInputStream());			
			JSONObject parameJson = JSONObject.parseObject(obj);
			QueryReq queryReq=JSON.toJavaObject(parameJson,QueryReq.class);
			obj=queryReq.getMessage().getData();
			//����������			
			strBody=encryptHelper.decrypt(obj);
			strTemp+=obj+"\n \n "+strBody; 
			// д�������м��ļ�
			writeMiddleFile(APIConst.INDOCPATH + "u8c.busiitf.arrivalqry", strTemp);
			//�õ���ѯ����
			JSONObject queryJson = JSONObject.parseObject(strBody);
			QueryParam queryParam=JSON.toJavaObject(queryJson,QueryParam.class);
			
			//��֯����
			String sql1="select [bbje], [budgetcheck], [ddhbbm], [deinvdate], [djbh], [djdl], [djkjnd], [djkjqj], [djlxbm], [djrq], [djzt], [dr], [dwbm], [dyvouchid], [dzrq], [effectdate], [enduser], [fbje], [fcounteractflag], [feinvstatus], [finvoicetype], [fj], [fktjbm], [hzbz], [inner_effect_date], [isjszxzf], [isnetready], [isonlinepay], [ispaid], [isreded], [isselectedpay], [jszxzf], [kmbm], [kskhyh], [lastshr], [lasttzr], [lrr], [lybz], [officialprintdate], [officialprintuser], [outbusitype], [paydate], [payman], [pj_jsfs], [pj_num], [pj_oid], [prepay], [pzglh], [qcbz], [qrr], [scomment], [settlenum], [sfkr], [shkjnd], [shkjqj], [shr], [shrq], [shzd], [specflag], [spzt], [ssbh], [sscause], [sxbz], [sxkjnd], [sxkjqj], [sxr], [sxrq], [ts], [veinvcode], [veinvfailnote], [veinvnumber], [vouchid], [vsplitrecord], [vsrceinvcode], [vsrceinvnumber], [xslxbm], [ybje], [yhqrkjnd], [yhqrkjqj], [yhqrr], [yhqrrq], [ywbm], [zdr], [zdrq], [zgyf], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9], [zzzt], [inccontype] " +
					" from arap_djzb where djdl='sk' and dr=0 and";
			//��������
			StringBuilder where = new StringBuilder(sql1);
			if (queryParam.getsAccountCheckDate()!= null && queryParam.getsAccountCheckDate().trim().length() > 0) {
				where.append(" djrq>='"+queryParam.getsAccountCheckDate()+"' and");
				sDate=queryParam.getsAccountCheckDate();
			}
			if (queryParam.geteAccountCheckDate()!= null && queryParam.geteAccountCheckDate().trim().length() > 0) {
				where.append(" djrq<='"+queryParam.geteAccountCheckDate()+"' and");
				eDate=queryParam.geteAccountCheckDate();
			}
			//���ݽ�������
			strTemp="";
			strTemp=XmlConfig.getQryParam(queryParam.getType());
			if (strTemp!= null && strTemp.trim().length() > 0) {
				where.append(" djlxbm in ("+strTemp+") and");
			}
			int index = where.lastIndexOf("and");
	        if (index > 0) {
	            where = where.delete(index, index + 3);
	        }
	        //��װheader	        
	        report.sAccountCheckDate=queryParam.getsAccountCheckDate();
	        report.eAccountCheckDate=queryParam.geteAccountCheckDate();
	        report.status="success";
	        report.u8cCode="";
	        List<ArrivalQryRepDetail> reportdetail=new ArrayList();
	        
			//����vo
			ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>) getDao().executeQuery(where.toString(), new BeanListProcessor(DJZBHeaderVO.class));	
			int iRow=1;
			for(DJZBHeaderVO vo : vos){
				CustVO custVO=null;
				CorpVO corpVO=null;
				ArrivalQryRepDetail detail=new ArrivalQryRepDetail();
				String vouchid=vo.getVouchid();
				String sql2="select [accountid], [assetpactno], [bankrollprojet], [bbhl], [bbpjlx], [bbtxfy], [bbye], [bfyhzh], [billdate], [bjdwhsdj], [bjdwsl], [bjdwwsdj], [bjjldw], [blargessflag], [bz_date], [bz_kjnd], [bz_kjqj], [bzbm], [cashitem], [chbm_cl], [checkflag], [chmc], [cinventoryid], [ckbm], [ckdh], [ckdid], [cksqsh], [clbh], [commonflag], [contractno], [ctzhtlx_pk], [ddh], [ddhh], [ddhid], [ddlx], [deptid], [dfbbje], [dfbbsj], [dfbbwsje], [dffbje], [dffbsj], [dfjs], [dfshl], [dfybje], [dfybsj], [dfybwsje], [dfyhzh], [discountmny], [dj], [djbh], [djdl], [djlxbm], [djxtflag], [dr], [dstlsubcs], [dwbm], [encode], [equipmentcode], [facardbh], [fb_oid], [fbhl], [fbpjlx], [fbtxfy], [fbye], [fjldw], [fkyhdz], [fkyhmc], [flbh], [fph], [fphid], [freeitemid], [fx], [ggxh], [groupnum], [hbbm], [hsdj], [hsl], [htbh], [htmc], [innerorderno], [issfkxychanged], [isverifyfinished], [item_bill_pk], [itemstyle], [jfbbje], [jfbbsj], [jffbje], [jffbsj], [jfjs], [jfshl], [jfybje], [jfybsj], [jfybwsje], [jfzkfbje], [jfzkybje], [jobid], [jobphaseid], [jsfsbm], [jshj], [kmbm], [kprq], [ksbm_cl], [kslb], [kxyt], [notetype], [occupationmny], [old_flag], [old_sys_flag], [ordercusmandoc], [othersysflag], [pausetransact], [paydate], [payflag], [payman], [pch], [ph], [pj_jsfs], [pjdirection], [pjh], [pk_jobobjpha], [pk_taxclass], [produceorder], [productline], [pzflh], [qxrq], [sanhu], [seqnum], [sfbz], [sfkxyh], [shlye], [skyhdz], [skyhmc], [sl], [spzt], [srbz], [szxmid], [task], [tax_num], [tbbh], [ts], [txlx_bbje], [txlx_fbje], [txlx_ybje], [usedept], [verifyfinisheddate], [vouchid], [wbfbbje], [wbffbje], [wbfybje], [wldx], [xbbm3], [xgbh], [xm], [xmbm2], [xmbm4], [xmys], [xyzh], [ybpjlx], [ybtxfy], [ybye], [ycskrq], [ysbbye], [ysfbye], [ysybye], [ywbm], [ywxz], [ywybm], [zjldw], [zkl], [zrdeptid], [zy], [zyx1], [zyx10], [zyx11], [zyx12], [zyx13], [zyx14], [zyx15], [zyx16], [zyx17], [zyx18], [zyx19], [zyx2], [zyx20], [zyx21], [zyx22], [zyx23], [zyx24], [zyx25], [zyx26], [zyx27], [zyx28], [zyx29], [zyx3], [zyx30], [zyx4], [zyx5], [zyx6], [zyx7], [zyx8], [zyx9] " +
						"from arap_djfb " +
						"where vouchid='"+vouchid+"';";
				//�ӱ� vob
				ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>) getDao().executeQuery(sql2, new BeanListProcessor(DJZBItemVO.class));
				
				//��˾
				String sql3="select unitcode,unitname from bd_corp where pk_corp='"+vo.getDwbm()+"'";
				corpVO=(CorpVO)getDao().executeQuery(sql3, new BeanProcessor(CorpVO.class));
				//����Ա
				sql3="select user_name from sm_user where cuserid='"+vo.getLrr()+"'";
				//sql3="select user_name from sm_user where cuserid='"+pk_user+"'";
				String userCode=(String)dao.executeQuery(sql3, new ColumnProcessor());
				
				for(nc.vo.ep.dj.DJZBItemVO vob : vobs){
					//�ͻ�
					sql3="select custcode,custname,conaddr,phone1,taxpayerid from bd_cubasdoc where pk_cubasdoc='"+vob.getHbbm()+"'";
					custVO=(CustVO)getDao().executeQuery(sql3, new BeanProcessor(CustVO.class));	
					//����
					sql3="select currtypecode from bd_currtype where pk_currtype='"+vob.getBzbm()+"'";
					String currency=(String)dao.executeQuery(sql3, new ColumnProcessor());
					
					detail.setRowNo(iRow);//�кţ�1��ʼ����
					detail.setArrivalBusinType(vo.getDjlxbm());//����ҵ������ �� //F2-01-���ͷ�  //F2-02-���� //F2-08 ת������ F2-09 �ڳ�����
					detail.setArrivalRegiCode(vouchid);//���˵ǼǺ�
					if (custVO!=null) {
						detail.setPayerCode(custVO.getCustcode());//֧��������
						detail.setPayerName(custVO.getCustname());//֧��������
					}
					switch(vo.getDjlxbm().toString()) {							
						case "F2-01": 
							//��������
							sql3="select rq from arap_dztz where pk_dztz='"+vob.getDdhh()+"'";
							break;
						case "F2-02":
							//��������
							sql3="select rq from arap_dztz where pk_dztz='"+vob.getDdhh()+"'";
							break;
						case "F2-08":
							//��������
							sql3="select paydate from arap_djzb where zyx1='"+vo.getZyx1()+"' and zyx2='"+vo.getZyx2()+"' and djdl='fk' and dr=0";
							break;
						case "F2-09":
							//��������
							sql3="select djrq from arap_djzb where vouchid='"+vouchid+"'";
							break;
						default:
							//��������
							sql3="select djrq from arap_djzb where vouchid='"+vouchid+"'";
							break;
					}
					String rq=(String)dao.executeQuery(sql3, new ColumnProcessor());
					if (rq!=null&&rq.trim().length()>0) {
						detail.setArrivalDate(rq);//��������
					}
					detail.setArrivalAmount(vob.getDfybje().doubleValue());//���˽��
					detail.setCurrency(currency);//���˱���
					detail.setOperatorCode(userCode);//������
					detail.setArrivalPurpose(vob.getZy());//������;
					detail.setIsPush("0");//�Ƿ�����ҵ��ϵͳ��0-δ���ͣ�1-�����ͣ�
					if (vo.getZyx11()!=null&&vo.getZyx11().trim().equals("0")) {
						detail.setIsPush("1");
					}
					detail.setZyx1("");
					detail.setZyx2("");
					detail.setZyx3("");
					detail.setComCode(corpVO.getUnitcode());//�տ��������
					detail.setComName(corpVO.getUnitname());//�տ��������
				}
				reportdetail.add(detail);
				iRow++;
			}
			report.setDetail(reportdetail);
		} catch (IOException e) {			 
			 report.sAccountCheckDate=sDate;
			 report.eAccountCheckDate=eDate;
			 report.status="fail";
			 report.u8cCode="IOException:"+e.getMessage();
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block			
			 report.sAccountCheckDate=sDate;
			 report.eAccountCheckDate=eDate;
			 report.status="fail";
			 report.u8cCode="SecurityException:"+e.getMessage();
			e.printStackTrace();
		} catch(Exception e) {			
			 report.sAccountCheckDate=sDate;
			 report.eAccountCheckDate=eDate;
			 report.status="fail";
			 report.u8cCode="Exception:"+e.getMessage();
			e.printStackTrace();
		}
		
		// �����������ؽ��
		obj=JSON.toJSONString(report);
		strTemp+="\n \n "+obj;
		try {
			strBody=encryptHelper.encrypt(obj);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rep.setData(strBody);
		strBody=JSON.toJSONString(rep);
		
		// д�������м��ļ�
		try {
			writeMiddleFile(APIConst.RETURNDATAPATH + "u8c.busiitf.arrivalqry",obj);
			strTemp+="\n \n "+strBody;
			writeMiddleFile(APIConst.RETURNDATAPATH + "u8c.busiitf.arrivalqry",strTemp);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strBody;
	}
	
	//����data
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
		//д�м��ļ�
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
}
