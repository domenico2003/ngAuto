package application.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "auto" })
public class ImmaginiAutomobili {
	@Id
	private UUID id;
	private String url;
	private String idEliminazione;

	@ManyToOne
	private Automobili auto;

	public ImmaginiAutomobili(String url, String idEliminazione, Automobili auto) {

		this.url = url;
		this.idEliminazione = idEliminazione;
		this.auto = auto;
	}

}
