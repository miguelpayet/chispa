package chispa;

import java.io.IOException;
import java.util.HashMap;

class Renderer {

	private String template;

	Renderer() throws IOException {
		cargarTemplate();
	}

	private void cargarTemplate() throws IOException {
		template = Util.readFile(Chispa.getConfiguracion().getNombreTemplate());
	}

	private void renderImg(StringBuilder sb, String imagen, String clase) {
		final String CARPETA_THUMBNAIL = "/thumbnail/";
		sb.append(String.format("<img class=\"%s\"", clase));
		sb.append(" ");
		sb.append(String.format("src=\"%s%s\"", CARPETA_THUMBNAIL, imagen));
		sb.append("/>");
	}

	private void renderLink(Archivo archivo, String cssClass, StringBuilder sb) {
		sb.append("<div class=\"");
		sb.append(cssClass);
		sb.append("\">");
		if (archivo != null) {
			sb.append("<a href=\"/pagina/");
			sb.append(archivo.getNombreBaseEncoded());
			sb.append("\">");
			sb.append(archivo.getNombreBase());
			sb.append("</a>");
		} else {
			sb.append("&nbsp;");
		}
		sb.append("</div>");
	}

	private String renderPagina(HashMap<String, Archivo> archivos) {
		StringBuilder sb = new StringBuilder();
		renderLink(archivos.get(Archivo.ANTERIOR), "izquierda", sb);
		renderThumbnail(archivos.get(Archivo.PRINCIPAL), sb);
		renderLink(archivos.get(Archivo.SIGUIENTE), "derecha", sb);
		return sb.toString();
	}

	String renderPagina(Archivo archivo, Directorio directorio) {
		final String IDENTIFICADOR = "<-- reemplazar cuerpo -->";
		HashMap<String, Archivo> archivos = new HashMap<>();
		archivos.put(Archivo.ANTERIOR, directorio.getArchivoAnterior(archivo));
		archivos.put(Archivo.PRINCIPAL, archivo);
		archivos.put(Archivo.SIGUIENTE, directorio.getArchivoSiguiente(archivo));
		return template.replaceFirst(IDENTIFICADOR, renderPagina(archivos));
	}

	private void renderThumbnail(Archivo archivo, StringBuilder sb) {
		sb.append("<div class=\"centro\">");
		sb.append("<div class=\"imagen\"");
		sb.append("<p>");
		renderImg(sb, archivo.getNombreBaseEncoded(), "thumbnail");
		sb.append("</p>");
		sb.append("<p class=\"nombre\">");
		sb.append(archivo.getNombreBase());
		sb.append("</p>");
		sb.append("</div>");
		sb.append("</div>");
	}

}