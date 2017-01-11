package practica1.grafo;

import java.util.HashMap;

import practica1.Main;
import practica1.Producto;
/**
 * Clase que representa a un grafo dirigido/no dirigido con pesos en sus aristas.
 *
 */
public class Grafo {
	
	public HashMap<Integer, Nodo<Identificable>> grafo;

	public Grafo() {
		grafo = new HashMap<>();
	}
	
	public int size(){ return grafo.size(); }
	/**
	 * Anyade un nodo al grafo.
	 */
	public void add(Nodo<Identificable> n){
		grafo.put(n.getId(), n);
	}
	
	/**
	 * Anyade un nuevo elemento al grafo, metiendolo automaticamente dentro de un nodo.
	 */
	public void add(Identificable n){
		Nodo<Identificable> nodo = new Nodo<>(n.getId(), n);
		add(nodo);
	}
	
	/**
	 * Elimina un nodo del grafo y todas sus conexiones.
	 * @param n nodo que esta presente en el grafo.
	 */
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
	
	/**
	 * Anyade una conexion bidireccional entre los dos nodos dados si no estan ya conectados.
	 * @param id es un identificador de un nodo presente en el grafo.
	 * @param id2 es un identificador de un nodo presente en el grafo y distinto de id.
	 * @return Verdadero si se ha podido anyadir la conexion. Falso en caso contrario.
	 */
	public boolean addConection(int id, int id2){
		if (!grafo.get(id).isConnected(id2)){
			grafo.get(id).addConection(id2);
			grafo.get(id2).addConection(id);
			
			return true;
		}
		return false;
	}
	
	/**
	 * Anyade una conexion bidireccional en el grafo, con un peso dado.
	 * @param id debe ser un identificador valido de un nodo en el grafo.
	 * @param id2
	 * @param peso recomendable que sea positivo.
	 * @return true si solo si se ha podido anyadir la conexion.
	 */
	public boolean addConection(int id, int id2, double peso){
		grafo.get(id).addConectionWeight(id2, peso);
		grafo.get(id2).addConectionWeight(id, peso);
			
		return true;
	}
	
	/**
	 * Junta dos nodos del grafo en un unico nodo que posee las mismas conexiones que ambos nodos.
	 * En caso de compartir conexiones, suma los pesos.
	 * 
	 * @param id
	 * @param id2
	 */
	public void merge(int id, int id2){
		Nodo<Identificable> n1 = grafo.get(id), n2 = grafo.get(id2);
		
		//n1.merge(n2);
		for(int conexiones : n2.getConections()){
			if (conexiones != id ) addConection(n1.getId(), conexiones, n2.getWeight(conexiones));
		}
		n1.addInfo(n2);
		remove(n2);
	}
	
	/**
	 * Devuelve un nodo aleatorio del grafo.
	 */
	public int get(){
		Integer[] aux = new Integer[100];
		
		aux = grafo.keySet().toArray(aux);
		
		return aux[Math.abs(Main.rand()) % grafo.keySet().size()];
	}
	/**
	 * Convierte a este grafo en una copia profunda del pasado por parametro.
	 * @param f grafo a copiar.
	 */
	public void copy(Grafo f){
		for(Object id : f.grafo.keySet()){
			Nodo<Identificable> numb = new Nodo<Identificable>(0, new Producto());
			numb.copy(f.grafo.get(id));
			grafo.put((Integer)id, numb);
		}
	}

}
