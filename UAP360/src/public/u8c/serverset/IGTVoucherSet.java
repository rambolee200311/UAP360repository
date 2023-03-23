package u8c.serverset;

import u8c.vo.goldentax.TokenGetVO;

public interface IGTVoucherSet {
	public String uploadGTVoucher(String vouchid,String strPkCorp,String pk_user);
	public String getGTToken(TokenGetVO tokenGetVO);
	public void updateInvoice(String strResult,String vouchid);
}
