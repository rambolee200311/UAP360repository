package nc.ui.arap.pj;

import com.alibaba.fastjson.JSON;

import nc.bs.logging.Logger;
import nc.ui.arap.actions.DefaultAction;
import nc.ui.ep.dj.ArapBillWorkPageConst;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;

public class TestboTest extends DefaultAction{
	public TestboTest() {
		
	}
	public void on_boTest()  throws BusinessException{
		Logger.init("hanglianAPI");
		DJZBVO vo = this.getDataBuffer().getCurrentDJZBVO();
		//Logger.debug("djzbvo:"+JSON.toJSONString(vo));
		DJZBHeaderVO head = (DJZBHeaderVO)((DJZBHeaderVO)vo.getParentVO());
		
		head.setZyx1("ABCTZS1676447982442");
		this.getBillCardPanel().setHeadItem("zyx1", "ABCTZS1676447982442");
		vo.setParentVO(head);
		this.getArapDjPanel().setM_DjState(ArapBillWorkPageConst.WORKSTAT_EDIT);
        this.getActionRunntimeV0().setCurrentPageStatus(ArapBillWorkPageConst.WORKSTAT_EDIT);
        vo.setm_OldVO((DJZBVO)vo.clone());
        this.getParent().showHintMessage(NCLangRes4VoTransl.getNCLangRes().getStrByID("2006030102", "UPP2006030102-000309"));
       
        
		//this.getParent().showHintMessage(NCLangRes4VoTransl.getNCLangRes().getStrByID("2006030102", "2006030102_uc_000003")+" 应收单据");
	}
}
