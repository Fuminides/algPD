package practica1.grafo;

/**
 * Interfaz que representa objetos con ID, que representan informacion que puede ampliarse
 * con la informacion otro objeto del mismo tipo y que tienen un metodo para hacer una copia
 * profunda de otro.
 * 
 * Pensados para trabajar en un grafo y soportar operaciones de copia y merge de nodos.
 *
 */
public interface Identificable {
	
	int getId();
	
	void add(Identificable t);

	void copy(Identificable p);

}
