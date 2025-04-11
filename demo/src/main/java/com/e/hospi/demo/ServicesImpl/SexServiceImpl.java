package com.e.hospi.demo.ServicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.Sex;
import com.e.hospi.demo.Repositories.SexRepository;
import com.e.hospi.demo.Services.SexService;

@Service
public class SexServiceImpl implements SexService{

    private final SexRepository sexRepository;

    public SexServiceImpl(SexRepository sexRepository) {
        this.sexRepository = sexRepository;
    }

    @Override
    public Sex findById(int idSex) {
        return sexRepository.findById(idSex).orElseThrow(() -> new RuntimeException("Sexo no encontrado"));
    }

    @Override
    public void save(Sex sex) {
        sexRepository.save(sex);
    }

    @Override
    public void delete(int idSex) {
        sexRepository.deleteById(idSex);
    }

    @Override
    public List<Sex> getAllSexs() {
        return sexRepository.findAll();
    }

}
