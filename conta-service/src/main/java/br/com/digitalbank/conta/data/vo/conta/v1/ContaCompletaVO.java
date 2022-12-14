package br.com.digitalbank.conta.data.vo.conta.v1;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ContaCompletaVO extends RepresentationModel<ContaCompletaVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CorrenteVO corrente;

	private PoupancaVO poupanca;
}
