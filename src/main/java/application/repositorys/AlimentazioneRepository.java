package application.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Alimentazione;

public interface AlimentazioneRepository extends JpaRepository<Alimentazione, Integer> {
	Optional<Alimentazione> findByTipoIgnoreCase(String tipo);
}
