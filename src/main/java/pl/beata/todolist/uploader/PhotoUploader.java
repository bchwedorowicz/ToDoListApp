package pl.beata.todolist.uploader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;

import pl.beata.todolist.streamResource.ByteArrayStreamResource;

public class PhotoUploader extends Panel {

	private Upload upload;
	private byte[] bytes;

	public PhotoUploader(Upload upload) {
		this.upload = upload;

		// Create upload stream
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Stream to write to

		upload.setReceiver(new Upload.Receiver() {
			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				baos.reset();
				return baos; // Return the output stream to write to
			}
		});

		upload.addSucceededListener(new Upload.SucceededListener() {
			@Override
			public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {

				showPhoto(baos.toByteArray());
			}
		});
	}

	public byte[] getBytes() {
		return bytes;
	}

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
