package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Obra;
import com.dan.dot.lab01.repository.ObraRepository;
import com.dan.dot.lab01.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraServiceImpl implements ObraService {

    @Autowired
    ObraRepository obraRepository;

    @Override
    public Obra guardarObra(Obra o) throws RecursoNoPersistidoException {
        Obra obraGuardada;

        try{
            obraGuardada = obraRepository.save(o);
        }
        catch(Exception ex){
            throw new RecursoNoPersistidoException("Falta información obligatoria de obra.");
        }

        return obraGuardada;
    }

    @Override
    public Obra bajaObra(Integer id) throws RecursoNoEncontradoException {
        Obra o;

        if(obraRepository.existsById(id)){
            o = obraRepository.findObraById(id).get();
            o.setHabilitado(false);
            obraRepository.save(o);
        }
        else{
            throw new RecursoNoEncontradoException("Obra con id no encongrada: ", id);
        }

        return o;
    }

    @Override
    public List<Obra> listarObras() {
        return obraRepository.findAll();
    }

    @Override
    public List<Obra> listarObrasPorIdCliente(Integer idCliente) {
        return obraRepository.findObrasByClienteId(idCliente);
    }

    @Override
    public List<Obra> listarObrasPorIdTipo(Integer idTipo) {
        return obraRepository.findObrasByTipoId(idTipo);
    }

    @Override
    public Optional<Obra> buscarObraPorId(Integer id){
        return obraRepository.findObraById(id);
    }

}
