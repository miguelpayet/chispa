package chispa;

import java.util.ArrayList;

public class PaginaWeb {

	private ArrayList<Archivo> archivos;
	private String ruta;

	public PaginaWeb() {
	}

	public ArrayList<Archivo> getArchivos() {
		return archivos;
	}

	public String getRuta() {
		return ruta;
	}

	private void leerArchivos() {
	}

	private PaginaWeb leerPagina() {
		return null;
	}

	private void refrescarArchivos() {
	}

	public void setArchivos(ArrayList<Archivo> archivos) {
		this.archivos = archivos;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


}
