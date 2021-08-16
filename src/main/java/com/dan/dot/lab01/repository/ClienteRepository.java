package com.dan.dot.lab01.repository;

import com.dan.dot.lab01.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findClienteById(Integer id);
    Optional<Cliente> findClienteByCuit(String cuit);
    Optional<Cliente> findClienteByRazonSocial(String razonSocial);
    boolean existsByCuit(String cuit);
    boolean existsByRazonSocial(String razonSocial);
    Cliente save(Cliente cliente);
}
