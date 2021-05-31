package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Cliente;
import com.dan.dot.lab01.repository.ClienteRepository;
import com.dan.dot.lab01.service.ClienteService;
import com.dan.dot.lab01.service.RiesgoCrediticioService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                throw new RiesgoException("Riesgo Excepcion: BCRA");
            }

            clienteGuardado = clienteRepo.save(c);
        } else {
            throw new RecursoNoEncontradoException("Falta información obligatoria de usuario");
        }

        return clienteGuardado;
    }

    private boolean validarDatosCliente(Cliente c) {
        boolean obrasValido = false;

        if (c.getObras() != null) {
            for (int i = 0; i < c.getObras().size(); i++) {
                obrasValido = c.getObras().get(i).getTipo() != null;
            }
        }

        return obrasValido && c.getUser() != null && c.getUser().getPassword() != null;
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
            throw new RecursoNoEncontradoException("Cliente", id);
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
        if (!clienteRepo.existsByCuit(razonSocial))
            throw new RecursoNoEncontradoException("Cliente no encontrado con Razon Social:", razonSocial);

        return this.clienteRepo.findClienteByRazonSocial(razonSocial);
    }
}
