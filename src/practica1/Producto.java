package practica1;

import java.util.ArrayList;

import practica1.grafo.Identificable;

public class Producto implements Identificable{

	int id, unidades;
	double precio;
	String nombre;
	ArrayList<Identificable> productosMergeados;
	
	public Producto() {}
	
	public Producto(int i, int u, double p, String n){
		id = i;
		unidades = u;
		precio = p;
		nombre = n;
		productosMergeados = new ArrayList<>();
	}
	
	@Override
	public String toString(){
		String basic =  
				"Id: "  + id +
				"\nNombre: " + nombre +
				"\nPrecio: " + precio +
				" euros\nUnidades: " + unidades;
		for(Identificable product : productosMergeados){
			basic += "\n" + product.toString();
		}
		return basic;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void add(Identificable otro) {
		productosMergeados.add(otro);
		
	}
	
	@Override
	public void copy(Identificable ident){
		Producto p = (Producto) ident;
		id = p.id;
		unidades = p.unidades;
		precio = p.precio;
		nombre = p.nombre;
		productosMergeados = new ArrayList<>();
		
		for (Identificable producto: p.productosMergeados){
			productosMergeados.add(producto);
		}
		
	}

}
