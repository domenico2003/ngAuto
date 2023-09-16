package application.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import application.entities.Alimentazione;
import application.entities.Automobili;
import application.entities.Cambio;
import application.entities.ImmaginiAutomobili;
import application.exceptions.BadRequestException;
import application.payloads.AutoPayload;
import application.services.AutoService;

@RestController
@RequestMapping("/automobili")
public class AutomobiliController {
	@Autowired
	AutoService autoServ;

	// Automobili
	@PostMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Automobili create(@RequestBody AutoPayload payload) throws IOException {
		// nuova o usata o noleggio
		if (!payload.getCondizione().toLowerCase().equals("nuova".toLowerCase())
				& !payload.getCondizione().toLowerCase().equals("noleggio".toLowerCase())
				& !payload.getCondizione().toLowerCase().equals("usata".toLowerCase())) {
			throw new BadRequestException("la condizione può essere nuova o usata o noleggio");
		}
		payload.setCondizione(payload.getCondizione().toLowerCase());
		return autoServ.create(payload);

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		autoServ.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Automobili findByid(@PathVariable String id) {
		return autoServ.findById(id);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Automobili> findAutoByCustomFilters(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String ordinamento,
			@RequestParam(required = false) String tipoAlimentazione, @RequestParam(required = false) String tipoCambio,
			@RequestParam(required = false) String condizione, @RequestParam(required = false) String colore) {
		return autoServ.findAutoByCustomFilters(page, ordinamento, tipoAlimentazione, tipoCambio, condizione, colore);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Automobili findByIdAndUpdate(@PathVariable String id, @RequestBody AutoPayload payload) throws IOException {

		// nuova o usata o noleggio
		if ((payload.getCondizione().toLowerCase().equals("nuova".toLowerCase())
				| payload.getCondizione().toLowerCase().equals("noleggio".toLowerCase())
				| payload.getCondizione().toLowerCase().equals("usata".toLowerCase()))) {
			payload.setCondizione(payload.getCondizione().toLowerCase());
			return autoServ.findByIdAndUpdate(payload, id);
		} else {
			throw new BadRequestException("la condizione può essere nuova o usata o noleggio");
		}
	}

	// IMMAGINI
	@PutMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Automobili addCopertina(@RequestParam String idFoto, @RequestParam String idAuto) throws IOException {
		return autoServ.aggiungiCopertina(idFoto, idAuto);
	}

	@PostMapping("/img")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public ImmaginiAutomobili addImmagini(@RequestBody MultipartFile file, @RequestParam String autoId)
			throws IOException {
		return autoServ.salvaImmagini(file, autoId);
	}

	@DeleteMapping("/img/{idImg}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void findImgByIdAndDelete(@PathVariable String idImg) throws IOException {
		autoServ.deleteImg(idImg);
	}

	@GetMapping("/img/{idAuto}")
	@ResponseStatus(HttpStatus.OK)
	public List<ImmaginiAutomobili> immagginiPerAuto(@PathVariable String idAuto) throws IOException {
		return autoServ.immaginiPerAuto(idAuto);
	}

	// Cambio e Alimentazione

	@GetMapping("/cambi")
	@ResponseStatus(HttpStatus.OK)
	public List<Cambio> cambi() {

		return autoServ.findAllCambio();
	}

	@GetMapping("/alimentazioni")
	@ResponseStatus(HttpStatus.OK)
	public List<Alimentazione> alimentazioni() {
		return autoServ.findAllAlimentazioni();
	}
}
