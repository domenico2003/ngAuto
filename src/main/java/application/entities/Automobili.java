package application.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import application.entities.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({})
public class Automobili {
	@Id
	private UUID id;

	@ManyToOne
	private Alimentazione alimentazione;

	@ManyToOne
	private Cambio cambio;

	@OneToMany
	private List<ImmaginiAutomobili> foto;

	@Enumerated(EnumType.STRING)
	private Status stato;
	private String condizione;// nuova o usata
	@Column(length = 500)
	private String colore;
	private long km;
	@Column(length = 500)
	private String carrozzeria;
	private long cilindrata;
	private int potenza_cv;

	@Column(length = 1500)
	private String note;
}
