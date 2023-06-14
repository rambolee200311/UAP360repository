package u8c.busiitf.task;

import java.util.LinkedHashMap;

import u8c.bs.exception.SecurityException;
import u8c.vo.arrival.EncryptHelper;
import com.alibaba.fastjson.JSON;

import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;

public class DecryptTest implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin {
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		Logger.init("hanglianAPI");
		LinkedHashMap<String, Object> para = param.getKeyMap();
		strResult = (String) para.get("temp");
		//strResult="df9d0d268864fb7597eb00b68cfb9689f8a84b768b6852b08588030c5412ef2ac82be9e257cf61a0b0fcbef59c810d168ca6af6d8c5f99821fb16b962f3ee9c30f14670415d6016fa2218d5b6225d1610e080f7b7ba629cbc04e06b8c0fb335025a8337a0aea85d1267801572e53b52d4d2509cf38adfe2f536dd2e2a7e1267dcd9cc7037d95baccc2fe00be2f75d15bf99fd015036ff656177b5c110ba1c1d1abd36230722bbe12dd077160355ac7b38ae07f55962577866209f55ff0030b61234aa6c4a0d6824378d3b33690e6af60de43e772f0665271d4df4304ac5179cebe7dcbd3527079ae5d090665e2e59702311bd5b25e468e8d0300e5fa685c7fd583116762f0672d27483e0ebcd8132047a1118ae07f918325643c8ddd0fc5eca1c682545d073e3198ecb961c00af225c7";
		strResult="b84a5cebfb37a4ee7475f8d36880f7897753106e4f1887868e059b6988065f722d1f5334b624eac70040910baa4d8f8acf60cc3eb82f1f73518de0bb9b7ebfc33f123aaf5e0541afb54153b55acd284d"; 
				
		Logger.debug(strResult);
		//jie√‹
		EncryptHelper encryptHelper=new EncryptHelper();
		try {
			strResult=encryptHelper.decrypt(strResult);
		} catch (SecurityException e) {
			Logger.error(e.getMessage(),e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.debug(strResult);
		return strResult;
	}
}
