package practica1.random;

/**
 * Clase para devolver valores aleatorios utilizando el Random por defecto de Java.
 */
import java.util.Random;

public class VanillaRandom {
	private static Random srandom;
	
	public static int rand(){
		if ( srandom == null){
			init();
		}
		return srandom.nextInt();
	}
	
	private static int init(){
		srandom = new Random();
		return srandom.nextInt();
	}

	private VanillaRandom() {
		// TODO Auto-generated constructor stub
	}

}
