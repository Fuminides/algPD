package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import practica1.grafo.Grafo;
import practica1.grafo.Nodo;

public class Main {
	

	private static final int metodo_aleatorio = 0;
	private static final int conjuntos_finales = 2;

	public static void main(String[] args) throws Exception {
		Grafo<Producto> grafo = new Grafo<>();
		ArrayList<Producto> productos = leerFicheroProd(args[0]);
		
		for ( Producto producto : productos){
			grafo.add(producto);
		}
		
		generarComprasRandom(grafo);
		int max = 400;
		
		while ( (grafo.grafo.size() > conjuntos_finales) && max > 0){
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
		
		for (int i : grafo.grafo.keySet()){
			Nodo<Producto> aux = grafo.get(i);
			System.out.println("Conjunto " + i +":\n\n" + aux.toString() + "\n");
		}
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
	
	private static void generarComprasRandom(Grafo<Producto> grafo){
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
		default:
			return aleatorio();
		}
	}
	
	private static int aleatorio(){
		Random generador = new Random();
		return generador.nextInt();
	}

}
