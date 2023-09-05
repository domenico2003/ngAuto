package application.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Integer> {
	Optional<Cambio> findByTipoIgnoreCase(String tipo);
}
