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
import u8c.server.TransferAPISet;
import u8c.serverset.ITransferAPISet;
/*
 * 20230310
 * 付款单上传业务系统
 */
public class ApplyTransferAct   extends DefaultAction{
	public ApplyTransferAct() {
		
	}
	public void on_applyTransfer() throws BusinessException {
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
			if (!djhead.getDjdl().equals("fk")) {
				bGT=false;
				this.getParent().showErrorMessage("本单据不是付款单");
			}
        	if (bGT) {
        		ITransferAPISet transferAPISet=(ITransferAPISet)NCLocator.getInstance().lookup(ITransferAPISet.class);
        		strResult=transferAPISet.uploadBusiSys(djhead.getVouchid(), corpvo.getPk_corp(), uservo.getPrimaryKey());
        		this.getParent().showErrorMessage(strResult);
        	}
			 
        }
	}
}
