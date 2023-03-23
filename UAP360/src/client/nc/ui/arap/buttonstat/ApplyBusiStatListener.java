package nc.ui.arap.buttonstat;
import nc.ui.arap.engine.AbstractRuntime;
import nc.ui.arap.engine.ExtButtonObject;
import nc.ui.arap.engine.IActionRuntime;
import nc.ui.arap.engine.IButtonStatus;
import nc.ui.arap.global.DjTempletHelper;
import nc.ui.ep.dj.ArapBillWorkPageConst;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.ep.dj.DJZBVOConsts;

public class ApplyBusiStatListener implements IButtonStatus{
	public ApplyBusiStatListener() {
		
	}
	public void updateButtonStatus(ExtButtonObject bo, IActionRuntime runtime) {
		 if (runtime instanceof AbstractRuntime) {
	            AbstractRuntime runt = (AbstractRuntime)runtime;
	            bo.setEnabled(true);
	            if (DjTempletHelper.isCardInEdit(runt)) {
	            	bo.setEnabled(false);
	            	//return;
	            }
		 }
	}
}
