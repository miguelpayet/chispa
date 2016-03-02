package chispa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Util {

	static String extension(String nombre) {
		String extension;
		int punto = nombre.lastIndexOf(".");
		if (punto != -1) {
			extension = nombre.substring(punto + 1);
		} else {
			extension = "";
		}
		return extension.toLowerCase();
	}

	static String nombreBase(String nombre) {
		String nombreBase;
		int punto = nombre.lastIndexOf(".");
		if (punto != -1) {
			nombreBase = nombre.substring(0, punto);
		} else {
			nombreBase = nombre;
		}
		return nombreBase.toLowerCase();
	}

	static String readFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Util.class.getClassLoader().getResourceAsStream(filename)));
		StringBuilder sb = new StringBuilder();
		String aux;
		while ((aux = reader.readLine()) != null) {
			sb.append(aux);
		}
		reader.close();
		return sb.toString();
	}

}
