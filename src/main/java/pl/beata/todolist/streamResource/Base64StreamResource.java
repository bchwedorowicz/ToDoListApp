package pl.beata.todolist.streamResource;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.vaadin.server.ExternalResource;

public class Base64StreamResource extends ExternalResource {

	private static final long serialVersionUID = -6424280306708306932L;

	public Base64StreamResource(byte[] photo, int width, int height) {
		super("data:image/png;base64," + Base64.getEncoder().encodeToString(resizeImage(photo, width, height)));
	}

	private static byte[] resizeImage(byte[] fileData, int width, int height) {
		try (ByteArrayInputStream in = new ByteArrayInputStream(fileData);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			BufferedImage img = ImageIO.read(in);
			Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

			ImageIO.write(imageBuff, "jpg", buffer);

			return buffer.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
