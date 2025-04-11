package com.e.hospi.demo.Services;

import java.util.List;

import com.e.hospi.demo.Domain.Sex;

public interface SexService {
    public Sex findById(int idSex);
    public void save(Sex sex);
    public void delete(int idSex);   
    public List<Sex> getAllSexs();
}
