package pl.beata.todolist.streamResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource;

public class ByteArrayStreamResource extends StreamResource {

	private static final long serialVersionUID = 7844477183570270130L;

	public ByteArrayStreamResource(byte[] photoByteArray) {
		super(new StreamSource() {
			
			private static final long serialVersionUID = 2896638697885043638L;

			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(photoByteArray);
			}
		}, "");
	}

}
