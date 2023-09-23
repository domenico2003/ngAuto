package application.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Marca;

public interface MarcaRepository extends JpaRepository<Marca, UUID> {
	Marca findByTipoIgnoreCase(String tipo);
}
