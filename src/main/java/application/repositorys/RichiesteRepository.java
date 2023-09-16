package application.repositorys;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.entities.Automobili;
import application.entities.RichiesteNoleggio;
import application.entities.Utente;

public interface RichiesteRepository extends JpaRepository<RichiesteNoleggio, UUID> {
//user
	Page<RichiesteNoleggio> findByUtente(Pageable pagina, Utente utente);

	Page<RichiesteNoleggio> findByUtenteAndAutoRichiesta(Pageable pagina, Utente utente, Automobili autoRichiesta);

	Page<RichiesteNoleggio> findByAutoRichiesta(Pageable pagina, Automobili autoRichiesta);

	Page<RichiesteNoleggio> findByDaDataBetween(Pageable pagina, LocalDate daData, LocalDate aData);

	Page<RichiesteNoleggio> findByDaDataGreaterThanEqual(Pageable pagina, LocalDate daData);

	Page<RichiesteNoleggio> findAllByFinoALessThanEqual(Pageable pageable, LocalDate finoA);

	@Query("SELECT r FROM RichiesteNoleggio r WHERE " + "(:utente IS NULL OR r.utente = :utente) "
			+ "AND (:autoRichiesta IS NULL OR r.autoRichiesta = :autoRichiesta) "
			+ "AND (:daData IS NULL OR r.daData >= :daData ) " + "AND (:finoA IS NULL OR r.finoA <= :finoA )")
	Page<RichiesteNoleggio> findRichiestaByCustomFilters(Pageable pagina, @Param("utente") Utente utente,
			@Param("autoRichiesta") Automobili autoRichiesta, @Param("daData") LocalDate daData,
			@Param("finoA") LocalDate finoA);

	Page<RichiesteNoleggio> findAllByUtenteAndAutoRichiestaAndDaDataGreaterThanEqualAndFinoALessThanEqual(
			Pageable pageable, Utente utente, Automobili autoRichiesta, LocalDate daData, LocalDate finoA);

	Page<RichiesteNoleggio> findAllByAutoRichiestaAndFinoALessThanEqual(Pageable pageable, Automobili autoRichiesta,
			LocalDate finoA);

	Page<RichiesteNoleggio> findAllByUtenteAndAutoRichiestaAndFinoALessThanEqual(Pageable pageable, Utente utente,
			Automobili autoRichiesta, LocalDate finoA);

	Page<RichiesteNoleggio> findAllByUtenteAndFinoALessThanEqual(Pageable pageable, Utente utente, LocalDate finoA);

	Page<RichiesteNoleggio> findAllByAutoRichiestaAndDaDataGreaterThanEqual(Pageable pageable, Automobili autoRichiesta,
			LocalDate daData);

	Page<RichiesteNoleggio> findAllByAutoRichiestaAndDaDataGreaterThanEqualAndFinoALessThanEqual(Pageable pageable,
			Automobili autoRichiesta, LocalDate daData, LocalDate finoA);

	Page<RichiesteNoleggio> findAllByUtenteAndDaDataGreaterThanEqualAndFinoALessThanEqual(Pageable pageable,
			Utente utente, LocalDate daData, LocalDate finoA);

	Page<RichiesteNoleggio> findAllByUtenteAndAutoRichiestaAndDaDataGreaterThanEqual(Pageable pageable, Utente utente,
			Automobili autoRichiesta, LocalDate daData);

	Page<RichiesteNoleggio> findAllByUtenteAndDaDataGreaterThanEqual(Pageable pageable, Utente utente,
			LocalDate daData);

}
