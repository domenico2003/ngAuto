package application.entities;

import java.time.LocalDate;
import java.util.UUID;

import application.entities.enums.StatoNoleggio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class RichiesteNoleggio {
	@Id
	@GeneratedValue
	private UUID id;

	@ManyToOne
	private Utente utente;

	private LocalDate daData;
	private LocalDate finoA;

	@ManyToOne
	private Automobili autoRichiesta;

	@Enumerated(EnumType.STRING)
	private StatoNoleggio statoRichiesta = StatoNoleggio.in_attesa;

	@Column(length = 1500)
	private String noteRisposta;

	private String contattoConsigliato;

	public RichiesteNoleggio(Utente utenteRichiedente, LocalDate daData, LocalDate aData, Automobili autoRichiesta) {
		super();
		this.utente = utenteRichiedente;
		this.daData = daData;
		this.finoA = aData;
		this.autoRichiesta = autoRichiesta;
	}

}
