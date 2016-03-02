package chispa;

import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

class Servidor {

	private Directorio directorio;
	private Renderer renderer;


	Servidor() throws URISyntaxException, IOException {
		init();
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
		renderer = new Renderer();
	}

	String pagina(Request req, Response res) {
		Archivo arch = getArchivo(req);
		if (arch != null) {
			return renderer.renderPagina(arch, directorio);
		} else {
			res.status(404);
			return "fail";
		}
	}

	String primeraPagina() {
		Archivo archivoPrincipal = directorio.getArchivoPrimero();
		return renderer.renderPagina(archivoPrincipal, directorio);
	}

	String thumbnail(Request req, Response res) throws IOException {
		Archivo archOriginal = getArchivo(req);
		File fileThumb = archOriginal.getThumbnail();
		res.redirect("/images/" + fileThumb.getName());
		return "";
	}

}
