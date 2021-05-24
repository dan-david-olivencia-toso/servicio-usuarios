package com.dan.dot.lab01.service;

import com.dan.dot.lab01.domain.*;
import com.dan.dot.lab01.rest.ClienteRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    public Cliente guardarCliente(Cliente c) throws RecursoNoEncontradoException, RiesgoException;
    public Cliente bajaCliente(Integer id) throws RecursoNoEncontradoException, OperacionNoPermitidaException;
    public List<Cliente> listarClientes();
    public Optional<Cliente> buscarClientePorId(Integer id) throws RecursoNoEncontradoException, RecursoNoEncontradoException;
    public Optional<Cliente> clientePorCuit(String cuit) throws RecursoNoEncontradoException;
    public Optional<Cliente> clientePorRazonSocial(String razonSocial) throws RecursoNoEncontradoException;
    public Cliente altaCliente(Integer id) throws RecursoNoEncontradoException;

    class OperacionNoPermitidaException extends Exception {
        public OperacionNoPermitidaException(String errorMessage, Throwable err) {
            super(errorMessage, err);
        }
    }

    public static class RiesgoException extends Exception{
        public RiesgoException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class RecursoNoEncontradoException extends Exception {
        public RecursoNoEncontradoException(String errorMessage, Integer id) {
            super(errorMessage+id);
        }
        public RecursoNoEncontradoException(String errorMessage) {
            super(errorMessage);
        }

        public RecursoNoEncontradoException(String cliente, String razonSocial) {
            super(cliente+razonSocial);
        }
    }


}
