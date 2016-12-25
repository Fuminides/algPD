package practica1.grafo;

import java.util.HashMap;

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
	
	public void addConection(int nodo){
		conexiones.put(nodo, 0.0);
	}
	
	public void addConectionWeight(int nodo, double peso){
		conexiones.put(nodo, peso);
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
	
	public void merge(Nodo<T> nodo){
		conexiones.putAll(nodo.conexiones);
	}
	
	public int[] getConections(){
		int i = 0;
		int[] nodos = new int[conexiones.size()];
		
		for(Integer nodo : conexiones.keySet()){
			nodos[i] = nodo;
			i++;
		}
		
		return nodos;
	}

	public T getProducto() {
		return producto;
	}

	public void setProducto(T producto) {
		this.producto = producto;
	}
	
	public void addInfo(Nodo<T> prod){
		producto.add(prod.getProducto());
	}
	
	@Override
	public String toString(){
		return producto.toString();
	}
	
	public void copy(Nodo<T> n){
		id = n.id;
		producto.copy(n.producto);
		conexiones = new HashMap<>();
		for(Object id : n.conexiones.keySet()){
			double numb = n.conexiones.get(id);
			conexiones.put((Integer)id, numb);
		}
	}

}
