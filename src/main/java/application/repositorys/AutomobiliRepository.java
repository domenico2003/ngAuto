package application.repositorys;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.entities.Alimentazione;
import application.entities.Automobili;
import application.entities.Cambio;

public interface AutomobiliRepository extends JpaRepository<Automobili, UUID> {

	Page<Automobili> findByAlimentazione(Pageable pagina, Alimentazione alimentazione);

	Page<Automobili> findByCambio(Pageable pagina, Cambio cambio);

	Page<Automobili> findByCondizioneStartingWithIgnoreCase(Pageable pagina, String condizione);

	Page<Automobili> findByColoreStartingWithIgnoreCase(Pageable pagina, String colore);

	@Query("SELECT a FROM Automobili a WHERE " + "(a.prezzo BETWEEN :prezzoMin AND :prezzoMax) "
			+ "AND (:marcaTipo IS NULL OR UPPER(a.marca.tipo) = UPPER(:marcaTipo)) "
			+ "AND(:modelloTipo IS NULL OR UPPER(a.modello.tipo) = UPPER(:modelloTipo)) "
			+ "AND (:alimentazioneTipo IS NULL OR UPPER(a.alimentazione.tipo) = UPPER(:alimentazioneTipo) )"
			+ "AND (:cambioTipo IS NULL OR UPPER(a.cambio.tipo)= UPPER(:cambioTipo)) "
			+ "AND (:condizione IS NULL OR LOWER(a.condizione) = LOWER(:condizione)) "
			+ "AND (:colore IS NULL OR UPPER(a.colore) LIKE UPPER(:colore) || '%')")
	Page<Automobili> findRichiestaByCustomFilters(Pageable pagina, @Param("prezzoMin") long prezzoMin,
			@Param("prezzoMax") long prezzoMax, @Param("marcaTipo") String marcaTipo,
			@Param("modelloTipo") String modelloTipo, @Param("alimentazioneTipo") String alimentazioneTipo,
			@Param("cambioTipo") String cambioTipo, @Param("condizione") String condizione,
			@Param("colore") String colore);
}
