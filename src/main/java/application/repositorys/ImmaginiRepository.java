package application.repositorys;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Automobili;
import application.entities.ImmaginiAutomobili;

public interface ImmaginiRepository extends JpaRepository<ImmaginiAutomobili, UUID> {

	List<ImmaginiAutomobili> findByAuto(Automobili auto);
}
