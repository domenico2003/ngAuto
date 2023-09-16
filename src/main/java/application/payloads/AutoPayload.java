package application.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AutoPayload {
	private String tipoAlimentazione;
	private String tipoCambio;
	private String stato;
	private String condizione;
	private String colore;
	private long km;
	private String carrozzeria;
	private long cilindrata;
	private int potenza_cv;

	private String note;
}
