package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository repository;

    @Autowired
    public DespesaService (DespesaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Despesa save(Despesa despesa) {
        validAsUniqueInTheMonth(despesa);
        return repository.save(despesa);
    }

    public Despesa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Despesa> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Despesa put(Long id, Despesa despesa) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        despesa.setId(id);
        validAsUniqueInTheMonth(despesa);
        return repository.save(despesa);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }

    private void validAsUniqueInTheMonth(Despesa despesa) {
        LocalDate dataStart = despesa.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataEnd = despesa.getData().with(TemporalAdjusters.lastDayOfMonth());
        if (repository.isDuplicate(despesa.getDescricao(), dataStart, dataEnd, despesa.getId())) {
            throw new RegistroDuplicadoException();
        }
    }
}
