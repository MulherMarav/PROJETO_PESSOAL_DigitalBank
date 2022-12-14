package br.com.digitalbank.conta.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digitalbank.conta.data.vo.conta.v1.PoupancaVO;
import br.com.digitalbank.conta.data.vo.movimentacao.v1.RendimentoVO;
import br.com.digitalbank.conta.service.movimentacao.RendimentoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/conta-service/rendimento/v1")
@RequiredArgsConstructor
public class RendimentoController {

	private final RendimentoService service;

	@PostMapping(value = "/{cpf}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PoupancaVO criaRendimento(@RequestBody @Valid RendimentoVO vo, @PathVariable(value = "cpf") String cpf) {
		return service.criaRendimento(vo, cpf);
	}

}
