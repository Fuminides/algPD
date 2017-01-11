package practica1.random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
/**
 * Clase que utliza una versión un poco mejor de los numeros aleatorios de la biblioteca de criptografia
 * de Java.
 */
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
		cl = new SecureRandom();
		//cl = SecureRandom.getInstanceStrong(); //SOLO JAVA 1.8!!
	}

	public SecuredRandom() {
		// TODO Auto-generated constructor stub
	}

}
