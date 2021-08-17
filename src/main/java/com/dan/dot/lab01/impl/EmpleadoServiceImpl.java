package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Empleado;
import com.dan.dot.lab01.repository.EmpleadoRepository;
import com.dan.dot.lab01.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    public Empleado guardarEmpleado(Empleado e) throws RecursoNoEncontradoException {
        Empleado empleadoGuardado;
        try{
            empleadoGuardado = empleadoRepository.save(e);
        }
        catch (Exception ex){
            throw new RecursoNoEncontradoException("Falta información obligatoria de empleado: " + ex);
        }
        return empleadoGuardado;
    }

    public Empleado bajaEmpleado(Integer id) throws RecursoNoEncontradoException {
        Empleado e;

        if(empleadoRepository.existsById(id)){
            e = empleadoRepository.findEmpleadoById(id).get();
            e.setHabilitado(false);
            empleadoRepository.save(e);
        }
        else{
            throw new RecursoNoEncontradoException("Empleado con id no encontrado: ", id);
        }

        return e;
    }

    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> buscarEmpleadoPorId(Integer id){
        return empleadoRepository.findEmpleadoById(id);
    }

    public Optional<Empleado> buscarEmpleadoPorUsuarioUsuario(String usuario) throws RecursoNoEncontradoException{
        if(!empleadoRepository.existsByUsuarioUsuario(usuario)){
            throw new EmpleadoService.RecursoNoEncontradoException("Cliente no encontrado con nombre de usuario: ", usuario);
        }
        return this.empleadoRepository.findEmpleadoByUsuarioUsuario(usuario);
    }

}
