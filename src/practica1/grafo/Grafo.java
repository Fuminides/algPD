package practica1.grafo;

import java.util.HashMap;

import practica1.Main;

public class Grafo <T extends Identificable> {
	
	public HashMap<Integer, Nodo<T>> grafo;

	public Grafo() {
		grafo = new HashMap<>();
	}
	
	public int size(){ return grafo.size(); }
	
	public void add(Nodo<T> n){
		grafo.put(n.getId(), n);
	}
	
	public void add(T n){
		Nodo<T> nodo = new Nodo<>(n.getId(), n);
		
		add(nodo);
	}
	
	public void remove(Nodo<T> n){
		int[] conexiones = n.getConections();
		
		for (int eliminar : conexiones){
			grafo.get(eliminar).removeConection(n.getId());
		}
		
		grafo.remove(n.getId());
	}
	
	public Nodo<T> get(int id){
		return grafo.get(id);
	}
	
	public void addConection(int id, int id2){
		grafo.get(id).addConection(id2);
		grafo.get(id2).addConection(id);
	}
	
	public void addConection(int id, int id2, double peso){
		grafo.get(id).addConectionWeight(id2, peso);
		grafo.get(id2).addConectionWeight(id, peso);
	}
	
	public void merge(int id, int id2){
		Nodo<T> n1 = grafo.get(id), n2 = grafo.get(id2);
		
		//n1.merge(n2);
		for(int conexiones : n2.getConections()){
			addConection(n1.getId(), conexiones);
		}
		n1.addInfo(n2);
		remove(n2);
	}
	
	public int get(){
		Integer[] aux = new Integer[100];
		
		aux = grafo.keySet().toArray(aux);
		
		return aux[Math.abs(Main.rand() % grafo.keySet().size())];
	}

}
