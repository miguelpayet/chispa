package chispa;

import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

class Servidor {

	private Directorio directorio;
	private Renderer renderer;


	Servidor() throws URISyntaxException, IOException {
		init();
	}

	private Archivo getArchivo(Request req) {
		String nombreEncoded = req.params(":nombre");
		String nombreArch;
		try {
			nombreArch = java.net.URLDecoder.decode(nombreEncoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			nombreArch = nombreEncoded;
		}
		return directorio.getArchivo(nombreArch);
	}

	String imagen(Request req, Response res) {
		Chispa.getLogger().info(req.params(":nombre"));
		res.redirect("hola");
		return "";
	}

	private void init() throws URISyntaxException, IOException {
		Chispa.getLogger().info("inicializando servidor");
		directorio = new Directorio(Chispa.getConfiguracion().getDirectorioOriginales());
		renderer = new Renderer();
	}

	String pagina(Request req, Response res) {
		Archivo arch = getArchivo(req);
		String resultado;
		if (arch != null) {
			resultado = renderer.renderPagina(arch, directorio);
		} else {
			res.redirect("/");
			resultado = "";
		}
		return resultado;
	}

	String primeraPagina() {
		Archivo archivoPrincipal = directorio.getArchivoPrimero();
		return renderer.renderPagina(archivoPrincipal, directorio);
	}

	String thumbnail(Request req, Response res) throws IOException {
		Archivo archOriginal = getArchivo(req);
		File fileThumb = archOriginal.getThumbnail();
		String fileName;
		try {
			fileName = URLEncoder.encode(fileThumb.getName(), "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			fileName = fileThumb.getName();
		}
		res.redirect("/images/" + fileName);
		return "";
	}

}
