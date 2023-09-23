package application.repositorys;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Marca;
import application.entities.Modello;

public interface ModelloRepository extends JpaRepository<Modello, UUID> {
	List<Modello> findByMarca(Marca marca);

	Modello findByTipoIgnoreCase(String tipo);
}
