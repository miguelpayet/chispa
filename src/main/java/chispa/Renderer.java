package chispa;

import java.io.IOException;
import java.util.HashMap;

class Renderer {

	private final String IDENTIFICADOR = "<-- reemplazar cuerpo -->";
	String template;

	Renderer() throws IOException {
		cargarTemplate();
	}

	private void cargarTemplate() throws IOException {
		template = Util.readFile(Chispa.getConfiguracion().getNombreTemplate());
	}

	private void renderLink(Archivo archivo, String cssClass, StringBuilder sb) {
		sb.append("<div class=\"");
		sb.append(cssClass);
		sb.append("\">");
		if (archivo != null) {
			sb.append("<a href=\"/pagina/");
			sb.append(archivo.getNombreBase());
			sb.append("\">");
			sb.append(archivo.getNombreBase());
			sb.append("</a>");
		} else {
			sb.append("&nbsp;");
		}
		sb.append("</div>");
	}

	String renderPagina(Archivo archivo, Directorio directorio) {
		HashMap<String, Archivo> archivos = new HashMap<>();
		archivos.put(Archivo.ANTERIOR, directorio.getArchivoAnterior(archivo));
		archivos.put(Archivo.PRINCIPAL, archivo);
		archivos.put(Archivo.SIGUIENTE, directorio.getArchivoSiguiente(archivo));
		return template.replaceFirst(IDENTIFICADOR, renderPagina(archivos));
	}

	private String renderPagina(HashMap<String, Archivo> archivos) {
		StringBuilder sb = new StringBuilder();
		renderLink(archivos.get(Archivo.ANTERIOR), "izquierda", sb);
		renderThumbnail(archivos.get(Archivo.PRINCIPAL), sb);
		renderLink(archivos.get(Archivo.SIGUIENTE), "derecha", sb);
		return sb.toString();
	}

	private void renderThumbnail(Archivo archivo, StringBuilder sb) {
		sb.append("<div class=\"centro\">");
		sb.append("<p>");
		sb.append("<img class=\"thumbnail\" ");
		sb.append("src=\"/thumbnail/");
		sb.append(archivo.getNombreBase());
		sb.append("\"/>");
		sb.append("</p>");
		sb.append("</div>");
	}

}