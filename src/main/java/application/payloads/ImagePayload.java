package application.payloads;

import lombok.Data;

@Data
public class ImagePayload {

	private String url;
	private String idEliminazione;

	public ImagePayload(String url, String idEliminazione) {

		this.url = url;
		this.idEliminazione = idEliminazione;
	}
}
