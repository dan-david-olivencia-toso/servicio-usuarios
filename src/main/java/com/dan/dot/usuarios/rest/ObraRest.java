package com.dan.dot.usuarios.rest;

import com.dan.dot.usuarios.domain.Obra;
import com.dan.dot.usuarios.service.ObraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/obra")
@Api(value = "ObraRest", description = "Permite gestionar los obras de la empresa")
public class ObraRest {

    @Autowired
    ObraService obraService;

    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca una obra por id")
    public ResponseEntity<?> obraPorId(@PathVariable Integer id) {
        Optional<Obra> o = null;

        try {
            o = obraService.buscarObraPorId(id);
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return ResponseEntity.ok(o);
    }

    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/cliente/{id}")
    @ApiOperation(value = "Busca las obras de un cliente")
    public ResponseEntity<?> obraPorClienteId(@PathVariable Integer id){
        List<Obra> listaObras = new ArrayList<Obra>();
        try{
            listaObras = obraService.listarObrasPorIdCliente(id);
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return ResponseEntity.ok(listaObras);
    }

    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/tipo/{id}")
    @ApiOperation(value = "Filtra las obras por tipo")
    public ResponseEntity<?> obraPorTipo(@PathVariable Integer id){
        List<Obra> listaObras = new ArrayList<Obra>();
        try{
            listaObras = obraService.listarObrasPorIdTipo(id);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
        return ResponseEntity.ok(listaObras);
    }

    @CrossOrigin(maxAge = 86400)
    @GetMapping
    public ResponseEntity<?> todos(){
        List<Obra> listaObras = new ArrayList<Obra>();
        try{
            listaObras = obraService.listarObras();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(listaObras);
    }

    @CrossOrigin(maxAge = 86400)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Obra nuevo) throws ObraService.RecursoNoPersistidoException {
        Obra obraCreada = null;

        try{
            obraCreada = obraService.guardarObra(nuevo);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(obraCreada);
    }

    @CrossOrigin(maxAge = 86400)
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un obra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<?> actualizar(@RequestBody Obra nuevo,  @PathVariable Integer id){
        Obra obraActualizada = null;

        try{
            obraActualizada = obraService.guardarObra(nuevo);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(obraActualizada);
    }

    @CrossOrigin(maxAge = 86400)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> baja(@PathVariable Integer id){
        try{
            obraService.bajaObra(id);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
