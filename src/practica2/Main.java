package practica2;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import practica2.arbol.ArbolSufijo;
import practica2.arbol.Celda;

/**
 * Clase principal de la ejecucion de la practica 2. Permite
 * buscar copias en documentos fasta o en strings pasados por la entrada
 * estandar.
 * 
 * @author Javier Fumanal Idocin, 684229
 *
 */
public class Main{
	
	static boolean full_mode = false;
	static boolean trivial = false;
	
	public static void main (String[] args){
		long inicio = System.nanoTime(), fin;
		ArrayList<String> genes = null; 
		ArrayList<String> nombres = new ArrayList<>();
		for (String arg:args){
			if (arg.equals("-f")) full_mode = true;
			if (arg.equals("-t")) trivial = true;
		}
		String palabra = args[args.length-1];
		File prueba = new File(palabra);
		
		if (prueba.exists()){
			if (!full_mode) palabra = FastaUtils.leerFasta(prueba);
			else genes = FastaUtils.procesarFastaCompleto(prueba, nombres);
		}
		
		if ( args[0].equals("-r")){
			if ( !full_mode ) {
				if ( !trivial ){
					ArbolSufijo arbol = new ArbolSufijo(palabra);
					arbol.naiveBuild();
					arbol.compact();
					System.out.println("Repeticion mas larga: " + repeticionLarga(arbol));
				}
				else{
					System.out.println("Repeticion mas larga: " + repeticionLargaTrivial(palabra));
				}
			}
			else{
				int i = 0;
				for (String word: genes){
					ArbolSufijo arbol = null;
					if (!trivial){
						arbol = new ArbolSufijo(word);
						arbol.naiveBuild();
						arbol.compact();
					}
					
					System.out.println(nombres.get(i));
					System.out.println(word);
					i++;
					if ( !trivial ) {
						System.out.println("Repeticion mas larga: " + repeticionLarga(arbol));
					}
					else{
						System.out.println("Repeticion mas larga: " + repeticionLargaTrivial(palabra));
					}
				}
			}
		}
		else if (args[0].equals("-c")){
			if ( !full_mode ){
				ArbolSufijo arbol = new ArbolSufijo(palabra);
				arbol.naiveBuild();
				arbol.compact();
				System.out.println("Substrings repetidos:");
				String anterior = "";
				for(String copia : arbol.buscarCopias()){
						if ((copia.length()>1)&&(!copia.equals(anterior))) System.out.println(copia);
						anterior = copia;
				}
			}
			else{
				int i = 0;
				for (String word:genes){
					ArbolSufijo arbol = new ArbolSufijo(word);
					arbol.naiveBuild();
					arbol.compact();
					System.out.println("Substrings repetidos:");
					String anterior = "";
					System.out.println(nombres.get(i));
					System.out.println(word);
					for(String copia : arbol.buscarCopias()){
							i++;
							if ((copia.length()>1)&&(!copia.equals(anterior))) System.out.println(copia);
							anterior = copia;
					}
				}
			}
		}
		fin = System.nanoTime();
		System.out.println("Ejecutado en " + TimeUnit.NANOSECONDS.toMillis(fin-inicio) + " milisegundos");
	}
	
	private static String repeticionLargaTrivial(String palabra) {
		String resultado = "";
		for ( int longitud = 1; longitud <= palabra.length()/2; longitud++){
			for(int z = 0; z+longitud <= palabra.length(); z++){
				boolean rep = false;
				String candidato = palabra.substring(z, z+longitud);
				for(int x = z+longitud; x+longitud <= palabra.length();x++){
					if ( candidato.equals(palabra.substring(x,x+longitud))) rep = true;
				}
				if (rep && (resultado.length()< candidato.length())) resultado = candidato;
			}
		}
		return resultado;
	}

	/**
	 * Devuelve el substring repetido mas largo en una cadena.
	 * 
	 * @param arbol Arbol de sufijos.
	 * @return el substring mas largo repetido.
	 */
	public static String repeticionLarga(ArbolSufijo arbol){		
		ArrayList<Celda> camino = arbol.caminoMayorProfundidad();
		String res="";
		for(int i = 1; i < camino.size()-2; i++){
			res+=camino.get(i).elemento;
		}
		return res;
	}
	
}