package practica1;

/**
 * Ejecuta el algoritmo Karger o Karger-Stein para el problema del corte mínimo.
 * Permite usar tres generadores distintos de numeros aleatorios.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


import practica1.grafo.Grafo;
import practica1.grafo.Identificable;
import practica1.grafo.Nodo;
import practica1.random.CryptoRandom;
import practica1.random.SecuredRandom;
import practica1.random.VanillaRandom;

public class Main {
	
    private static long startTime = System.currentTimeMillis();
    
	//Metodos aleatorio: 0->Random Java, 1->SecureRandom Java, 2->SecureRandom Java mas fuerte
	private static int metodo_aleatorio = 0;
	private static int conjuntos_finales = 2;
	
	//Si utilizamos Karger-Stein (Por defecto no)
	private static boolean stein = false;
	//Numero de nodos en el caso base
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
		int z = 1;
		for (int i : grafo.grafo.keySet()){
			Nodo<Identificable> aux = grafo.get(i);
			System.out.println("Conjunto " + (z++) +":\n\n" + aux.toString() + "\n");
		}
		
		System.out.println("Coste del corte: " + evaluate(grafo));
		long timeEnd = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion: " + (timeEnd - startTime) + " milisegundos.");


	}
	
	/**
	 * Lee del fichero de productos y conecta los mismos segun el numero de veces que han sido comprados juntos.
	 * 
	 * @param string Nombre del fichero.
	 * @param grafo Grafo donde estan cargados ya los productos.
	 * @throws FileNotFoundException Si no existe el fichero de productos dado.
	 */
	private static void leerCompras(String string, Grafo grafo) throws FileNotFoundException {
		Scanner fichero = new Scanner(new File(string));
		
		while ( fichero.hasNextLine() ){
			int id = fichero.nextInt();
			fichero.next();
			fichero.nextInt();
			fichero.nextDouble();

			while (fichero.hasNextInt() ){
				int conect = fichero.nextInt(), cantidad = 0;
				if ( conect > id ) {
					cantidad = fichero.nextInt(); 
					grafo.addConection(id, conect, cantidad);
					//if (grafo.addConection(id, conect, cantidad)) System.out.println("Conectamos " + id + " y " + conect + " con fuerza: " + cantidad);
				}
			}
			fichero.nextLine();
		}
		
		fichero.close();
	}

	/**
	 * Crea un grafo donde se encuentran contenidos los productos con sus datos básicos.
	 * 
	 * @param ruta Ruta del fichero.
	 * @return Grafo con los productos.
	 * @throws FileNotFoundException Si no existe el fichero.
	 */
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
	
	/**
	 * Devuelve un numero aleatorio utilizando el generador espcificado.
	 * 
	 * @return numero aleatorio.
	 */
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
	 * Aplica el algoritmo Karger-Stein (o Karger si el numero de conjuntos finales = 2)
	 * y devuelve el grafo resultante de buscar el minimum cut.
	 * 
	 * @param grafo Grafo con los productos y las ventas entre ellos.
	 */
	private static Grafo kargerStein(Grafo grafo, int conjuntos_end) {
		if (grafo.size() == 2){ //Si hay dos nodos, ya hemos terminado.
			return grafo;
		}
		else if (grafo.size() == CASO_BASE){ //Si lo podemos resolver por fuerza bruta, lo hacemos.
			return minCutBruteForce(grafo);
		}
		else {
			int max = 400; //Ponemos un tope de intentos, por si acaso no es posible reducir más el grafo.
			
			while ( (grafo.grafo.size() > conjuntos_end) && max > 0){
				int nodo1 = grafo.get(); //Buscamos un nodo cualquiera
				if ( grafo.get(nodo1).getConections().length > 0){ //Si tiene conxiones
					int i = 0, j;
					//Buscamos maximizar la probabilidad de hacer merge con el nodo con el que mas
					//peso tenga la conexion.
					HashMap<Integer, Double> conexiones = grafo.get(nodo1).getWeights();
					ArrayList<Integer> prob = new ArrayList<Integer>();
					for ( Integer nodoC: conexiones.keySet()){ 
						j = i;
						//Anadimos en la lista una participacion igual al valor de la conexion
						for( ; i < j + conexiones.get(nodoC); i++){
							prob.add(nodoC);
						}
					}
					//Elegimos un nodo de la lista de participaciones y hacemos merge
					int nodo2 = prob.get(Math.abs(rand()) % prob.size());
					grafo.merge(nodo1, nodo2);
				}
				else{
					max--;
					if ( max == 0){
						System.out.println("Lo sentimos, no hay suficientes conexiones para hacer el corte. Quedan " + grafo.size() + " nodos.");
					}
				}
			}
			if ( conjuntos_end != 2 ){
				//Aplicamos recursivamente el algoritmo en dos copias.
				Grafo aux1 = new Grafo(), aux2 = new Grafo();
				aux1.copy(grafo);
				aux2.copy(grafo);
				
				aux1 = kargerStein(aux1, (int) (aux1.size()*1.0/Math.sqrt(2)));
				aux2 = kargerStein(aux2, (int) (aux2.size()*1.0/Math.sqrt(2)));
				
				//Y nos quedamos con la mejor
				if ( evaluate(aux1) <= evaluate(aux2) ){
					grafo = aux1;
				}
				else{
					grafo = aux2;
				}
			}
			
			return grafo;
		}
	}
	
	/**
	 * Resuelve por fuerza bruta el problema del min cut para un grafo de tres nodos.
	 * 
	 * @param grafo Grafo de tres nodos.
	 * @return Grafo partido por su min cut.
	 */
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
	
	/**
	 * Devuelve la suma de los pesos de todas las conexiones de un grafo.
	 * (Nos evalua lo bueno que es el corte del grafo)
	 *  
	 * @param f grafo cualquiera.
	 * @return La suma de los pesos de las conexiones.
	 */
	private static double evaluate(Grafo f){
		double pesos = 0.0;
		for(Integer nodo: f.grafo.keySet()){
			for(Integer conexion: f.get(nodo).getWeights().keySet()){
				pesos += f.get(nodo).getWeights().get(conexion);
			}
		}
		return pesos/2;
	}
	
	
	
}
