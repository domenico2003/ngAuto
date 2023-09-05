package application.repositorys;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Alimentazione;
import application.entities.Automobili;
import application.entities.Cambio;

public interface AutomobiliRepository extends JpaRepository<Automobili, UUID> {

	Page<Automobili> findByAlimentazione(Pageable pagina, Alimentazione alimentazione);

	Page<Automobili> findByCambio(Pageable pagina, Cambio cambio);

	Page<Automobili> findByCondizioneStartingWithIgnoreCase(Pageable pagina, String condizione);

	Page<Automobili> findByColoreStartingWithIgnoreCase(Pageable pagina, String colore);

}
