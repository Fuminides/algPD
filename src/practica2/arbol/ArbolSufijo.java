package practica2.arbol;

import java.util.ArrayList;

public class ArbolSufijo {
	
	private String word;
	public final static String FIN = "$";
	private Celda raiz;

	public ArbolSufijo(String palabra){
		word = palabra;
		raiz = new Celda();
		raiz.elemento = "*";
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
	
	public void ukkonenBuild(){
		ArrayList<String> prefijos = new ArrayList<>();
		
		for(int i = 1; i <= word.length(); i++){
			prefijos.add(word.substring(0,i));
		}
		for (int i = 0; i < word.length(); i++){
			for(){
				if ( prefijos.get(i).length() > 0){
					System.out.println("Inserto " + prefijos.get(i));
					ukkonenAux(raiz, prefijos.get(i));
				}
			}
		}
	}
	
	private void ukkonenAux(Celda ptr, String prefijo){
		String aux = "", nuevoElem = "";
		
		for(int i = 0; i < ptr.elemento.length();i++){
			try{
				if ( ptr.elemento.charAt(i) == prefijo.charAt(i)){
					prefijo = prefijo.substring(i+1);
					nuevoElem = ptr.elemento.substring(i+1);
					aux += ptr.elemento.charAt(i);
				}
				else{
					break;
				}
			} catch (StringIndexOutOfBoundsException e){
				break;
			}
		}
		if ( ukkonenRegla1(ptr, aux) ){
			System.out.println("Regla 1 aplicada");
			ptr.elemento = ptr.elemento + prefijo;
			return;
		}
		else if ( ukkonenRegla2(ptr, aux, prefijo) ){
			System.out.println("Regla 2 aplicada, anyado " + prefijo);
			if ( ptr != raiz){
				Celda nueva = new Celda(nuevoElem);
				nueva.hijos = ptr.hijos;
				ptr.hijos = new ArrayList<>();
				ptr.hijos.add(nueva);
				ptr.createHijo(prefijo);
				ptr.elemento = aux;
			}
			else{
				ptr.createHijo(prefijo);
			}
			return;

		}
		else if ( (aux.length() != ptr.elemento.length()) && (prefijo.length()==0) ){
			System.out.println("Regla 3 aplicada"); //No hacemos nada
			return;
		}
			
		for ( Celda hijo: ptr.hijos){
			if ( prefijo.length() > hijo.elemento.length()) {
				if ( prefijo.startsWith(hijo.elemento) ){
					ukkonenAux(hijo, prefijo);
				}
			}
			else{
				if ( hijo.elemento.startsWith(prefijo) ){
					System.out.println("Ocurre");
					ukkonenAux(hijo, prefijo);
				}
			}
		}
		
	}

	private boolean ukkonenRegla1(Celda ptr, String comun) {
		if ((ptr.hijos.size() == 0) && (ptr.elemento.length() == comun.length())){
			return true;
		}
		return false;
	}
	
	private boolean ukkonenRegla2(Celda ptr, String aux, String prefijo){
		for ( Celda hijo: ptr.hijos){
			if ( prefijo.length() > hijo.elemento.length()) {
				if ( prefijo.startsWith(hijo.elemento) ){
					return false;
				}
			}
			else{
				if ( hijo.elemento.startsWith(prefijo) ){
					System.out.println("Ocurre");
					return false;
				}
			}
		}
		if (aux.length() != ptr.elemento.length() && (prefijo.length()>0)){
			return true;
		}
		return false;
	}
	
	public void explicitar(){
		explicitar(raiz);
	}
	
	private void explicitar(Celda raiz2) {
		if ( raiz2.hijos.size() == 0 ){
			raiz2.createHijo(FIN);
		}
		else{
			for(Celda hijo:raiz2.hijos){
				explicitar(hijo);
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
	
	public static void main(String[] args){
		String prueba = "xa";
		ArbolSufijo arbol = new ArbolSufijo(prueba);
		arbol.ukkonenBuild();
		arbol.explicitar();
		System.out.println(arbol.toString());
	}

}
