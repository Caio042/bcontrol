package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository repository;

    @Autowired
    public ReceitaService(ReceitaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Receita save(Receita receita) {
        if (isDuplicateInMonth(receita)) {
            throw new RegistroDuplicadoException();
        }

        return repository.save(receita);
    }

    public List<Receita> listAll(String descricao) {
        return repository.findAllByDescricao(descricao);
    }

    public Receita findById(Long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Receita put(Receita receita) {
        if (!repository.existsById(receita.getId())) {
            throw new NotFoundException();
        }
        if (isDuplicateInMonth(receita)) {
            throw new RegistroDuplicadoException();
        }
        return repository.save(receita);
    }

    private boolean isDuplicateInMonth(Receita receita) {
        LocalDate dataStart = receita.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataEnd = receita.getData().with(TemporalAdjusters.lastDayOfMonth());
        return repository.isDuplicate(receita.getDescricao(), dataStart, dataEnd, receita.getId());
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }

    public List<Receita> findAllByDate(Integer ano, Integer mes) {
        return repository.findAllByDate(ano, mes);
    }
}
