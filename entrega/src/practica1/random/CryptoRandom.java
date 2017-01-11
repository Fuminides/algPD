package practica1.random;

import java.security.SecureRandom;
/**
 * Clase que utiliza un generador de numeros aleatorios mejor que el defecto, pensado para criptografia. 
 */
public class CryptoRandom {
	
	private static SecureRandom cl = null;

	public static int rand() {
		if ( cl == null){
			init();
		}
		return cl.nextInt();
	}
	/**
	 * @return
	 */
	private static void init() {
		cl = new SecureRandom();
	}
	
	private CryptoRandom() {
		// TODO Auto-generated constructor stub
	}

}
