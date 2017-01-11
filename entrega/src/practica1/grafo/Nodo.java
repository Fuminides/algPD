package practica1.grafo;

import java.util.HashMap;
/**
 * Clase que representa a un nodo de un grafo.
 * 
 * @param <T> Debe implementar Identificable.
 */
public class Nodo <T extends Identificable> {
	
	private int id;
	private T producto;
	
	private HashMap<Integer, Double> conexiones;
	
	public Nodo(int i, T produc){
		setId(i);
		conexiones = new HashMap<>();
		setProducto(produc);
	}
	
	public boolean isConnected(int nodo){
		return conexiones.containsKey(nodo);
	}
	/**
	 * Anyade una conexion sin peso entre este nodo y el dado.
	 */
	public void addConection(int nodo){
		conexiones.put(nodo, 0.0);
	}
	
	/**
	 * Anyade una conexion con peso entre este nodo y el dado. Si
	 * tal conexion ya existia, se suma el peso dado al ya existente.
	 */
	public void addConectionWeight(int nodo, double peso){
		if (conexiones.containsKey(nodo)){
			conexiones.put(nodo, conexiones.get(nodo) + peso);
		}
		else{
			conexiones.put(nodo, peso);
		}
	}
	
	public void removeConection(int nodo){
		conexiones.remove(nodo);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Anyade todas las conexiones del nodo dado a este.
	 */
	public void merge(Nodo<T> nodo){
		conexiones.putAll(nodo.conexiones);
	}
	
	/**
	 * Devuelve en un array todas las conexiones que posee este nodo.
	 */
	public int[] getConections(){
		int i = 0;
		int[] nodos = new int[conexiones.size()];
		
		for(Integer nodo : conexiones.keySet()){
			nodos[i] = nodo;
			i++;
		}
		
		return nodos;
	}
	
	/**
	 * Devuelve un HashMap con todas las conexiones de este nodo. (Clave:id del nodo, Valor: peso de la conexion).
	 */
	public HashMap<Integer, Double> getWeights(){
		return conexiones;
	}

	public T getProducto() {
		return producto;
	}

	public void setProducto(T producto) {
		this.producto = producto;
	}
	
	/**
	 * Anyade al elemento la informacion contenida en el elemento de otro nodo.
	 * @param prod
	 */
	public void addInfo(Nodo<T> prod){
		producto.add(prod.getProducto());
	}
	
	/**
	 * Constructor de copia profunda.
	 */
	public void copy(Nodo<T> n){
		id = n.id;
		producto.copy(n.producto);
		conexiones = new HashMap<>();
		for(Object id : n.conexiones.keySet()){
			double numb = n.conexiones.get(id);
			conexiones.put((Integer)id, numb);
		}
	}
	
	/**
	 * Devuelve el peso de la conexion de este nodo con el dado.
	 * @param conexiones2 debe ser un nodo conectado a este nodo.
	 */
	public double getWeight(int conexiones2) {
		return conexiones.get(conexiones2);
	}
	
	/**
	 * Devuelve el objeto del nodo en formato String.
	 */
	@Override
	public String toString(){
		return producto.toString();
	}

}
