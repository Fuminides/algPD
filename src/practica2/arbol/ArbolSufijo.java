package practica2.arbol;

import java.util.ArrayList;

public class ArbolSufijo {
	
	private String word;
	public final static String FIN = "$";
	private Celda raiz;

	public ArbolSufijo(String palabra){
		word = palabra;
		raiz = new Celda();
		raiz.elemento = "";
	}
	
	//---------Contruir------------------------------
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
	
	//-------------------------------------------------------
	
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
	
	//APARTADO 1
	
	public ArrayList<Celda> caminoMayorProfundidad(){
		return caminoMayorProfundidad(raiz);
	}
	
	private ArrayList<Celda> caminoMayorProfundidad(Celda ptr){
		ArrayList<Celda> res = new ArrayList<>(), better = new ArrayList<>();
		int max = -1;
		
		res.add(ptr);

		for(Celda hijo:ptr.hijos){
			
			ArrayList<Celda> aux = caminoMayorProfundidad(hijo);
			String acum = "";
			for(int i = 0; i < aux.size()-2; i++){
				acum += aux.get(i).elemento;
			}
			if ( acum.length() > max){
				max = acum.length();
				better = aux;
			}
		}
		
		res.addAll(better);

		return res;
	}
	
	
	//APARTADO 2
	
	public ArrayList<String> buscarCopias() {
		ArrayList<String> cadenasBrutas = buscarCopias(raiz), resultados = new ArrayList<>();
		String actual = "";
		ArrayList<ArrayList<String>> matriz = new ArrayList<>();
		
		for(String cadena:cadenasBrutas){
			//System.out.println("Cadena bruta: "+ cadena);
			if ((!cadena.split("/")[0].replaceAll(" ", "").equals(""))&&(cadena.split("/").length == 2)){
				boolean exito = true;
				String tratada = cadena.split("/")[0].replaceAll(" ", "");
				int derecha = Integer.parseInt(cadena.split("/")[1]);
				int indexFinal, indexInicial;
				indexFinal = word.length()-derecha; 
				indexInicial = indexFinal-tratada.length()-1;
				
				String prologo; 
				if ( indexInicial < 0) prologo = FIN;
				else prologo = word.charAt(indexInicial)+"";
				String epilogo = word.charAt(indexFinal)+"";
				//System.out.println("Cadena: " + tratada + " Anterior " + prologo + ", Epilogo: " + epilogo);
	
				if (tratada.equals(actual)){
					for(ArrayList<String> mAux: matriz){
						if (prologo.equals(mAux.get(0))|epilogo.equals(mAux.get(1))){
							exito = false;
						}
					}
					if ( exito ){
						resultados.add(tratada);
					}
				}
				else{
					matriz.clear();
					actual = tratada;
					ArrayList<String> aux = new ArrayList<>();
					if ( indexInicial >= 0) aux.add(word.charAt(indexInicial)+"");
					else aux.add(FIN);
					aux.add(word.charAt(indexFinal)+"");
					matriz.add(aux);
				}
			}
		}
		
		return resultados;
		
	}

	 
	public ArrayList<String> buscarCopias(Celda ptr) {
		ArrayList<String> result = new ArrayList<>();
		
		if (ptr.hijos.size() > 1){
			boolean defecto = true, fin=true;
			for(Celda hijo: ptr.hijos){
				ArrayList<String> nietos= buscarCopias(hijo);	
				for(String nieto : nietos){
					if ( nieto.split("/").length>1){
						result.add(ptr.elemento + nieto);
						defecto = false;
					}
				}
				if(nietos.isEmpty()){ fin = true;}
			}			
			if ((fin)|(defecto)) result.add(ptr.elemento);
		}
		Integer barras_bajas = 0;
		for(int i = 0; i < ptr.elemento.length(); i++){
			barras_bajas += 1;
		}
		result.add(" /"+barras_bajas);
		return result;
		
	}

}
