package u8c.busiitf.task;

import java.util.LinkedHashMap;

import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import u8c.vo.goldentax.GTFLBM;

public class FPFLBMtask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String corpID="";
		String billType="";
		String zyx2="";
		String strResult="";
		LinkedHashMap<String, Object> para = param.getKeyMap();
		corpID = (String) para.get("corp");
		billType= (String) para.get("billtype");
		zyx2= (String) para.get("zyx2");
		GTFLBM gTFLBM=u8c.server.XmlConfig.getGTFLBM(corpID, billType);
		if (gTFLBM!=null) {
			strResult="税收分类:"+gTFLBM.getVal();
			if ((!gTFLBM.getDef().equals(""))&&(gTFLBM.getDef().length()>0)&&(!gTFLBM.getDef().isEmpty())){
				strResult+="货物或应税劳务:"+gTFLBM.getDef();
			}else {
				strResult+="货物或应税劳务:"+zyx2+gTFLBM.getAdd();
			}
		}
		return strResult;
	}

}
