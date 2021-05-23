package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Cliente;
import com.dan.dot.lab01.repository.ClienteRepository;
import com.dan.dot.lab01.rest.ClienteRest;
import com.dan.dot.lab01.service.ClienteService;
import com.dan.dot.lab01.service.RiesgoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.dot.lab01.rest.ClienteRest.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepo;

    private final RiesgoCrediticioService riesgoService;
    private final List<Cliente> listaClientes = new ArrayList<>();
    private Integer ID_GEN = 1;

    public ClienteServiceImpl(RiesgoCrediticioService rs, ClienteRest clienteRest) {
        this.riesgoService = rs;
    }

    @Override
    public Cliente guardarCliente(Cliente c) throws RiesgoException, RecursoNoEncontradoException {

        if (!(c.getId() != null && c.getId() > 0)) { //Si no existe el usuario
            if (validarDatosCliente(c)) { //Si tiene obras e información de usuario y contraseña
                if (!riesgoService.reporteBCRAPositivo(c.getCuit())) {
                    throw new RiesgoException("BCRA");
                }
                c.setId(ID_GEN++);
                listaClientes.add(c);
            } else {
                throw new RecursoNoEncontradoException("Falta información obligatoria de usuario");
            }
        } else { //Si el usuario ya existe, se actualiza
            if (validarDatosCliente(c)) { //Si tiene obras e información de usuario y contraseña
                OptionalInt indexOpt = IntStream.range(0, listaClientes.size())
                        .filter(i -> listaClientes.get(i).getId().equals(c.getId()))
                        .findFirst();
                if (indexOpt.isPresent()) {
                    listaClientes.set(indexOpt.getAsInt(), c);
                } else {
                    throw new RecursoNoEncontradoException("Cliente", c.getId());
                }
            } else {
                throw new RecursoNoEncontradoException("Falta información obligatoria de usuario");
            }
        }

        this.clienteRepo.save(c);

        return c;
    }

    private boolean validarDatosCliente(Cliente c) {
        return c.getObras() != null && c.getUser() != null && c.getPassword() != null;
    }

    @Override
    public Cliente bajaCliente(Cliente c) throws RecursoNoEncontradoException, OperacionNoPermitidaException {
        c.setFechaBaja(Calendar.getInstance().getTime());
        /*if(c.getPedidos() != null && !c.getPedidos().isEmpty()) {
            c.setFechaBaja(Calendar.getInstance().getTime());
        }*/
        return c;
    }

    @Override
    public List<Cliente> listarClientes() {
        return listaClientes;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer id) throws RecursoNoEncontradoException {
        /*Optional<Cliente> c = listaClientes
                .stream()
                .filter(unCli -> unCli.getId().equals(id) && unCli.getFechaBaja() == null)
                .findFirst();

        if (c.isEmpty())
            throw new RecursoNoEncontradoException("Cliente", id);

        return c;
         */

        return this.clienteRepo.findById(id);
    }

    @Override
    public Optional<Cliente> clientePorCuit(String cuit) throws RecursoNoEncontradoException {
        Optional<Cliente> c = listaClientes
                .stream()
                .filter(unCli -> unCli.getCuit().equals(cuit) && unCli.getFechaBaja() == null)
                .findFirst();

        if (c.isEmpty())
            throw new RecursoNoEncontradoException("Cliente", Integer.parseInt(cuit));

        return c;
    }

    @Override
    public Optional<Cliente> clientePorRazonSocial(String razonSocial) throws RecursoNoEncontradoException {
        Optional<Cliente> c = listaClientes
                .stream()
                .filter(unCli -> unCli.getRazonSocial().equals(razonSocial) && unCli.getFechaBaja() == null)
                .findFirst();

        if (c.isEmpty())
            throw new RecursoNoEncontradoException("Cliente", razonSocial);

        return c;
    }
}
