package com.dan.dot.usuarios.service;

import com.dan.dot.usuarios.domain.*;
import com.dan.dot.usuarios.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceUnitTest {

    @Autowired
    ClienteService clienteService;

    @Autowired
    RiesgoCrediticioService riesgoCrediticioService;

    @MockBean
    ClienteRepository clienteRepo;

    Cliente unCliente;

    /*
    @BeforeEach
    void setUp() throws Exception {
        unCliente = new Cliente();
        unCliente.setCuit("20410436524");
        unCliente.setRazonSocial("FaustoD");
        unCliente.setMail("faus@mail.com");
        Obra obra = new Obra();
        TipoObra tipoObra = new TipoObra();
        tipoObra.setDescripcion("Casa");
        obra.setTipo(tipoObra);
        Usuario usuario = new Usuario();
        usuario.setUser("fdavid");
        usuario.setPassword("123456");
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setTipo("CLIENTE");
        usuario.setTipo(tipoUsuario);
        unCliente.setUser(usuario);
        unCliente.setObras(new ArrayList<>());
        unCliente.getObras().add(obra);
    }*/

    @Test
    void guardarClienteConInformacionObligatoria() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException {
        crearClienteConInfoObligatoria();
        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente);
        Cliente clienteResultado = clienteService.guardarCliente(unCliente);

        assertEquals(clienteResultado.getRazonSocial(), (unCliente.getRazonSocial()));
        assertEquals(clienteResultado.getCuit(),(unCliente.getCuit()));
        assertEquals(clienteResultado.getMail(),(unCliente.getMail()));
        assertEquals(clienteResultado.getUsuario().getPassword(),(unCliente.getUsuario().getPassword()));
        assertEquals(clienteResultado.getUsuario().getUsuario(),(unCliente.getUsuario().getUsuario()));
        assertEquals(clienteResultado.getUsuario().getTipo(),(unCliente.getUsuario().getTipo()));
        assertNotNull(clienteResultado.getObras());
        assertNull(clienteResultado.getFechaBaja());

        assertEquals(1, (int) clienteResultado.getId());
        verify(clienteRepo,times(1)).save(unCliente);
    }

    @Test
    void guardarClienteSinInformacionObligatoria(){
        crearClienteSinInfoDeUsuario(); // crearClienteSinInfoDeObra()
        ClienteService.RecursoNoEncontradoException thrown = assertThrows(ClienteService.RecursoNoEncontradoException.class, () -> clienteService.guardarCliente(unCliente));
        assertEquals("Falta información obligatoria de usuario", thrown.getMessage());
    }

    @Test
    void eliminarClienteConFechaBaja() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException, ClienteService.OperacionNoPermitidaException {
        crearClienteConInfoObligatoria();
        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente);
        clienteService.guardarCliente(unCliente);
        Cliente clienteResultado = clienteService.bajaCliente(unCliente.getId());
        assertNotNull(clienteResultado.getFechaBaja());
    }

    @Test
    void altaClienteSinFechaBaja() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException, ClienteService.OperacionNoPermitidaException {
        crearClienteConInfoObligatoria();
        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente);
        clienteService.guardarCliente(unCliente);
        clienteService.bajaCliente(unCliente.getId());
        Cliente clienteResultado = clienteService.altaCliente(unCliente.getId());
        assertNull(clienteResultado.getFechaBaja());
    }

    @Test
    void listarSoloClientesActivos() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException, ClienteService.OperacionNoPermitidaException {
        crearClienteConInfoObligatoria();
        Cliente clienteHabilitado = clienteService.guardarCliente(unCliente);
        crearClienteConFechaBaja();
        Cliente clienteDadoDeBaja = clienteService.guardarCliente(unCliente);
        assertFalse(clienteService.listarClientes().contains(clienteDadoDeBaja));
        assertTrue(clienteService.listarClientes().contains(clienteHabilitado));
    }

    @Test
    void listarClientePorId() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException, ClienteService.OperacionNoPermitidaException {
        crearClienteConInfoObligatoria();
        System.out.println(unCliente);

        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente); //Veo el valor del cliente
        Cliente clienteResultado = clienteService.guardarCliente(unCliente); //Lo guardo en la BDD

        System.out.println(clienteResultado);

        assertEquals(1, (int) clienteResultado.getId());  //Verifico que el valor retornado por la BDD tenga ID:1
        verify(clienteRepo,times(1)).save(unCliente); //Verifico que clienteRepo se haya invocado una vez

        assertEquals(Optional.of(clienteResultado), clienteService.buscarClientePorId(clienteResultado.getId()));
    }

    @Test
    void listarClientePorRazonSocial() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException, ClienteService.OperacionNoPermitidaException {
        crearClienteConInfoObligatoria();
        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente);
        Cliente clienteResultado = clienteService.guardarCliente(unCliente);

        assertEquals("FaustoD", clienteResultado.getRazonSocial());
        verify(clienteRepo,times(1)).save(unCliente);

        assertEquals(Optional.of(clienteResultado), clienteService.clientePorRazonSocial(clienteResultado.getRazonSocial()));
    }

    @Test
    void listarClientePorCuit() throws ClienteService.RiesgoException, ClienteService.RecursoNoEncontradoException, ClienteService.OperacionNoPermitidaException {
        crearClienteConInfoObligatoria();
        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente);
        Cliente clienteResultado = clienteService.guardarCliente(unCliente);

        assertEquals("20410436524", clienteResultado.getCuit());
        verify(clienteRepo,times(1)).save(unCliente);

        assertEquals(Optional.of(clienteResultado), clienteService.buscarClientePorCuit(clienteResultado.getCuit()));
    }

    public void crearClienteConInfoObligatoria() {
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

    public Cliente crearClienteConFechaBaja() {
        unCliente = new Cliente();
        unCliente.setCuit("20410436524");
        unCliente.setRazonSocial("FaustoDadoDeBaja");
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
        unCliente.setFechaBaja(Calendar.getInstance().getTime());

        return unCliente;
    }

    public void crearClienteSinInfoDeUsuario() {
        unCliente = new Cliente();
        unCliente.setCuit("20410436524");
        unCliente.setRazonSocial("FaustoD");
        unCliente.setMail("faus@mail.com");

        Obra obra = new Obra();
        TipoObra tipoObra = new TipoObra();
        tipoObra.setTipo("Casa");
        obra.setTipo(tipoObra);

        unCliente.setObras(new ArrayList<>());
        unCliente.getObras().add(obra);
    }

    public void crearClienteSinInfoDeObra() {
        unCliente = new Cliente();
        unCliente.setCuit("20410436524");
        unCliente.setRazonSocial("FaustoD");
        unCliente.setMail("faus@mail.com");

        Usuario usuario = new Usuario();
        usuario.setUsuario("fdavid");
        usuario.setPassword("123456");
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setTipo("CLIENTE");
        usuario.setTipo(tipoUsuario);

        unCliente.setUsuario(usuario);
    }

}