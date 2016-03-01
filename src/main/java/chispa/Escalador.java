package chispa;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Escalador {

	private static final int THUMBNAIL_HORIZONTAL = 500;

	static File escalar(Archivo archivo) throws IOException {
		String dirImagenes = Chispa.getConfiguracion().getDirectorioPublico();
		String nombreThumbnail;
		nombreThumbnail = dirImagenes + "/" + archivo.getNombreThumbnail(THUMBNAIL_HORIZONTAL);
		File f = new File(nombreThumbnail);
		if (!f.exists() || f.isDirectory()) {
			BufferedImage srcImage = ImageIO.read(new File(archivo.getNombreCompleto()));
			BufferedImage scaledImage = Scalr.resize(srcImage, THUMBNAIL_HORIZONTAL);
			ImageIO.write(scaledImage, Util.extension(nombreThumbnail), f);
		}
		return f;
	}
}
