package com.dan.dot.usuarios.rest;

import com.dan.dot.usuarios.domain.*;
import com.dan.dot.usuarios.service.EmpleadoService;
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
@RequestMapping("/api/empleado")
@Api(value = "EmpleadoRest", description = "Permite gestionar los empleados de la empresa")
public class EmpleadoRest {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un empleado por id")
    public ResponseEntity<?> empleadoPorId(@PathVariable Integer id) {
        Optional<Empleado> e = null;

        try {
            e = this.empleadoService.buscarEmpleadoPorId(id);
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return ResponseEntity.ok(e);
    }

    @GetMapping(path = "/usuario/{usuario}")
    @ApiOperation(value = "Busca un empleado por nombre de usuario")
    public ResponseEntity<?> empleadoPorNombreUsuario(@PathVariable String usuario){
        Optional<Empleado> e = null;

        try {
            e = empleadoService.buscarEmpleadoPorUsuarioUsuario(usuario);
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        return ResponseEntity.ok(e);
    }

    @GetMapping
    public ResponseEntity<?> todos(){
        List<Empleado> empleados;

        try{
            empleados = this.empleadoService.listarEmpleados();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(empleados);
    }

    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) throws EmpleadoService.RecursoNoEncontradoException { //FIXME: Remover este throws
        Empleado empleadoCreado = null;
        try {
            empleadoCreado = empleadoService.guardarEmpleado(empleado);
        }
        catch (EmpleadoService.RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(empleadoCreado);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<?> actualizar(@RequestBody Empleado nuevo){
        Empleado empleadoActualizado = null;

        try {
            empleadoActualizado = empleadoService.guardarEmpleado(nuevo);
        }
        catch(EmpleadoService.RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> borrar(@PathVariable Integer id){
        try{
            empleadoService.bajaEmpleado(id);
        }
        catch(EmpleadoService.RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok().build();

    }
}
