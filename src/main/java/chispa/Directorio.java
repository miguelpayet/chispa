package chispa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

class Directorio {

	private interface ArchivoPosicion {
		Map.Entry<String, Archivo> posicion(Archivo arch);
	}

	private TreeMap<String, Archivo> archivos;
	private Path ruta;

	Directorio(String rutaConfig) throws IOException {
		ruta = Paths.get(rutaConfig);
		archivos = new TreeMap<>();
		leerArchivos();
	}

	void colocarArchivo(Path p) {
		Archivo a = new Archivo(p);
		archivos.put(a.getNombreBase(), a);
	}

	Archivo getArchivo(String nombre) {
		return archivos.get(Util.nombreBase(nombre));
	}

	private Archivo getArchivo(Archivo archActual, ArchivoPosicion archOper) {
		Map.Entry<String, Archivo> entry = archOper.posicion(archActual);
		Archivo archNuevo = null;
		if (entry != null) {
			archNuevo = entry.getValue();
		}
		return archNuevo;
	}

	Archivo getArchivoAnterior(Archivo archivo) {
		return getArchivo(archivo, (Archivo) -> archivos.lowerEntry(archivo.getNombreBase()));
	}

	Archivo getArchivoPrimero() {
		return archivos.firstEntry().getValue();

	}

	Archivo getArchivoSiguiente(Archivo archivo) {
		return getArchivo(archivo, (Archivo) -> archivos.higherEntry(archivo.getNombreBase()));
	}

	private void leerArchivos() throws IOException {
		ArchivoFileVisitor visitor = new ArchivoFileVisitor(this);
		Files.walkFileTree(ruta, visitor);
	}

}

