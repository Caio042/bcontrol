package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.exception.UnauthorizedException;
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
        if(isDuplicateInMonth(despesa)) {
            throw new RegistroDuplicadoException();
        }
        return repository.save(despesa);
    }

    public Despesa findById(Long id, String username) {

        Despesa despesa = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        if (!username.equals(despesa.getUsuario().getUsername())) {
            throw new UnauthorizedException();
        }
        return despesa;
    }

    public List<Despesa> listAll(String descricao) {
        return repository.findAllByDescricao(descricao);
    }

    @Transactional
    public Despesa update(Despesa despesa, String username) {
        if (isDuplicateInMonth(despesa)) {
            throw new RegistroDuplicadoException();
        }
        Despesa despesaDB = findById(despesa.getId(), username);
        despesa.setUsuario(despesaDB.getUsuario());
        return repository.save(despesa);
    }

    @Transactional
    public void deleteById(Long id, String username) {
        findById(id, username);
        repository.deleteById(id);
    }

    private boolean isDuplicateInMonth(Despesa despesa) {
        LocalDate dataStart = despesa.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataEnd = despesa.getData().with(TemporalAdjusters.lastDayOfMonth());
        return repository.isDuplicate(despesa.getDescricao(), dataStart, dataEnd, despesa.getId());
    }

    public List<Despesa> findAllByDate(Integer ano, Integer mes) {
        return repository.findAllByDate(ano, mes);
    }
}
