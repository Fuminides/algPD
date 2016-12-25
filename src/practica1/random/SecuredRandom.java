package practica1.random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecuredRandom {

	private static SecureRandom cl = null;

	public static int rand() {
		if ( cl == null){
			try {
				init();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return cl.nextInt();
	}
	/**
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	private static void init() throws NoSuchAlgorithmException {
		cl = SecureRandom.getInstanceStrong();
	}

	public SecuredRandom() {
		// TODO Auto-generated constructor stub
	}

}
