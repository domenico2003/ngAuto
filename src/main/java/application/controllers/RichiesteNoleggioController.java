package application.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import application.entities.RichiesteNoleggio;
import application.exceptions.BadRequestException;
import application.payloads.RichiesteNoleggioPayload;
import application.services.RichiesteNoleggioService;

@RestController
@RequestMapping("/noleggio")
public class RichiesteNoleggioController {
	@Autowired
	RichiesteNoleggioService rns;

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public RichiesteNoleggio createRichiesta(@RequestBody RichiesteNoleggioPayload payload) {

		if (payload.getDaData().isAfter(LocalDate.now()) && payload.getAData().isAfter(payload.getDaData())) {
			return rns.create(payload);
		} else {
			throw new BadRequestException(
					"la data di partenza deve essere dopo quella odierna e quella di fine dopo quella di partenza ");
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		rns.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RichiesteNoleggio findByid(@PathVariable String id) {
		return rns.findById(id);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<RichiesteNoleggio> findRichiestaByCustomFilters(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String ordinamento,
			@RequestParam(required = false) String emailUtenteRichiedente,
			@RequestParam(required = false) String idAutoRichiesta, @RequestParam(required = false) LocalDate daData,
			@RequestParam(required = false) LocalDate aData) {

		return rns.findRichiestaByCustomFilters(page, ordinamento, emailUtenteRichiedente, idAutoRichiesta, daData,
				aData);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RichiesteNoleggio modificaRichiedente(@PathVariable String id,
			@RequestBody RichiesteNoleggioPayload payload) {

		if (payload.getDaData().isAfter(LocalDate.now()) && payload.getAData().isAfter(payload.getDaData())) {
			return rns.modificaRichiedente(id, payload);
		} else {
			throw new BadRequestException(
					"la data di partenza deve essere dopo quella odierna e quella di fine dopo quella di partenza ");
		}

	}

	@PutMapping("/rispondi/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RichiesteNoleggio Risposta(@PathVariable String id, @RequestBody RichiesteNoleggioPayload payload) {
		return rns.Risposta(id, payload);
	}

}
