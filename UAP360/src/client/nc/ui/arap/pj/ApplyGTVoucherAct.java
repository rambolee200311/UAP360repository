package nc.ui.arap.pj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.arap.service.IEinvService4Arap;
import nc.ui.arap.actions.DefaultAction;
import nc.ui.pub.ClientEnvironment;
import nc.vo.bd.CorpVO;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.sm.UserVO;
import u8c.server.GTVoucherSet;
import u8c.serverset.IGTVoucherSet;
/*
 * 20230310
 * 应收单上传金税，并回写业务系统
 */
public class ApplyGTVoucherAct extends DefaultAction{
	public ApplyGTVoucherAct(){
		
	}
	public void on_applyGTVoucher() throws BusinessException {
        this.onToEinv();
    }
	private void onToEinv() throws BusinessException {
        CorpVO corpvo = ClientEnvironment.getInstance().getCorporation();
        UserVO uservo=ClientEnvironment.getInstance().getUser();
        DJZBVO[] selectedVOs = this.getSelectedvos();
        DJZBVO[] var2=selectedVOs;
        int var3=selectedVOs.length;
        String strResult="";
        boolean bGT=true;
        for(int var4=0;var4<var3;var4++) {
        	bGT=true;
        	DJZBVO djzbvo=var2[var4];
        	DJZBHeaderVO djhead=djzbvo.getHeadVO();
        	//this.getParent().showHintMessage(corpvo.getPk_corp()+"_"+uservo.getPrimaryKey()+"_"+djhead.getDjbh()+"_"+djhead.getVouchid());
			if (!djhead.getDjdl().equals("ys")) {
				bGT=false;
				this.getParent().showErrorMessage("本单据不是应收单");
			}else if ((djhead.getZyx1()==null)||(djhead.getZyx1().length()==0)||(djhead.getZyx1().isEmpty())||(djhead.getZyx1().equals(""))) {
				bGT=false;
				this.getParent().showErrorMessage("本应收单不是业务系统同步的");
			}
        	if (bGT) {
				/*
				 * GTVoucherSet gtvoucherSet=new GTVoucherSet(); strResult
				 * =gtvoucherSet.uploadGTVoucher(djhead.getVouchid(), corpvo.getPk_corp(),
				 * uservo.getPrimaryKey());
				 */
        	IGTVoucherSet gtvoucherSet=(IGTVoucherSet)NCLocator.getInstance().lookup(IGTVoucherSet.class);
        	strResult=gtvoucherSet.uploadGTVoucher(djhead.getVouchid(), corpvo.getPk_corp(),uservo.getPrimaryKey(),"","");
        	this.getParent().showErrorMessage(strResult);
        	}
			 
        }
	}
}
