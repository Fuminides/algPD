package practica2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FastaUtils {

	/**
	 * Elige un gen aleatorio del fichero fasta y lo devuelve.
	 * 
	 * @param prueba
	 * @return
	 */
	public static ArrayList<String> procesarFastaCompleto(File prueba, ArrayList<String> nombres) {
		System.out.println("s");

		ArrayList<String> gen = new ArrayList<>();
		try {
			Scanner leerfichero = new Scanner(prueba);
			while(leerfichero.hasNextLine()){
				String reg = leerfichero.nextLine();
				if (reg.startsWith(">")) {
					String line = leerfichero.nextLine();
					nombres.add(reg.replaceAll("gene", ""));
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
			e.printStackTrace();
		}
		return gen;
	}
	
	/**
	 * Elige un gen aleatorio del fichero fasta y lo devuelve.
	 * 
	 * @param prueba nombre del fichero.
	 * @return un gen aleatorio en el fichero.
	 */
	public static String leerFasta(File prueba) {
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
			e.printStackTrace();
		}
		System.out.println("Gen elegido: "+ genescogido.replaceAll("gene", "") +"\n" + gen);
		return gen;
	}

}
