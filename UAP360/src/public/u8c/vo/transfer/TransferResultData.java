package u8c.vo.transfer;

import java.util.List;

public class TransferResultData {
	public int totalNum;
	public List<TransferResult> transferData;
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public List<TransferResult> getTransferData() {
		return transferData;
	}
	public void setTransferData(List<TransferResult> transferData) {
		this.transferData = transferData;
	}
	
}
