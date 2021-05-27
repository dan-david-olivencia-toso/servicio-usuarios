package com.dan.dot.lab01.impl;

import com.dan.dot.lab01.domain.Cliente;
import com.dan.dot.lab01.repository.ClienteRepository;
import com.dan.dot.lab01.rest.ClienteRest;
import com.dan.dot.lab01.service.ClienteService;
import com.dan.dot.lab01.service.RiesgoCrediticioService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dan.dot.lab01.rest.ClienteRest.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired //Servicio del que depende
    private final RiesgoCrediticioService riesgoService;

    @Autowired //Configuration necesaria para guardar en memoria
    ClienteRepository clienteRepo;

//    private final List<Cliente> listaClientes = new ArrayList<>();
//    private Integer ID_GEN = 1;

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
//            c.setId(ID_GEN);//SACAR DESPUES DE IMPLEMENTAR BDD
//            ID_GEN++;//SACAR DESPUES DE IMPLEMENTAR BDD

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
                c = clienteRepo.findById(id).get();
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

    private boolean validarBajaCliente(Integer id) {
        return true;
    }

    @Override
    public Cliente altaCliente(Integer id) throws RecursoNoEncontradoException {

        if (!clienteRepo.existsById(id))
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", id);

        Cliente c = clienteRepo.findById(id).get();
        clienteRepo.save(c);

        return c;
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> lc= Lists.newArrayList(clienteRepo.findAll().iterator());

        List<Cliente> lca = lc //Filtro lista de clientes activos
                .stream()
                .filter(unCli -> unCli.getFechaBaja() == null)
                .collect(Collectors.toList());

        return lca;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer id) throws RecursoNoEncontradoException {

        if (!clienteRepo.existsById(id))
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", id);

        return this.clienteRepo.findById(id);
    }

    @Override
    public Optional<Cliente> clientePorCuit(String cuit) throws RecursoNoEncontradoException {
        List<Cliente> listaClientesCuit = Lists.newArrayList(clienteRepo.findAll().iterator());

        Optional<Cliente> c = listaClientesCuit
                .stream()
                .filter(unCli -> unCli.getCuit().equals(cuit) && unCli.getFechaBaja() == null)
                .findFirst();

        if (c.isEmpty())
            throw new RecursoNoEncontradoException("Cliente no encontrado con CUIT:", cuit);

        return c;
    }

    @Override
    public Optional<Cliente> clientePorRazonSocial(String razonSocial) throws RecursoNoEncontradoException {

        List<Cliente> listaClientesRazon = Lists.newArrayList(clienteRepo.findAll().iterator());

        Optional<Cliente> c = listaClientesRazon
                .stream()
                .filter(unCli -> unCli.getRazonSocial().equals(razonSocial) && unCli.getFechaBaja() == null)
                .findFirst();

        if (c.isEmpty())
            throw new RecursoNoEncontradoException("Cliente no encontrado con Razon Social:", razonSocial);

        return c;
    }
}
