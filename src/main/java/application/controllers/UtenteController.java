package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import application.entities.Utente;
import application.payloads.CreateUtentePayload;
import application.services.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	UtenteService utenteService;

// crud per utente(senza create poichè sta in /auth)
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		utenteService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Utente findByid(@PathVariable String id) {
		return utenteService.findById(id);
	}

	@GetMapping("/email/{email}")
	@ResponseStatus(HttpStatus.OK)
	public Utente findByEmail(@PathVariable String email) {
		return utenteService.findByEmail(email);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Utente findByidAndUpdate(@PathVariable String id, @RequestBody CreateUtentePayload payload) {
		return utenteService.findByIdAndUpadate(id, payload);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String order) {
		return utenteService.findAll(page, order);
	}

	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public Utente me() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Utente utente = (Utente) authentication.getPrincipal();

		return utente;
	}
}
