package com.rs.utils;

import com.motiservice.Motivote;
import com.rs.game.player.Player;

public class VoteManager {
	
	public static final Motivote MOTIVOTE = new Motivote("ZariaRsps", "9ac5c0082a0c94ef4ef1d8aa85f71910");
	
	public static void main(Player player) {
	try {
		//String command = args[0];

		//String auth = args[1];
		/*Result r = MOTIVOTE.redeem(SearchField.AUTH_CODE, auth);
		if (r.success()) {
			player.sm("Vote succes");
			System.out.println("Successful redemption!");
		} else {
			player.sm("No vote was found.");
		}*/
		/*switch (command) {
			case "redeemauth":
				String auth = args[1];
				Result r1 = MOTIVOTE.redeem(SearchField.AUTH_CODE, auth);
				
				if (r1.success()) {
					// we only expect 1 result, so skip checking the size of the results
					System.out.println("Successful redemption!");
				}
				break;
			case "redeemuser":
				String username = args[1];
				Result r2 = MOTIVOTE.redeem(SearchField.USER_NAME, username);
				
				if (r2.success()) {
					// since this type of redemption can yield multiple votes being redeemed at once
					// check how many were redeemed.
					int total = r2.votes().size();
					System.out.println("Successful redemption! x" + total);
				}
				break;
			case "redeemip":
				String ip = args[1];
				Result r3 = MOTIVOTE.redeem(SearchField.IP_ADDRESS, ip);
				
				if (r3.success()) {
					// since this type of redemption can yield multiple votes being redeemed at once
					// check how many were redeemed.
					int total = r3.votes().size();
					System.out.println("Successful redemption! x" + total);
				}
				break;
			default:
				System.err.println("Unknown command: " + command);
				break;
		}*/
	}
	catch (Exception ex) {
		ex.printStackTrace();
	}
}
	
}