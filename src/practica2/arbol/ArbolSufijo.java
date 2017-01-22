package practica2.arbol;

import java.util.ArrayList;
/**
 * Clase que representa un arbol de sufijos.Se construyen utilizando el
 * algoritmo sencillo de coste O(n^2). Permite buscar la copia mas larga
 * y todas las copias presentes en el arbol.
 * 
 * @author Javier Fumanal Idocin, 684229
 *
 */
public class ArbolSufijo {
	
	private String word;
	private Celda raiz;
	
	public final static String FIN = "$";
	public final static int PROLOGO = 0;
	public final static int EPILOGO = 1;
	public final static int CADENA = 2;
	


	public ArbolSufijo(String palabra){
		word = palabra;
		raiz = new Celda();
		raiz.elemento = "";
	}
	
	//---------Contruir------------------------------
	/**
	 * Construye el arbol de sufijos utilizando el algoritmo basico.
	 */
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
	
	/**
	 * Reduce el tamanyo del arbol.
	 */
	public void compact(){
		compact(raiz);
	}
	/**
	 * Reduce el tamano del arbol a partir del nodo dado, usandolo como raiz.
	 * @param ptr raiz del arbol a reducir.
	 */
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
	
	//------------------MISC-----------------
	/**
	 * Devuelve un listado con todos los elementos del arbol.
	 * @return
	 */
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
	
	//--------------------------------------------
	//APARTADO 1
	//-------------------------------------------
	/**
	 * Devuelve el camino de mayor profundidad del arbol.
	 * Camino mas largo = Duplicado mas largo en la cadena.
	 * 
	 * @return una lista con los nodos ordenados en orden.
	 */
	public ArrayList<Celda> caminoMayorProfundidad(){
		return caminoMayorProfundidad(raiz);
	}
	
	/**
	 * Devuelve el camino mas largo a partir del nodo dado.
	 * 
	 * @param ptr nodo origen del camino.
	 * @return lista de nodos ordenados con el camiano.
	 */
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
	
	
	//--------------------------------------------
	//APARTADO 2
	//--------------------------------------------
	
	/**
	 * Devuelve todos las copias maximales aparecidas en el String ordenados segun aparicion.
	 * 
	 * @return Devuelve una lista con los duplicados.
	 */
	public ArrayList<String> buscarCopias() {
		ArrayList<String> cadenasBrutas = buscarCopias(raiz), resultados = new ArrayList<>();
		ArrayList<ArrayList<String>> matriz = new ArrayList<>();
		for(String a:cadenasBrutas) System.out.println(a);
		for(String cadena:cadenasBrutas){
			if ( cadena.split("/")[0].replaceAll(" ", "").length() > 1){
				if ((!cadena.split("/")[0].replaceAll(" ", "").equals(""))&&(cadena.split("/").length == 2)){
					String tratada = cadena.split("/")[0].replaceAll(" ", "");
					int derecha = Integer.parseInt(cadena.split("/")[1]);
					int indexFinal, indexInicial;
					indexFinal = word.length()-derecha; 
					indexInicial = indexFinal-tratada.length();
					
					String prologo, epilogo; 
					if ( indexInicial == 0) prologo = FIN;
					else prologo = word.charAt(indexInicial-1)+"";
					if ( indexFinal >= word.length()-1 ) epilogo = FIN; 
					else epilogo = word.charAt(indexFinal)+"";
					ArrayList<String> aux = new ArrayList<>();
					aux.add(prologo); aux.add(epilogo); aux.add(tratada);
					System.out.println("Prologo: " + prologo + " Epilogo: " + epilogo + " Cadena: " + tratada + " Posicion: " + indexInicial + " Final: " + indexFinal);
					matriz.add(aux);
				}
			}
		}
		
		for(ArrayList<String> candidato: matriz){
			boolean exito = true;
			for(ArrayList<String> test: matriz){
				if ( test.get(CADENA).contains(candidato.get(CADENA)) && (!test.get(CADENA).equals(candidato.get(CADENA)))){
					int index = test.get(CADENA).indexOf(candidato.get(CADENA));
					if ( candidato.get(CADENA).equals("bc")) System.out.println(index);
					if (index==0){
						if (test.get(PROLOGO).equals(candidato.get(PROLOGO))
								&& (candidato.get(EPILOGO).equals(test.get(CADENA).charAt(index+candidato.get(CADENA).length())))) exito = false;
					}
					else if (index+candidato.get(CADENA).length() == test.get(CADENA).length()){
						if ( candidato.get(CADENA).equals("bc")) System.out.println("Ep: " + candidato.get(EPILOGO));
						if (test.get(EPILOGO).equals(candidato.get(EPILOGO))
								&& (candidato.get(PROLOGO).equals(test.get(CADENA).charAt(index-1)+""))) exito = false;
					}
					else{
						exito = false;
					}
				}
				
			}
			if ( exito ){
				resultados.add(candidato.get(CADENA));
			}
		}
		
		return resultados;
	}
		
	
		

	/**
	 * Devuelve los duplicados en un arbor a partir de un nodo dado.
	 * (No distingue entre maximales y no).
	 * 
	 * @param ptr origen de la busqueda.
	 * @return devuelve la copia
	 */
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
		
		/*Integer barras_bajas = 0;
		for(int i = 0; i < ptr.elemento.length(); i++){
			barras_bajas += 1;
		}*/
		if (!ptr.elemento.equals(FIN)) result.add(" /"+ptr.elemento.length());
		else result.add(" /0");
		return result;
		
	}

}
