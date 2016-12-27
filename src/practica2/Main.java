package practica2;

import java.util.ArrayList;

import practica2.arbol.ArbolSufijo;
import practica2.arbol.Celda;

public class Main{
	
	public static void main (String[] args){
		String palabra = args[0];
		ArbolSufijo arbol = new ArbolSufijo(palabra);
		arbol.naiveBuild();
		arbol.compact();
		
		System.out.println(arbol.toString());
		System.out.println("L: " + repeticionLarga(arbol));
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