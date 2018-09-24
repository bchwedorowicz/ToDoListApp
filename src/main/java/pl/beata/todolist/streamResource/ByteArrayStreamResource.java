package pl.beata.todolist.streamResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource;

public class ByteArrayStreamResource extends StreamResource {

	public ByteArrayStreamResource(byte[] photoByteArray) {
		super(new StreamSource() {
			
			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(photoByteArray);
			}
		}, "");
	}

}
