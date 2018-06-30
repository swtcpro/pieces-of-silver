package com.twb.wechat.utils.jingtum;



public class Wallet {
	private String secret = null;
	
	public Wallet() {
	}
	

	
	public static boolean isValidAddress(String address) {
		try {
			Config.getB58IdentiferCodecs().decode(address, B58IdentiferCodecs.VER_ACCOUNT_ID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}