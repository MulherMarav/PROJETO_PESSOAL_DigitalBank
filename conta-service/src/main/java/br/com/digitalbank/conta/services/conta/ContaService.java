package br.com.digitalbank.conta.services.conta;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.digitalbank.conta.controllers.ContaController;
import br.com.digitalbank.conta.data.vo.conta.v1.ContaCompletaVO;
import br.com.digitalbank.conta.data.vo.conta.v1.ContaVO;
import br.com.digitalbank.conta.data.vo.conta.v1.CorrenteVO;
import br.com.digitalbank.conta.data.vo.conta.v1.PoupancaVO;
import br.com.digitalbank.conta.exceptions.ResourceNotFoundException;
import br.com.digitalbank.conta.mapper.DozerMapper;
import br.com.digitalbank.conta.models.conta.ContaCompleta;
import br.com.digitalbank.conta.models.conta.Corrente;
import br.com.digitalbank.conta.models.conta.Poupanca;
import br.com.digitalbank.conta.repositories.CorrenteRepository;
import br.com.digitalbank.conta.repositories.PoupancaRepository;
import br.com.digitalbank.conta.services.conta.acoes.RealizaValidacaoConta;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContaService {

	private final CorrenteRepository correnteRepository;

	private final PoupancaRepository poupancaRepository;

	private final RealizaValidacaoConta realizaValidacaoConta;

	private Logger logger = LoggerFactory.getLogger(ContaService.class);

	private final PagedResourcesAssembler<ContaCompletaVO> assembler; 

	public ContaCompletaVO create(ContaVO vo) {

		realizaValidacaoConta.validaCriacaoDeContaNova(vo);

		logger.info("Persistindo e criando uma conta corrente.");

		var corrente = DozerMapper.parseObject(vo, Corrente.class);
		var correnteVO = DozerMapper.parseObject(correnteRepository.save(corrente), CorrenteVO.class);

		logger.info("Persistindo e criando uma conta poupança.");

		var poupanca = DozerMapper.parseObject(vo, Poupanca.class);
		var poupancaVO = DozerMapper.parseObject(poupancaRepository.save(poupanca), PoupancaVO.class);

		ContaCompletaVO contaCompletaVO = new ContaCompletaVO(correnteVO, poupancaVO);

		contaCompletaVO
				.add(linkTo(methodOn(ContaController.class).findByCpf(correnteVO.getCpfCliente())).withSelfRel());

		return contaCompletaVO;
	}

	public ContaCompletaVO findByCpf(String cpf) {
		
		var corrente = realizaValidacaoConta.validaEBuscaContaExistente(cpf);

		logger.info("Busca conta corrente e poupança pelo CPF cliente.");

		var correnteVO = DozerMapper.parseObject(corrente, CorrenteVO.class);

		var poupancaVO = DozerMapper.parseObject(poupancaRepository.findByCpfCliente(cpf), PoupancaVO.class);

		ContaCompletaVO contaCompletaVO = new ContaCompletaVO(correnteVO, poupancaVO);

		contaCompletaVO
				.add(linkTo(methodOn(ContaController.class).findByCpf(correnteVO.getCpfCliente())).withSelfRel());

		return contaCompletaVO;
	}

	public PagedModel<EntityModel<ContaCompletaVO>> findAll(Pageable pageable) {
		logger.info("Busca todas as contas cadastradas.");

		Page<ContaCompleta> contasPage = correnteRepository.findAllContas(pageable);

		var vosPages = contasPage
				.map(c -> new ContaCompletaVO(DozerMapper.parseObject(c.getCorrente(), CorrenteVO.class),
						DozerMapper.parseObject(c.getPoupanca(), PoupancaVO.class)));

		vosPages.map(c -> c
				.add(linkTo(methodOn(ContaController.class).findByCpf(c.getCorrente().getCpfCliente())).withSelfRel()));

		Link link = linkTo(
				methodOn(ContaController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
						.withSelfRel();

		return assembler.toModel(vosPages, link);
	}

}
