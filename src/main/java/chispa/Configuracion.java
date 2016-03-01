package chispa;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.XMLBuilderParameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

class Configuracion {

	private XMLConfiguration config;

	@SuppressWarnings("unused")
	static void printClassPath() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader) cl).getURLs();
		for (URL url : urls) {
			System.out.println(url.getFile());
		}
		System.out.println("---");
		System.out.println(System.getProperty("java.class.path"));
	}

	Configuracion() throws ConfigurationException {
		//printClassPath();
		init();
	}

	String getDirectorioOriginales() {
		return config.getString("directorios.imagenes");
	}

	String getDirectorioPublico() {
		return config.getString("directorios.publico");
	}

	String getDirectorioRaiz() throws URISyntaxException {
		String d = config.getString("directorios.raiz");
		Chispa.getLogger().info("directorio raíz es " + d);
		return d;
	}

	String getNombreTemplate() {
		return config.getString("template.nombre");
	}

	private void init() throws ConfigurationException {
		Parameters params = new Parameters();
		XMLBuilderParameters xbp = params.xml();
		xbp.setValidating(true);
		xbp.setFileName("configuracion.xml");
		xbp.setThrowExceptionOnMissing(true);
		FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<>(XMLConfiguration.class);
		builder.configure(xbp);
		config = builder.getConfiguration();
	}

}
