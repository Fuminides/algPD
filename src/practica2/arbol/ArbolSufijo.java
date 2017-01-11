package practica2.arbol;

import java.util.ArrayList;

public class ArbolSufijo {
	
	private String word;
	public final static String FIN = "$";
	private Celda raiz;

	public ArbolSufijo(String palabra){
		word = palabra;
		raiz = new Celda();
	}
	
	public void naiveBuild(){
		ArrayList<String> prefijos = new ArrayList<>();
		
		for(int i = 0; i < word.length(); i++){
			prefijos.add(word.substring(i));
		}
		
		for (String prefijo:prefijos){
			Celda actual = raiz;
			while ( prefijo.length() > 0 ){
				if ( !actual.hasHijo(prefijo.charAt(0)+"")){
					actual.createHijo(prefijo.charAt(0)+"");
				}
				actual = actual.getHijo(prefijo.charAt(0)+"");
				prefijo = prefijo.substring(1);
			}
			
			actual.createHijo(FIN+"");
		}
	}
	
	public void compact(){
		compact(raiz);
	}
	
	private void compact(Celda ptr){
		if (ptr.hijos.size() != 0){
			for ( Celda padre:ptr.hijos){
				while ( padre.hijos.size() == 1){
					if (padre.hasHijo(FIN)) break;
					padre.elemento += padre.hijos.get(0).elemento;
					padre.hijos = padre.hijos.get(0).hijos;
				}
				compact(padre);
			}
		}
	}
	
	public ArrayList<String> nodos(){
		ArrayList<String> resultado = new ArrayList<>();
		
		for(Celda hijo: raiz.hijos){
			resultado.addAll(hijo.getNodos());
		}
		return resultado;
	}
	
	@Override
	public String toString(){
		String res = "";
		for(String elems: nodos()){
			res += " " + elems;
		}
		return res;
	}
	
	public ArrayList<Celda> caminoMayorProfundidad(){
		return caminoMayorProfundidad(raiz);
	}
	
	private ArrayList<Celda> caminoMayorProfundidad(Celda ptr){
		ArrayList<Celda> res = new ArrayList<>();
		int max = -1;
		
		res.add(0,ptr);

		for(Celda hijo:ptr.hijos){
			ArrayList<Celda> aux = caminoMayorProfundidad(hijo);
			String acum = "";
			for(int i = 0; i < aux.size()-2; i++){
				acum += aux.get(i).elemento;
			}
			if ( acum.length() > max){
				res.addAll(aux);
				max = acum.length();
			}
		}
		
		return res;
	}
	
	public ArrayList<String> buscarCopias() {
		ArrayList<String> res = new ArrayList<String>();
		
		for(Celda hijo:raiz.hijos){
			for(Celda nieto:hijo.hijos){
				res.addAll(buscarCopias(nieto, "","",hijo.elemento));
			}
		}
		
		return res;
	}

	 
	public ArrayList<String> buscarCopias(Celda ptr, String recorrido, String sol, String padre) {
		if (recorrido.startsWith(sol+ptr.elemento)){
			sol += ptr.elemento;
		}
		else{
			if ( sol != "") recorrido+=ptr.elemento;
			else {
				ArrayList<String> res = new ArrayList<String>();
				res.add(padre + "/" + sol + "/" + ptr.elemento);
				return res;
			}
		}
		ArrayList<String> res = new ArrayList<String>();

		if (ptr.hijos.size() > 0){
			for(Celda hijo: ptr.hijos){
				res.addAll(buscarCopias(hijo, new String(recorrido), new String(sol),padre));
			}
		}
		
		return res;
		
	}

}
