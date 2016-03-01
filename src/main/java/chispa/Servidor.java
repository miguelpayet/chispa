package chispa;

import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

class Servidor {

	private final String IDENTIFICADOR = "<-- reemplazar cuerpo -->";
	private Directorio directorio;
	String template;

	Servidor() throws URISyntaxException, IOException {
		init();
	}

	private void cargarTemplate() throws IOException {
		template = Util.readFile(Chispa.getConfiguracion().getNombreTemplate());
	}

	private Archivo getArchivo(Request req) {
		return directorio.getArchivo(req.params(":nombre"));
	}

	String imagen(Request req, Response res) {
		Chispa.getLogger().info(req.params(":nombre"));
		res.redirect("hola");
		return "";
	}

	private void init() throws URISyntaxException, IOException {
		Chispa.getLogger().info("inicializando servidor");
		directorio = new Directorio(Chispa.getConfiguracion().getDirectorioOriginales());
		cargarTemplate();
	}

	String pagina(Request req, Response res) {
		Archivo arch = getArchivo(req);
		if (arch != null) {
			return renderPagina(arch);
		} else {
			res.status(404);
			return "fail";
		}
	}

	String primeraPagina() {
		Archivo archivoPrincipal = directorio.getArchivoPrimero();
		return renderPagina(archivoPrincipal);
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

	private String renderPagina(Archivo archivo) {
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
		sb.append("<img ");
		sb.append("src=\"/thumbnail/");
		sb.append(archivo.getNombreBase());
		sb.append("\"/>");
		sb.append("</p>");
		sb.append("</div>");
	}

	String thumbnail(Request req, Response res) throws IOException {
		Archivo archOriginal = getArchivo(req);
		File fileThumb = archOriginal.getThumbnail();
		res.redirect("/images/" + fileThumb.getName());
		return "";
	}

}
