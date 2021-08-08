package com.dan.dot.lab01.service;

import com.dan.dot.lab01.domain.Cliente;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//Notación para indicar que es un servicio
@Service
//Asegura que toda la data requerida este segura hasta que la transacción termine
//Recomiendo leer acerca de esta notación (es un mundo completo jeje)
@Transactional
public interface ClienteService {

    public Cliente guardarCliente(Cliente c) throws RecursoNoEncontradoException, RiesgoException;
    public Cliente bajaCliente(Integer id) throws RecursoNoEncontradoException, OperacionNoPermitidaException;
    public List<Cliente> listarClientes();
    public Optional<Cliente> buscarClientePorId(Integer id) throws RecursoNoEncontradoException;
    public Optional<Cliente> buscarClientePorCuit(String cuit) throws RecursoNoEncontradoException;
    public Optional<Cliente> clientePorRazonSocial(String razonSocial) throws RecursoNoEncontradoException;
    public Cliente altaCliente(Integer id) throws RecursoNoEncontradoException;

    class OperacionNoPermitidaException extends Exception {
        public OperacionNoPermitidaException(String errorMessage) {
            super(errorMessage);
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
