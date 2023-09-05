package application.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import application.entities.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "richiesteNoleggio" })
public class Automobili {
	@Id
	private UUID id;

	@ManyToOne
	private Alimentazione alimentazione;

	@ManyToOne
	private Cambio cambio;

	@OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ImmaginiAutomobili> foto;

	@Enumerated(EnumType.STRING)
	private Status stato;
	private String condizione;// nuova o usata o noleggio
	@Column(length = 500)
	private String colore;
	private long km;
	@Column(length = 500)
	private String carrozzeria;
	private long cilindrata;
	private int potenza_cv;

	@OneToMany(mappedBy = "autoRichiesta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RichiesteNoleggio> richiesteNoleggio;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private ImmaginiAutomobili copertina;
	@Column(length = 1500)
	private String note;

	public Automobili(Alimentazione alimentazione, Cambio cambio, Status stato, String condizione, String colore,
			long km, String carrozzeria, long cilindrata, int potenza_cv, ImmaginiAutomobili copertina, String note) {

		this.alimentazione = alimentazione;
		this.cambio = cambio;

		this.stato = stato;
		this.condizione = condizione;
		this.colore = colore;
		this.km = km;
		this.carrozzeria = carrozzeria;
		this.cilindrata = cilindrata;
		this.potenza_cv = potenza_cv;
		this.copertina = copertina;
		this.note = note;
	}

}
