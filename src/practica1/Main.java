package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


import practica1.grafo.Grafo;
import practica1.grafo.Identificable;
import practica1.grafo.Nodo;
import practica1.random.CryptoRandom;
import practica1.random.SecuredRandom;
import practica1.random.VanillaRandom;

public class Main {
	

	private static int metodo_aleatorio = 0;
	private static int conjuntos_finales = 2;
	private static boolean stein = true;
	private static final int CASO_BASE = 3;
	
	public static void main(String[] args) throws Exception {
		Grafo grafo = new Grafo();
		ArrayList<Producto> productos = null;
		String prod = "Productos.txt";
		
		for(int i = 0; i < args.length; i++){
			String arg = args[i];
			
			if (args[i].equals("-p")){
				prod = args[i+1];
				productos = leerFicheroProd(args[i+1]);
			}
			else if (arg.equalsIgnoreCase("-a")){
				metodo_aleatorio = Integer.parseInt(args [i+1]);
			}
			else if (arg.equalsIgnoreCase("-s")){
				stein = true;
			}
		}
		if (productos == null) leerFicheroProd(prod);
		
		for ( Producto producto : productos){
			grafo.add(producto);
		}
		
		leerCompras(prod, grafo);
		
		if ( stein ) {
			conjuntos_finales = (int) (grafo.size()*1.0 / Math.sqrt(2));
		}
		
		grafo = kargerStein(grafo, conjuntos_finales);
		
		for (int i : grafo.grafo.keySet()){
			Nodo<Identificable> aux = grafo.get(i);
			System.out.println("Conjunto " + i +":\n\n" + aux.toString() + "\n");
		}
	}
	
	private static void leerCompras(String string, Grafo grafo) throws FileNotFoundException {
		Scanner fichero = new Scanner(new File(string));
		
		while ( fichero.hasNextLine() ){
			int id = fichero.nextInt();
			fichero.next();
			fichero.nextInt();
			fichero.nextDouble();

			while (fichero.hasNextInt() ){
				int conect = fichero.nextInt();
				if (grafo.addConection(id, conect)) System.out.println("Conectamos " + id + " y " + conect);
			}
			
			fichero.nextLine();
		}
		
		fichero.close();
	}

	private static ArrayList<Producto> leerFicheroProd(String ruta) throws FileNotFoundException{
		Scanner fichero = new Scanner(new File(ruta));
		ArrayList<Producto> productos = new ArrayList<>();
		
		while ( fichero.hasNextLine() ){
			int id = fichero.nextInt();
			String nombre = fichero.next();
			int unidades = fichero.nextInt();
			double precio = fichero.nextDouble();

			Producto producto = new Producto(id, unidades, precio, nombre);
			productos.add(producto);
			
			fichero.nextLine();
		}
		
		fichero.close();
		
		return productos;
	}
	
	@SuppressWarnings("unused")
	private static void generarComprasRandom(Grafo grafo){
		for ( int i = 0; i < grafo.size(); i++){
			for ( int z =1 + i; z < grafo.size(); z++){
				int relacionados = rand() % 2;
				if ( relacionados > 0){
					System.out.println("Conectamos " + i + " y " + z);
					grafo.addConection(i, z, relacionados);
				}
			}
		}
		
		
	}
	
	public static int rand(){
			switch(metodo_aleatorio){
			case 1:
				 return CryptoRandom.rand(); 
			case 2:
				return SecuredRandom.rand();
			default:
				return VanillaRandom.rand();
			}
	}

	/**
	 * @param grafo
	 */
	private static Grafo kargerStein(Grafo grafo, int conjuntos_end) {
		System.out.println("Tamaño actual: " + grafo.size());
		if (grafo.size() == 2){
			System.out.println("Terminado satisfactoriamente");
			return grafo;
		}
		else if (grafo.size() == CASO_BASE){
			return minCutBruteForce(grafo);
		}
		else {
			int max = 400;
			while ( (grafo.grafo.size() > conjuntos_end) && max > 0){
				int nodo1 = grafo.get();
				if ( grafo.get(nodo1).getConections().length > 0){
					int nodo2 = grafo.get(nodo1).getConections()[Math.abs(rand()) %  grafo.get(nodo1).getConections().length];
					grafo.merge(nodo1, nodo2);
					System.out.println("Mergeamos " + nodo1 + " y " + nodo2);
				}
				else{
					max--;
					if ( max == 0){
						System.out.println("Lo sentimos, no hay suficientes conexiones para hacer el corte.");
					}
				}
			}
			System.out.println("Vamos a: " + (int) (grafo.size()*1.0/Math.sqrt(2)));
			Grafo aux1 = new Grafo(), aux2 = new Grafo();
			aux1.copy(grafo);
			aux2.copy(grafo);
			
			aux1 = kargerStein(aux1, (int) (aux1.size()*1.0/Math.sqrt(2)));
			aux2 = kargerStein(aux2, (int) (aux2.size()*1.0/Math.sqrt(2)));
			
			if ( evaluate(aux1) <= evaluate(aux2) ){
				grafo = aux1;
			}
			else{
				grafo = aux2;
			}
			
			System.out.println("Tama�o final: " + grafo.size());
			return grafo;
		}
	}
	
	private static Grafo minCutBruteForce(Grafo grafo){
		//(Para numero de nodos = 3)
		Integer[] nodos = new Integer[grafo.size()];
		double min = Integer.MAX_VALUE;
		Grafo mejor = null;
		
		grafo.grafo.keySet().toArray(nodos);
		for(int i = 0; i < nodos.length; i++){
			Grafo aux = new Grafo();
			aux.copy(grafo);
			aux.merge(nodos[i], nodos[(i+1)%nodos.length]);
			if (evaluate(aux) <= min) {
				mejor = aux;
			}
		}
		
		return mejor;
	}
	
	private static double evaluate(Grafo f){
		return 0.0;
	}
	
	
	
}
