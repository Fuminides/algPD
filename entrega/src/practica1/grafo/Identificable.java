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
	
	/**
	 * Devuelve el identificador unico del objeto.
	 */
	int getId();
	
	/**
	 * Permite combinar el identificable pasado por parametro con this.
	 */
	void add(Identificable t);

	/**
	 * Permite inicializar los parametros de this con los de p,
	 * con el objetivo de convertir a this en una copia dura de p.
	 */
	void copy(Identificable p);

}
