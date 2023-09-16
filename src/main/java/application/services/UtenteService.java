package application.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Utente;
import application.entities.enums.UtenteRuoli;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.CreateUtentePayload;
import application.repositorys.UtenteRepository;

@Service
public class UtenteService {
	@Autowired
	UtenteRepository utenteRepo;

	// creo l' utente
	public Utente create(CreateUtentePayload payload) {

		utenteRepo.findByEmailIgnoreCase(payload.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " già utilizzata!");
		});

		Utente u = new Utente(payload.getEmail(), payload.getPassword(), payload.getNome(), payload.getCognome(),
				payload.getUsername(), payload.getTelefono());

		return utenteRepo.save(u);
	}

	public Page<Utente> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return utenteRepo.findAll(pagina);
	}

	public Utente findByIdAndUpadate(String id, CreateUtentePayload body) {
		Utente u = this.findById(id);
		if (!u.getEmail().equals(body.getEmail()))
			utenteRepo.findByEmailIgnoreCase(body.getEmail()).ifPresent(user -> {
				throw new BadRequestException("Email " + user.getEmail() + " già utilizzata!");
			});
		u.setUsername(body.getUsername());
		u.setEmail(body.getEmail());
		u.setNome(body.getNome());
		u.setCognome(body.getCognome());
		u.setTelefono(body.getTelefono());
		return utenteRepo.save(u);

	}

	public void findByIdAndDelete(String id) {
		Utente u = this.findById(id);

		utenteRepo.delete(u);

	}

	// cerco utente con id
	public Utente findById(String id) {
		return utenteRepo.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Utente con id: " + id + " non trovato!"));
	}

	public Utente findByEmail(String email) {
		return utenteRepo.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new NotFoundException("Utente con " + email + " non trovato!"));
	}

	public void aggiungiAdmin(String email) {
		Utente found = this.findByEmail(email);
		found.setRuolo(UtenteRuoli.ADMIN);
		utenteRepo.save(found);
	}
}
