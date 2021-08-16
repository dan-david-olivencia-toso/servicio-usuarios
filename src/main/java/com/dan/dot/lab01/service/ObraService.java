package com.dan.dot.lab01.service;

import com.dan.dot.lab01.domain.Obra;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//Notación para indicar que es un servicio
@Service
//Asegura que toda la data requerida este segura hasta que la transacción termine
//Recomiendo leer acerca de esta notación (es un mundo completo jeje)
@Transactional
public interface ObraService {
    public Obra guardarObra(Obra o) throws RecursoNoPersistidoException;
    public Obra bajaObra(Integer id) throws RecursoNoEncontradoException;
    public List<Obra> listarObras();
    public List<Obra> listarObrasPorIdCliente(Integer idCliente);
    public List<Obra> listarObrasPorIdTipo(Integer idTipo);
    public Optional<Obra> buscarObraPorId(Integer id) throws RecursoNoEncontradoException;

    public static class RecursoNoEncontradoException extends Exception {
        public RecursoNoEncontradoException(String errorMessage, Integer id) {
            super(errorMessage+id);
        }
    }

    public static class RecursoNoPersistidoException extends Exception {
        public RecursoNoPersistidoException(String errorMessage) {super(errorMessage); }
    }
}
