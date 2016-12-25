package practica1.random;

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
