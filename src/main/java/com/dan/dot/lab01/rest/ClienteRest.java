package com.dan.dot.lab01.rest;

import com.dan.dot.lab01.domain.Cliente;
import com.dan.dot.lab01.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {

    @Autowired
    ClienteService clienteService;

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
            c = this.clienteService.buscarClientePorCuit(cuit);
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
        Cliente clienteCreado = null;
        try {
            clienteCreado = this.clienteService.guardarCliente(cliente);
        } catch (ClienteService.RecursoNoEncontradoException | ClienteService.RiesgoException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok(clienteCreado);
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
    public ResponseEntity<?> borrar(@PathVariable Integer id){
        try {
            this.clienteService.bajaCliente(id);
        } catch (ClienteService.RecursoNoEncontradoException | ClienteService.OperacionNoPermitidaException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "alta/{id}")
    public ResponseEntity<?> altaCliente(@PathVariable Integer id){
        try {
            this.clienteService.altaCliente(id);
        } catch (ClienteService.RecursoNoEncontradoException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}

