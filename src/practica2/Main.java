package practica2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import practica2.arbol.ArbolSufijo;
import practica2.arbol.Celda;

public class Main{
	
	private static final String COM = "$";

	public static void main (String[] args){
		String palabra = args[1];
		if ( args[0].equals("-r")){
			ArbolSufijo arbol = new ArbolSufijo(palabra);
			arbol.naiveBuild();
			arbol.compact();
			
			System.out.println("Repeticion mas larga: " + repeticionLarga(arbol));
		}
		else if (args[0].equals("-c")){
			palabra = COM + palabra + COM;
			ArbolSufijo arbol = new ArbolSufijo(palabra);
			arbol.naiveBuild();
			arbol.compact();
			System.out.println("Substrings repetidos:");
			for(String copia : arbol.buscarCopias()){
				if (copia.length()>1){
					System.out.println(copia);
				}
			}
		}
		
		
	}
	
	public static String repeticionLarga(ArbolSufijo arbol){		
		ArrayList<Celda> camino = arbol.caminoMayorProfundidad();
		String res="";
		for(int i = 1; i < camino.size()-2; i++){
			res+=camino.get(i).elemento;
		}
		return res;
	}
	
}