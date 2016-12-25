package problemasProb;

import java.util.Random;

public class Problema9 {

	private final static double PI = 3.1415926535897932384626433;
	
	public static void main(String[] args){
		double altura = 10.0, radio = altura / 2, exito, piCalculado, error;
		int aciertos;
		Circulo cir = new Circulo(radio);
		Cuadrado cua = new Cuadrado(altura);
		
		System.out.println("Lanz.\tValor\tError\t");
		
		for ( int cantidad = 1; cantidad < 100000000 ; cantidad = cantidad * 10){	
			aciertos =  lanzarDardos(cantidad, cir, cua);
			
			exito = aciertos / (cantidad *1.0);
			piCalculado = (exito * altura*altura / (radio * radio));
			
			error = piCalculado - PI;
			if ( error < 0 ) error = -error;
			error = error / PI;
			
			System.out.printf("%d\t%.4f\t%.2f%%\n", 
					cantidad, piCalculado, error*100);
		}
	}
	
	
	private static int lanzarDardos(int cantidad, Circulo cir, Cuadrado cua) {
		int exitos = 0;
		
		for (int i = 0; i < cantidad; i++){
			if ( lanzarDardoCirculo(cua, cir) ){
				exitos++;
			}
		}
		
		return exitos;
	}


	private static boolean lanzarDardoCirculo(Cuadrado r, Circulo c){
		Random generador = new Random();
		double lado = r.lado, radio = c.radio,
				pX, pY;
		
		//Restamos el centro del cuadrado para crear un vector
		pY = generador.nextDouble() * lado - lado/2;
		pX = generador.nextDouble() * lado - lado/2;
		
		//Comprobamos que el modulo de ese vector no es mayor que el radio
		//de ser así, está dentro del círculo, si no, está fuera.
		return Math.sqrt(pX*pX + pY*pY) - radio <= 0;	
	}
	
	
}