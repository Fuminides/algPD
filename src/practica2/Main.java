package practica2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import practica2.arbol.ArbolSufijo;
import practica2.arbol.Celda;

public class Main{
	
	public static void main (String[] args){
		ArrayList<String> genes = null; 
		boolean full_mode = false;
		for (String arg:args){
			if (arg.equals("-f")) full_mode = true;
		}
		String palabra = args[1];
		File prueba = new File(palabra);
		if (prueba.exists()){
			if (!full_mode) palabra = leerFasta(prueba);
			else genes = procesarFastaCompleto(prueba);
		}
		
		if ( args[0].equals("-r")){
			if ( !full_mode ) {
				ArbolSufijo arbol = new ArbolSufijo(palabra);
				arbol.naiveBuild();
				arbol.compact();
				System.out.println("Repeticion mas larga: " + repeticionLarga(arbol));
			}
			else{
				int i = 0;
				for (String word: genes){
					ArbolSufijo arbol = new ArbolSufijo(word);
					arbol.naiveBuild();
					arbol.compact();
					System.out.println("Gen " + i + ":");
					i++;
					System.out.println("Repeticion mas larga: " + repeticionLarga(arbol));
				}
			}
		}
		else if (args[0].equals("-c")){
			if ( !full_mode ){
				ArbolSufijo arbol = new ArbolSufijo(palabra);
				arbol.naiveBuild();
				arbol.compact();
				System.out.println("Substrings repetidos:");
				String anterior = "";
				for(String copia : arbol.buscarCopias()){
						if ((copia.length()>1)&&(!copia.equals(anterior))) System.out.println(copia);
						anterior = copia;
				}
			}
			else{
				int i = 0;
				for (String word:genes){
					ArbolSufijo arbol = new ArbolSufijo(word);
					arbol.naiveBuild();
					arbol.compact();
					System.out.println("Substrings repetidos:");
					String anterior = "";
					System.out.println("Gen " + i + ":");
					for(String copia : arbol.buscarCopias()){
							i++;
							if ((copia.length()>1)&&(!copia.equals(anterior))) System.out.println(copia);
							anterior = copia;
					}
				}
			}
		}
	}
	
	/**
	 * Elige un gen aleatorio del fichero fasta y lo devuelve.
	 * 
	 * @param prueba
	 * @return
	 */
	private static String leerFasta(File prueba) {
		String gen = "", genescogido = null;
		try {
			ArrayList<Integer> cabeceras = new ArrayList<>();
			int contador = 0;
			Scanner leerfichero = new Scanner(prueba);
			while(leerfichero.hasNextLine()){
				if (leerfichero.nextLine().startsWith(">")) {
					cabeceras.add(contador);
					contador++;
				}
			}
			Random generador = new Random();
			int genElegido = cabeceras.get(Math.abs(generador.nextInt()%(cabeceras.size())));
			contador = 0;
			leerfichero.close();
			leerfichero = new Scanner(prueba);
			while(contador < cabeceras.get(genElegido)){
				leerfichero.nextLine();
				contador++;
			}
			genescogido = leerfichero.nextLine();
			while (!genescogido.startsWith(">")) genescogido = leerfichero.nextLine();
			String line = leerfichero.nextLine();
			while (!line.startsWith(">") ){
				gen+=line.replaceAll("\n", "");
				line = leerfichero.nextLine();
			}
			leerfichero.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Gen elegido: "+ genescogido +"\n" + gen);
		return gen;
	}
	
	/**
	 * Elige un gen aleatorio del fichero fasta y lo devuelve.
	 * 
	 * @param prueba
	 * @return
	 */
	private static ArrayList<String> procesarFastaCompleto(File prueba) {
		ArrayList<String> gen = new ArrayList<>();
		try {
			Scanner leerfichero = new Scanner(prueba);
			while(leerfichero.hasNextLine()){
				if (leerfichero.nextLine().startsWith(">")) {
					String line = leerfichero.nextLine();
					String gento = "";
					while (!line.startsWith(">") ){
						gento+=line.replaceAll("\n", "");
						line = leerfichero.nextLine();
					}
					gen.add(gento);
				}
			}
			leerfichero.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gen;
	}

	public static String repeticionLarga(ArbolSufijo arbol){		
		ArrayList<Celda> camino = arbol.caminoMayorProfundidad();
		String res="";
		for(int i = 1; i < camino.size()-2; i++){
			res+=camino.get(i).elemento;
		}
		return res;
	}
	
}