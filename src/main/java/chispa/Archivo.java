package chispa;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;

class Archivo implements Comparable<Archivo> {

	static final String ANTERIOR = "anterior";
	static final String PRINCIPAL = "principal";
	static final String SIGUIENTE = "siguiente";

	private long fecha;
	private String nombre;
	private String nombreBase;
	private String nombreCompleto;

	Archivo(Path p) {
		nombreCompleto = p.toAbsolutePath().toString();
		nombre = p.getFileName().toString();
		File f = p.toFile();
		fecha = f.lastModified();
		nombreBase = Util.nombreBase(nombre);
	}

	public int compareTo(Archivo arch) {
		long diferencia = getFecha() - arch.getFecha();
		return (int) diferencia;
	}

	private long getFecha() {
		return fecha;
	}

	private String getNombre() {
		return nombre;
	}

	String getNombreBase() {
		return nombreBase;
	}

	String getNombreBaseEncoded() {
		String nombreEncoded;
		try {
			nombreEncoded = URLEncoder.encode(getNombreBase(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			nombreEncoded = getNombre();
		}
		return nombreEncoded;
	}

	String getNombreCompleto() {
		return nombreCompleto;
	}

	String getNombreThumbnail(int ancho) {
		return String.format("%1s-%2d.%3s", getNombreBase(), ancho, Util.extension(getNombre()));
	}

	File getThumbnail() throws IOException {
		return Escalador.escalar(this);
	}

}
