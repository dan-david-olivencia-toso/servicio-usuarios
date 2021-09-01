package com.dan.dot.usuarios.service;

import com.dan.dot.usuarios.domain.Empleado;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// Notación para indicar que es un servicio
@Service
// Asegura que toda la data requerida esté segura hasta que la transacción termine
@Transactional
public interface EmpleadoService {
    public Empleado guardarEmpleado(Empleado c) throws RecursoNoEncontradoException;
    public Empleado bajaEmpleado(Integer id) throws RecursoNoEncontradoException;
    public List<Empleado> listarEmpleados();
    public Optional<Empleado> buscarEmpleadoPorId(Integer id)  throws RecursoNoEncontradoException;
    public Optional<Empleado> buscarEmpleadoPorUsuarioUsuario(String usuario)  throws RecursoNoEncontradoException;

    class OperacionNoPermitidaException extends Exception {
        public OperacionNoPermitidaException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class RecursoNoEncontradoException extends Exception {
        public RecursoNoEncontradoException(String errorMessage, Integer id) {
            super(errorMessage+id);
        }
        public RecursoNoEncontradoException(String errorMessage, String string) {
            super(errorMessage+string);
        }
        public RecursoNoEncontradoException(String errorMessage) {
            super(errorMessage);
        }
    }
}
