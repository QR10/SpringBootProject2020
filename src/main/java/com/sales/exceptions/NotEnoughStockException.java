package com.sales.exceptions;

public class NotEnoughStockException extends Exception {
	private final int pStock;
	private final Long pId;
	private final Long cId;
	private final int oQty;
	
	public NotEnoughStockException(int pStock, Long pID, Long cId,int oQty) {
		this.pStock = pStock;
		this.pId = pID;
		this.cId = cId;
		this.oQty = oQty;
	}

	public int getpStock() {
		return pStock;
	}

	public Long getpId() {
		return pId;
	}

	public Long getcId() {
		return cId;
	}

	public int getoQty() {
		return oQty;
	}
}
