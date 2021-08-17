package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Cliente;
import com.dan.dot.lab01.repository.ClienteRepository;
import com.dan.dot.lab01.service.ClienteService;
import com.dan.dot.lab01.service.RiesgoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired //Servicio del que depende
    private final RiesgoCrediticioService riesgoService;

    @Autowired //Configuration necesaria para persistir
    ClienteRepository clienteRepo;

    public ClienteServiceImpl(RiesgoCrediticioService rs) {
        this.riesgoService = rs;
    }

    @Override
    public Cliente guardarCliente(Cliente c) throws RiesgoException, RecursoNoEncontradoException {

        Cliente clienteGuardado;

        if (validarDatosCliente(c)) { //Si tiene obras, información de usuario y contraseña
            if (!riesgoService.reporteBCRAPositivo(c.getCuit())) {
                throw new RiesgoException("Riesgo Excepcion: BCRA"); //TODO: Documentar en informe que esto es un mock
            }
        }

        try{
            clienteGuardado = clienteRepo.save(c);
        }
        catch(Exception ex){
            throw new RecursoNoEncontradoException("Falta información obligatoria de usuario" + ex);
        }

        return clienteGuardado;
    }

    private boolean validarDatosCliente(Cliente c) {
        boolean obrasValido = c.getObras() != null;

        return obrasValido && c.getUsuario() != null && c.getUsuario().getPassword() != null;
    }

    @Override
    public Cliente bajaCliente(Integer id) throws RecursoNoEncontradoException, OperacionNoPermitidaException {

        Cliente c;

        if (clienteRepo.existsById(id)) {
            if (validarBajaCliente(id)) { //Si tiene pedidos pendientes
                c = clienteRepo.findClienteById(id).get();
                c.setFechaBaja(Calendar.getInstance().getTime());
                clienteRepo.save(c);
            } else {
                throw new OperacionNoPermitidaException("No se puede eliminar, el cliente tiene pedidos pendientes");
            }
        } else {
            throw new RecursoNoEncontradoException("Cliente con id no encontrado: ", id);
        }

        return c;
    }

    // TODO: Implementar este método
    private boolean validarBajaCliente(Integer id) {
        return true;
    }

    @Override
    public Cliente altaCliente(Integer id) throws RecursoNoEncontradoException {

        if (!clienteRepo.existsById(id))
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", id);

        Cliente c = clienteRepo.findClienteById(id).get();
        c.setFechaBaja(null);
        clienteRepo.save(c);

        return c;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer id) throws RecursoNoEncontradoException {
        if (!clienteRepo.existsById(id))
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: ", id);

        return this.clienteRepo.findClienteById(id);
    }

    @Override
    public Optional<Cliente> buscarClientePorCuit(String cuit) throws RecursoNoEncontradoException {
        if (!clienteRepo.existsByCuit(cuit))
            throw new RecursoNoEncontradoException("Cliente no encontrado con CUIT: ", cuit);

        return this.clienteRepo.findClienteByCuit(cuit);
    }

    @Override
    public Optional<Cliente> clientePorRazonSocial(String razonSocial) throws RecursoNoEncontradoException {
        if (!clienteRepo.existsByRazonSocial(razonSocial))
            throw new RecursoNoEncontradoException("Cliente no encontrado con Razon Social:", razonSocial);

        return this.clienteRepo.findClienteByRazonSocial(razonSocial);
    }
}
