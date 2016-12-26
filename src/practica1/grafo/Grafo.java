package practica1.grafo;

import java.util.HashMap;

import practica1.Main;
import practica1.Producto;

public class Grafo {
	
	public HashMap<Integer, Nodo<Identificable>> grafo;

	public Grafo() {
		grafo = new HashMap<>();
	}
	
	public int size(){ return grafo.size(); }
	
	public void add(Nodo<Identificable> n){
		grafo.put(n.getId(), n);
	}
	
	public void add(Identificable n){
		Nodo<Identificable> nodo = new Nodo<>(n.getId(), n);
		
		add(nodo);
	}
	
	public void remove(Nodo<Identificable> n){
		int[] conexiones = n.getConections();
		
		for (int eliminar : conexiones){
			grafo.get(eliminar).removeConection(n.getId());
		}
		
		grafo.remove(n.getId());
	}
	
	public Nodo<Identificable> get(int id){
		return grafo.get(id);
	}
	
	public boolean addConection(int id, int id2){
		if (!grafo.get(id).isConnected(id2)){
			grafo.get(id).addConection(id2);
			grafo.get(id2).addConection(id);
			
			return true;
		}
		return false;
	}
	
	public boolean addConection(int id, int id2, double peso){
		if (!grafo.get(id).isConnected(id2)){
			grafo.get(id).addConectionWeight(id2, peso);
			grafo.get(id2).addConectionWeight(id, peso);
			
			return true;
		}
		
		return false;
	}
	
	public void merge(int id, int id2){
		Nodo<Identificable> n1 = grafo.get(id), n2 = grafo.get(id2);
		
		//n1.merge(n2);
		for(int conexiones : n2.getConections()){
			if (conexiones != id ) addConection(n1.getId(), conexiones, n2.getWeight(conexiones));
		}
		n1.addInfo(n2);
		remove(n2);
	}
	
	public int get(){
		Integer[] aux = new Integer[100];
		
		aux = grafo.keySet().toArray(aux);
		
		return aux[Math.abs(Main.rand()) % grafo.keySet().size()];
	}
	
	public void copy(Grafo f){
		for(Object id : f.grafo.keySet()){
			Nodo<Identificable> numb = new Nodo<Identificable>(0, new Producto());
			numb.copy(f.grafo.get(id));
			grafo.put((Integer)id, numb);
		}
	}

}
