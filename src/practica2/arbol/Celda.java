package practica2.arbol;

import java.util.ArrayList;

/**
 * Celda que forma parte un arbol de sufijos. Los atributos
 * son publicos para un manejo m�s c�modo.
 * 
 * @author Javier Fumanal Idocin, 684229
 */
public class Celda {

	public ArrayList<Celda> hijos;
	public String elemento;
	
	public Celda(){
		hijos = new ArrayList<>();
	}
	
	public Celda(String elemento) {
		hijos = new ArrayList<>();
		this.elemento = elemento;
	}

	public boolean hasHijo(String charAt) {
		for(Celda hijo:hijos){
			if ( hijo.elemento.equals(charAt)){
				return true;
			}
		}
		
		return false;
	}

	public void createHijo(String fIN) {
		hijos.add(new Celda(fIN));
	}

	public Celda getHijo(String charAt) {
		for(Celda hijo:hijos){
			if ( hijo.elemento.equals(charAt)){
				return hijo;
			}
		}
		return null;
	}
	
	public void removeHijo(String charAt){
		for(Celda hijo:hijos){
			if ( hijo.elemento.equals(charAt)){
				hijos.remove(hijo);
			}
		}
	}

	public ArrayList<String> getNodos() {
		ArrayList<String> resultado = new ArrayList<>();
		
		resultado.add(elemento);
		for(Celda hijo: hijos){
			resultado.addAll(hijo.getNodos());
		}
		return resultado;
	}

}
