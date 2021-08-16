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
    public Obra guardarObra(Obra o);
    public Obra actualizarObra(Obra o);
    public Obra bajaObra(Integer id);
    public List<Obra> listarObras();
    public List<Obra> listarObrasPorIdCliente(Integer idCliente);
    public Optional<Obra> buscarObraPorId(Integer id);
//    public boolean clienteTieneObras(Integer idCliente); //TODO: Para Repository

}
