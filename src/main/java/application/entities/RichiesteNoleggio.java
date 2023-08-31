package application.entities;

import java.time.LocalDate;
import java.util.UUID;

import application.entities.enums.StatoNoleggio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class RichiesteNoleggio {
	@Id
	private UUID id;

	@ManyToOne
	private Utente utenteRichiedente;

	private LocalDate daData;
	private LocalDate aData;

	@ManyToOne
	private Automobili autoRichiesta;

	@Enumerated(EnumType.STRING)
	private StatoNoleggio statoRichiesta = StatoNoleggio.in_attesa;

	@Column(length = 1500)
	private String noteRisposta;

	private String contattoConsigliato;

	public RichiesteNoleggio(Utente utenteRichiedente, LocalDate daData, LocalDate aData, Automobili autoRichiesta) {
		super();
		this.utenteRichiedente = utenteRichiedente;
		this.daData = daData;
		this.aData = aData;
		this.autoRichiesta = autoRichiesta;
	}

}
