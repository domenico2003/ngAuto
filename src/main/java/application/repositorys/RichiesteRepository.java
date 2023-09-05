package application.repositorys;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Automobili;
import application.entities.RichiesteNoleggio;
import application.entities.Utente;

public interface RichiesteRepository extends JpaRepository<RichiesteNoleggio, UUID> {

	Page<RichiesteNoleggio> findByUtenteRichiedente(Pageable pagina, Utente utenteRichiedente);

	Page<RichiesteNoleggio> findByAutoRichiesta(Pageable pagina, Automobili autoRichiesta);

	Page<RichiesteNoleggio> findByDaDataBetween(Pageable pagina, LocalDate daData, LocalDate aData);

	Page<RichiesteNoleggio> findByDaData(Pageable pagina, LocalDate daData);
}
