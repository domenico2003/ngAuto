package application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.Automobili;
import application.entities.ImmaginiAutomobili;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.ImagePayload;
import application.repositorys.AutomobiliRepository;
import application.repositorys.ImmaginiRepository;

@Service
public class ImgDbAutoService {

	@Autowired
	ImmaginiRepository imRepo;

	@Autowired
	AutomobiliRepository autoRepo;

	public ImmaginiAutomobili addImmagine(ImagePayload payload, Automobili auto) {
		ImmaginiAutomobili immagine = new ImmaginiAutomobili(payload.getUrl(), payload.getIdEliminazione(), auto);
		return imRepo.save(immagine);
	}

	public void deleteImg(UUID id) {
		ImmaginiAutomobili immagine = imRepo.findById(id)
				.orElseThrow(() -> new BadRequestException("immaggine non trovata"));
		imRepo.delete(immagine);
	}

	public List<ImmaginiAutomobili> immaginiPerAuto(String idAuto) {
		Automobili auto = autoRepo.findById(UUID.fromString(idAuto))
				.orElseThrow(() -> new BadRequestException("auto non trovata"));
		return imRepo.findByAuto(auto);
	}

	public ImmaginiAutomobili findById(String id) {
		return imRepo.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("immagine con id: " + id + " non trovata!"));
	}
}
