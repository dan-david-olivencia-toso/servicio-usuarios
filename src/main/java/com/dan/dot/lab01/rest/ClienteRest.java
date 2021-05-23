package com.dan.dot.lab01.rest;

import com.dan.dot.lab01.domain.Cliente;
import com.dan.dot.lab01.service.ClienteService;
import com.dan.dot.lab01.service.RiesgoCrediticioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {


    @Autowired
    ClienteService clienteService;

    @Autowired
    RiesgoCrediticioService riesgoCrediticioService;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<?> clientePorId(@PathVariable Integer id) {
        Optional<Cliente> c = null;
        try {
            c = this.clienteService.buscarClientePorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(c);
    }

    @GetMapping(path = "/cuit/{cuit}")
    @ApiOperation(value = "Busca un cliente por CUIT")
    public ResponseEntity<?> clientePorCuit(@PathVariable String cuit) {
        Optional<Cliente> c = null;
        try {
            c = this.clienteService.clientePorCuit(cuit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(c);
    }

    @GetMapping(path = "/razonsocial/{razonSocial}")
    @ApiOperation(value = "Busca clientes por razon social")
    public ResponseEntity<?> clientePorRazonSocial(@PathVariable String razonSocial) {
        Optional<Cliente> c = null;
        try {
            c = this.clienteService.clientePorRazonSocial(razonSocial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(c);
    }

    @GetMapping
    public ResponseEntity<?> todos() {
        List<Cliente> clientes = null;
        try {
            clientes = this.clienteService.listarClientes();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    @ApiOperation(value = "Dar de alta un cliente")
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException {
        Cliente creado = null;
        try {
            creado = this.clienteService.guardarCliente(cliente);
        } catch (ClienteService.RecursoNoEncontradoException | ClienteService.RiesgoException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok(creado);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<?> actualizar(@RequestBody Cliente nuevo) {
        Cliente actualizado = null;
        try {
            actualizado = this.clienteService.guardarCliente(nuevo);
        } catch (ClienteService.RecursoNoEncontradoException | ClienteService.RiesgoException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok(actualizado);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> borrar(@RequestBody Cliente c) {
        Cliente dadoDeBaja = null;
        try {
            dadoDeBaja = this.clienteService.bajaCliente(c);
        } catch (ClienteService.RecursoNoEncontradoException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        } catch (ClienteService.OperacionNoPermitidaException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        this.actualizar(dadoDeBaja);
        return ResponseEntity.ok().build();
    }
}

