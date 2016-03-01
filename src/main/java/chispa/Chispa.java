package chispa;

import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;

public class Chispa {

	private static Configuracion config;
	private static Logger logger = Logger.getLogger(Chispa.class.getName());
	private static Servidor servidor;

	static {
		try {
			config = new Configuracion();
		} catch (ConfigurationException e) {
			System.out.println("Error al crear configuración. " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	static Configuracion getConfiguracion() {
		return config;
	}

	static Logger getLogger() {
		return logger;
	}

	private static void init() throws ConfigurationException, URISyntaxException, IOException {
		servidor = new Servidor();
		externalStaticFileLocation(Chispa.getConfiguracion().getDirectorioRaiz());
	}

	public static void main(String[] args) throws URISyntaxException {
		try {
			init();
			get("/", (req, res) -> servidor.primeraPagina());
			get("/pagina/:nombre", (req, res) -> servidor.pagina(req, res));
			get("/thumbnail/:nombre", (req, res) -> servidor.thumbnail(req, res));
			get("/imagen/:nombre", (req, res) -> servidor.imagen(req, res));
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

}
