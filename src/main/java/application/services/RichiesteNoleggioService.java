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
		Richiesta.setFinoA(body.getAData());
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

		return ricNolRepo.findByUtente(pagina, utenteRichiedente);
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
		return ricNolRepo.findByDaDataGreaterThanEqual(pagina, daData);
	};

	// filtro
	public Page<RichiesteNoleggio> findRichiestaByCustomFilters(int page, String ordinamento,
			String idUtenteRichiedente, String idAutoRichiesta, LocalDate daData, LocalDate aData) {
//		Utente utenteRichiedente = null;
//		Automobili autoRichiesta = null;

		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));

		// utente
		if (idUtenteRichiedente != null && idAutoRichiesta == null && daData == null && aData == null) {
			return this.findByUtenteRichiedente(page, ordinamento, idUtenteRichiedente);
		}

		// auto
		else if (idAutoRichiesta != null && idUtenteRichiedente == null && daData == null && aData == null) {
			return this.findByAutoRichiesta(page, ordinamento, idAutoRichiesta);
		}

		// da
		else if (daData != null && idUtenteRichiedente == null && idAutoRichiesta == null && aData == null) {
			return this.findByDaData(page, ordinamento, daData);
		}

		// a
		else if (daData == null && idUtenteRichiedente == null && idAutoRichiesta == null && aData != null) {
			return ricNolRepo.findAllByFinoALessThanEqual(pagina, aData);
		}

		// da - a
		else if (daData != null && aData != null && idUtenteRichiedente == null && idAutoRichiesta == null) {
			return this.findByDaDataBetween(page, ordinamento, daData, aData);
		}

		// utente - auto
		else if (idUtenteRichiedente != null && idAutoRichiesta != null && daData == null && aData == null) {

			return ricNolRepo.findByUtenteAndAutoRichiesta(pagina, us.findById(idUtenteRichiedente),
					auser.findById(idAutoRichiesta));
		}

		// utente - auto - da
		else if (idAutoRichiesta != null && idUtenteRichiedente != null && daData != null && aData == null) {

			return ricNolRepo.findAllByUtenteAndAutoRichiestaAndDaDataGreaterThanEqual(pagina,
					us.findById(idUtenteRichiedente), auser.findById(idAutoRichiesta), daData);
		}

		// utente - auto - a
		else if (idAutoRichiesta != null && idUtenteRichiedente != null && daData == null && aData != null) {

			return ricNolRepo.findAllByUtenteAndAutoRichiestaAndFinoALessThanEqual(pagina,
					us.findById(idUtenteRichiedente), auser.findById(idAutoRichiesta), aData);
		}

		// utente - auto - da - a
		else if (daData != null && idUtenteRichiedente != null && idAutoRichiesta != null && aData != null) {

			return ricNolRepo.findAllByUtenteAndAutoRichiestaAndDaDataGreaterThanEqualAndFinoALessThanEqual(pagina,
					us.findById(idUtenteRichiedente), auser.findById(idAutoRichiesta), daData, aData);
		}

		// tutti
		else if (daData == null && aData == null && idUtenteRichiedente == null && idAutoRichiesta == null) {
			return this.findAll(page, ordinamento);
		}

		// utente - da
		else if (idUtenteRichiedente != null && idAutoRichiesta == null && daData != null && aData == null) {

			return ricNolRepo.findAllByUtenteAndDaDataGreaterThanEqual(pagina, us.findById(idUtenteRichiedente),
					daData);
		}

		// utente - a
		else if (idUtenteRichiedente != null && idAutoRichiesta == null && daData == null && aData != null) {

			return ricNolRepo.findAllByUtenteAndFinoALessThanEqual(pagina, us.findById(idUtenteRichiedente), aData);
		}

		// utente - da - a
		else if (idAutoRichiesta == null && idUtenteRichiedente != null && daData != null && aData != null) {
			// da fare
			return ricNolRepo.findAllByUtenteAndDaDataGreaterThanEqualAndFinoALessThanEqual(pagina,
					us.findById(idUtenteRichiedente), daData, aData);
		}

		// auto - da
		else if (daData != null && idUtenteRichiedente == null && idAutoRichiesta != null && aData == null) {
			// da fare
			return ricNolRepo.findAllByAutoRichiestaAndDaDataGreaterThanEqual(pagina, auser.findById(idAutoRichiesta),
					daData);
		}

		// auto - a
		else if (daData == null && idUtenteRichiedente == null && idAutoRichiesta != null && aData != null) {
			// da fare
			return ricNolRepo.findAllByAutoRichiestaAndFinoALessThanEqual(pagina, auser.findById(idAutoRichiesta),
					aData);
		}

		// auto - da - a
		else if (daData != null && aData != null && idUtenteRichiedente == null && idAutoRichiesta != null) {
			// da fare
			return ricNolRepo.findAllByAutoRichiestaAndDaDataGreaterThanEqualAndFinoALessThanEqual(pagina,
					auser.findById(idAutoRichiesta), daData, aData);
		}

		else {
			throw new BadRequestException("La richiesta effettueta non si può eseguire");
		}

//		return ricNolRepo.findRichiestaByCustomFilters(pagina, utenteRichiedente, autoRichiesta, daData, aData);

	};
//		String daDataStringa = null;
//		String aDataStringa = null;
//		if (daData != null) {
//			daDataStringa = daData.toString();
//
//		}
//		if (aData != null) {
//			aDataStringa = aData.toString();
//		}
}
