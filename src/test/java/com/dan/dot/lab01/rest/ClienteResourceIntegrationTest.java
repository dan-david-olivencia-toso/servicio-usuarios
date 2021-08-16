package com.dan.dot.lab01.rest;

import com.dan.dot.lab01.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteResourceIntegrationTest {

    @LocalServerPort
    private int puerto;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String urlServer = "http://localhost:";
    private final String apiCliente = "/api/cliente";
    private Cliente unCliente;

    @Test
    void crearCliente() {
        String server = urlServer + puerto + apiCliente;
        crearClienteConInfoObligatoria();
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(unCliente);
        ResponseEntity<String> respuesta = testRestTemplate.exchange(server, HttpMethod.POST, requestCliente, String.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void deberiaFallarPorFaltaDeInfo() {
        String server = urlServer + puerto + apiCliente;
        unCliente = new Cliente();
        unCliente.setMail("faus@mail.com");
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(unCliente);
        ResponseEntity<String> respuesta = testRestTemplate.exchange(server, HttpMethod.POST, requestCliente, String.class);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    private void crearClienteConInfoObligatoria() {
        unCliente = new Cliente();
        unCliente.setCuit("20410436524");
        unCliente.setRazonSocial("FaustoD");
        unCliente.setMail("faus@mail.com");

        Obra obra = new Obra();
        TipoObra tipoObra = new TipoObra();
        tipoObra.setTipo("Casa");
        obra.setTipo(tipoObra);

        Usuario usuario = new Usuario();
        usuario.setUsuario("fdavid");
        usuario.setPassword("123456");
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setTipo("CLIENTE");
        usuario.setTipo(tipoUsuario);

        unCliente.setUsuario(usuario);
        unCliente.setObras(new ArrayList<>());
        unCliente.getObras().add(obra);
    }
}
