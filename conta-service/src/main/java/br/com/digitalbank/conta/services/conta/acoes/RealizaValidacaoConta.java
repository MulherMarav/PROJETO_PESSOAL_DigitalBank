package br.com.digitalbank.conta.services.conta.acoes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.digitalbank.conta.data.vo.conta.v1.ContaVO;
import br.com.digitalbank.conta.models.conta.ContaCompleta;
import br.com.digitalbank.conta.services.conta.validacoes.ValidacaoConta;
import br.com.digitalbank.conta.services.conta.validacoes.ValidacaoContaExiste;

@Component
public class RealizaValidacaoConta {

	@Autowired
	private ValidacaoContaExiste validacaoContaExiste;

	@Autowired
	private List<ValidacaoConta> validacoesConta;
	

	public void validaCriacaoDeContaNova(ContaVO conta) {
		this.validacoesConta.forEach(v -> v.valida(conta));
	}

	public ContaCompleta validaEBuscaContaExistente(String cpf) {
		return this.validacaoContaExiste.valida(cpf);
	}
}
