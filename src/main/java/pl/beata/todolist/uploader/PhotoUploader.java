package pl.beata.todolist.uploader;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;

import pl.beata.todolist.streamResource.ByteArrayStreamResource;

/**
 * 
 * Component to upload photo.
 */
public class PhotoUploader extends Panel {

	private static final long serialVersionUID = 6665873457125702962L;
	private byte[] bytes;

	public PhotoUploader(Upload upload) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		upload.setReceiver(new Upload.Receiver() {

			private static final long serialVersionUID = 1099236069066034351L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				baos.reset();
				return baos;
			}
		});

		upload.addSucceededListener(new Upload.SucceededListener() {

			private static final long serialVersionUID = -3584913197238413126L;

			@Override
			public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {

				showPhoto(baos.toByteArray());
			}
		});
	}

	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * Displays user photo.
	 * 
	 * @param photoByte photo.
	 * 
	 */
	public void showPhoto(byte[] photoByte) {
		Image image = new Image();
		image.setHeight(9, Unit.CM);
		this.bytes = photoByte;
		if (photoByte != null) {
			image.setSource(new ByteArrayStreamResource(photoByte));
			image.setVisible(true);
		} else {
			image.setVisible(false);
		}
		setContent(image);
	}
}
