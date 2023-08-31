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
@JsonIgnoreProperties({ "id", "auto" })
public class ImmaginiAutomobili {
	@Id
	private UUID id;
	private String url;
	private String idEliminazione;

	@ManyToOne
	private Automobili auto;
}
