package application.payloads;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AutoPayload {
	private String TipoAlimentazione;
	private String TipoCambio;
	private String stato;
	private String condizione;
	private String colore;
	private long km;
	private String carrozzeria;
	private long cilindrata;
	private int potenza_cv;
	private MultipartFile copertina;;
	private String note;
}
