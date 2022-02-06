package com.caiolima.bcontrol;

import com.caiolima.bcontrol.controller.DespesaController;
import com.caiolima.bcontrol.controller.ReceitaController;
import com.caiolima.bcontrol.controller.ResumoController;
import com.caiolima.bcontrol.repository.DespesaRepository;
import com.caiolima.bcontrol.repository.ReceitaRepository;
import com.caiolima.bcontrol.service.DespesaService;
import com.caiolima.bcontrol.service.ReceitaService;
import com.caiolima.bcontrol.service.ResumoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BcontrolApplicationTests {

	@Autowired
	private DespesaService despesaService;
	@Autowired
	private DespesaController despesaController;
	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private ReceitaService receitaService;
	@Autowired
	private ReceitaController receitaController;
	@Autowired
	private ReceitaRepository receitaRepository;

	@Autowired
	private ResumoService resumoService;
	@Autowired
	private ResumoController resumoController;

	@Test
	@DisplayName("Testa se a aplicação está subindo corretamente")
	void contextLoads() {

		assertThat(this).hasNoNullFieldsOrProperties();

	}

}
