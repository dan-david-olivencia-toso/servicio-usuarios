package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Obra;
import com.dan.dot.lab01.repository.ObraRepository;
import com.dan.dot.lab01.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ObraServiceImpl implements ObraService {

    @Autowired
    ObraRepository obraRepository;

    @Override
    public Obra guardarObra(Obra o){
        return o;
    }

    @Override
    public Obra actualizarObra(Obra o){
        return o;
    }

    @Override
    public Obra bajaObra(Integer id){
        return null;
    }

    @Override
    public List<Obra> listarObras(){
        return new ArrayList<Obra>();
    }

    @Override
    public List<Obra> listarObrasPorIdCliente(Integer idCliente){
        return new ArrayList<Obra>();
    }

    @Override
    public Optional<Obra> buscarObraPorId(Integer id){
        return null;
    }

}
