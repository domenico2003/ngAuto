package application.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Automobili;
import application.entities.RichiesteNoleggio;
import application.entities.Utente;
import application.entities.enums.StatoNoleggio;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.RichiesteNoleggioPayload;
import application.repositorys.RichiesteRepository;

@Service
public class RichiesteNoleggioService {

	@Autowired
	RichiesteRepository ricNolRepo;

	@Autowired
	UtenteService us;

	@Autowired
	AutoService auser;

	public RichiesteNoleggio create(RichiesteNoleggioPayload payload) {

		RichiesteNoleggio richiesta = new RichiesteNoleggio(us.findById(payload.getIdUtenteRichiedente()),
				payload.getDaData(), payload.getAData(), auser.findById(payload.getIdAutoRichiesta()));

		return ricNolRepo.save(richiesta);
	}

	public Page<RichiesteNoleggio> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return ricNolRepo.findAll(pagina);
	}

	public RichiesteNoleggio Risposta(String id, RichiesteNoleggioPayload body) {
		RichiesteNoleggio Richiesta = this.findById(id);
		if (!body.getStatus().contains("in_attesa") & !body.getStatus().contains("rifiutata")
				& !body.getStatus().contains("approvata") & !body.getStatus().contains("riproposta")) {
			throw new BadRequestException("stato inserito non corretto");
		}
		Richiesta.setStatoRichiesta(StatoNoleggio.valueOf(body.getStatus().toLowerCase()));
		Richiesta.setNoteRisposta(body.getNoteRisposta());
		Richiesta.setContattoConsigliato(body.getContattoConsigliato());
		return ricNolRepo.save(Richiesta);

	}

	public RichiesteNoleggio modificaRichiedente(String id, RichiesteNoleggioPayload body) {
		RichiesteNoleggio Richiesta = this.findById(id);

		Richiesta.setDaData(body.getDaData());
		Richiesta.setAData(body.getAData());
		Richiesta.setAutoRichiesta(auser.findById(body.getIdAutoRichiesta()));

		return ricNolRepo.save(Richiesta);

	}

	public void findByIdAndDelete(String id) {
		RichiesteNoleggio richiesta = this.findById(id);

		ricNolRepo.delete(richiesta);

	}

	public RichiesteNoleggio findById(String id) {
		return ricNolRepo.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Richiesta con id: " + id + " non trovata!"));
	}

//	metodi custom

	public Page<RichiesteNoleggio> findByUtenteRichiedente(int page, String ordinamento, String idUtenteRichiedente) {

		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));

		Utente utenteRichiedente = us.findById(idUtenteRichiedente);

		return ricNolRepo.findByUtenteRichiedente(pagina, utenteRichiedente);
	};

	public Page<RichiesteNoleggio> findByAutoRichiesta(int page, String ordinamento, String idAutoRichiesta) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		Automobili autoRichiesta = auser.findById(idAutoRichiesta);
		return ricNolRepo.findByAutoRichiesta(pagina, autoRichiesta);
	};

	public Page<RichiesteNoleggio> findByDaDataBetween(int page, String ordinamento, LocalDate daData,
			LocalDate aData) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));

		return ricNolRepo.findByDaDataBetween(pagina, daData, aData);
	};

	public Page<RichiesteNoleggio> findByDaData(int page, String ordinamento, LocalDate daData) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return ricNolRepo.findByDaData(pagina, daData);
	};

}
