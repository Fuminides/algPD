package practica2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import practica2.arbol.ArbolSufijo;
import practica2.arbol.Celda;

public class Main{
	
	private static final String COM = "$";

	public static void main (String[] args){
		String palabra = args[0];
		ArbolSufijo arbol = new ArbolSufijo(palabra);
		arbol.naiveBuild();
		arbol.compact();
		
		System.out.println(arbol.toString());
		System.out.println("Repeticion mas larga: " + repeticionLarga(arbol));
	}
	
	public static String repeticionLarga(ArbolSufijo arbol){		
		ArrayList<Celda> camino = arbol.caminoMayorProfundidad();
		String res="";
		for(int i = 1; i < camino.size()-2; i++){
			res+=camino.get(i).elemento;
		}
		return res;
	}
	
	public static ArrayList<String> copia(String word, ArbolSufijo arbol){
		ArrayList<String> resultado = new ArrayList<>()
				, posibilidades = new ArrayList<>();
		String anterior, epilogo;
		
		
		return resultado;
	}
	
}