package application.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import application.entities.Alimentazione;
import application.entities.Automobili;
import application.entities.Cambio;
import application.entities.ImmaginiAutomobili;
import application.entities.enums.Status;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.AutoPayload;
import application.payloads.ImagePayload;
import application.repositorys.AlimentazioneRepository;
import application.repositorys.AutomobiliRepository;
import application.repositorys.CambioRepository;

@Service
public class AutoService {

	@Autowired
	AutomobiliRepository autoRepo;

	@Autowired
	CambioRepository cambioRepo;

	@Autowired
	AlimentazioneRepository aliRepo;

	@Autowired
	ImageService imageService;

	@Autowired
	ImgDbAutoService imgDBService;

	// metodi cambio e alimentazione
	public Cambio findCambioByNome(String tipo) {
		return cambioRepo.findByTipoIgnoreCase(tipo)
				.orElseThrow(() -> new NotFoundException("cambio " + tipo + " non trovato!"));
	}

	public Alimentazione findAlimentazioneByNome(String tipo) {
		return aliRepo.findByTipoIgnoreCase(tipo)
				.orElseThrow(() -> new NotFoundException("alimentazione " + tipo + " non trovata!"));
	}

	public List<Alimentazione> findAllAlimentazioni() {

		return aliRepo.findAll();
	}

	public List<Cambio> findAllCambio() {

		return cambioRepo.findAll();
	}

	// metodi immagini

	public void deleteImg(String id) {

		imgDBService.deleteImg(id);
	}

	public List<ImmaginiAutomobili> immaginiPerAuto(String idAuto) {

		return imgDBService.immaginiPerAuto(idAuto);
	}

	public ImmaginiAutomobili salvaImmagini(MultipartFile file, String autoId) throws IOException {
		ImagePayload immaggineSalvata = imageService.uploadImage(file);
		return imgDBService.addImmagine(immaggineSalvata, this.findById(autoId));
	}

	public Automobili aggiungiCopertina(String idImagine, String autoId) throws IOException {
		ImmaginiAutomobili copertina = imgDBService.findById(idImagine);
		Automobili auto = this.findById(autoId);
		auto.setCopertina(copertina);
		return autoRepo.save(auto);
	}

	public void deleteImgAuto(String idAuto) {
		List<ImmaginiAutomobili> immagini = imgDBService.immaginiPerAuto(idAuto);

		immagini.forEach(im -> {
			imageService.deleteImage(im.getIdEliminazione());
		});
	}

	// metodi automobili
	public Automobili findById(String id) {
		return autoRepo.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("auto con id: " + id + " non trovata!"));
	}

	public Automobili create(AutoPayload body) throws IOException {

		Automobili autoDaSalvare = new Automobili();

		autoDaSalvare.setAlimentazione(this.findAlimentazioneByNome(body.getTipoAlimentazione()));
		autoDaSalvare.setCambio(this.findCambioByNome(body.getTipoCambio()));
		autoDaSalvare.setCondizione(body.getCondizione());
		autoDaSalvare.setColore(body.getColore());
		autoDaSalvare.setKm(body.getKm());
		autoDaSalvare.setCarrozzeria(body.getCarrozzeria());
		autoDaSalvare.setCilindrata(body.getCilindrata());
		autoDaSalvare.setPotenza_cv(body.getPotenza_cv());
		autoDaSalvare.setNote(body.getNote());
		if (!body.getStato().contains("in_vendita") & !body.getStato().contains("venduta")
				& !body.getStato().contains("non_disponibile") & !body.getStato().contains("da_noleggiare")
				& !body.getStato().contains("noleggiata")) {
			throw new BadRequestException("stato inserito non corretto");
		}

		autoDaSalvare.setStato(Status.valueOf(body.getStato().toLowerCase()));

		return autoRepo.save(autoDaSalvare);
	}

	public Automobili findByIdAndUpdate(AutoPayload body, String autoId) throws IOException {

		Automobili autoDaSalvare = this.findById(autoId);

		autoDaSalvare.setAlimentazione(this.findAlimentazioneByNome(body.getTipoAlimentazione()));
		autoDaSalvare.setCambio(this.findCambioByNome(body.getTipoCambio()));
		autoDaSalvare.setCondizione(body.getCondizione());
		autoDaSalvare.setColore(body.getColore());
		autoDaSalvare.setKm(body.getKm());
		autoDaSalvare.setCarrozzeria(body.getCarrozzeria());
		autoDaSalvare.setCilindrata(body.getCilindrata());
		autoDaSalvare.setPotenza_cv(body.getPotenza_cv());
		autoDaSalvare.setNote(body.getNote());
		if (!body.getStato().contains("in_vendita") & !body.getStato().contains("venduta")
				& !body.getStato().contains("non_disponibile") & !body.getStato().contains("da_noleggiare")
				& !body.getStato().contains("noleggiata")) {
			throw new BadRequestException("stato inserito non corretto");
		}

		autoDaSalvare.setStato(Status.valueOf(body.getStato().toLowerCase()));

		return autoRepo.save(autoDaSalvare);
	}

	public Page<Automobili> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return autoRepo.findAll(pagina);
	}

	public void findByIdAndDelete(String id) {
		this.deleteImgAuto(id);
		Automobili auto = this.findById(id);

		autoRepo.delete(auto);

	}

	// metodi auto custom
	public Page<Automobili> findByAlimentazione(int page, String ordinamento, String tipoAlimentazione) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		Alimentazione ali = this.findAlimentazioneByNome(tipoAlimentazione);
		return autoRepo.findByAlimentazione(pagina, ali);

	};

	public Page<Automobili> findByCambio(int page, String ordinamento, String cambioTipo) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));

		Cambio cam = this.findCambioByNome(cambioTipo);
		return autoRepo.findByCambio(pagina, cam);
	};

	public Page<Automobili> findByCondizioneStartingWithIgnoreCase(int page, String ordinamento, String condizione) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return autoRepo.findByCondizioneStartingWithIgnoreCase(pagina, condizione);

	};

	public Page<Automobili> findByColoreStartingWithIgnoreCase(int page, String ordinamento, String colore) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));

		return autoRepo.findByColoreStartingWithIgnoreCase(pagina, colore);
	};

	public Page<Automobili> findAutoByCustomFilters(int page, String ordinamento, String tipoAlimentazione,
			String tipoCambio, String condizione, String colore) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));

		if (tipoCambio != null & tipoAlimentazione != null) {
			Cambio CambioTrovato = this.findCambioByNome(tipoCambio);
			Alimentazione alimentazioneRichiesta = this.findAlimentazioneByNome(tipoAlimentazione);
			return autoRepo.findRichiestaByCustomFilters(pagina, alimentazioneRichiesta, CambioTrovato, condizione,
					colore);
		} else if (tipoCambio == null & tipoAlimentazione != null) {

			Alimentazione alimentazioneRichiesta = this.findAlimentazioneByNome(tipoAlimentazione);
			return autoRepo.findRichiestaByCustomFilters(pagina, alimentazioneRichiesta, null, condizione, colore);
		} else if (tipoCambio != null & tipoAlimentazione == null) {

			Cambio CambioTrovato = this.findCambioByNome(tipoCambio);
			return autoRepo.findRichiestaByCustomFilters(pagina, null, CambioTrovato, condizione, colore);
		} else {
			return autoRepo.findRichiestaByCustomFilters(pagina, null, null, condizione, colore);
		}
	};
}
