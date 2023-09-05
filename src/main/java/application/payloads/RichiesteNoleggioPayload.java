package application.payloads;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RichiesteNoleggioPayload {

	private String idUtenteRichiedente;
	private LocalDate daData;
	private LocalDate aData;
	private String status;
	private String idAutoRichiesta;
	private String noteRisposta;
	private String contattoConsigliato;

}
