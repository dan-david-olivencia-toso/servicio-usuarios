package com.dan.dot.lab01.repository;

import com.dan.dot.lab01.domain.Cliente;
import frsf.isi.dan.InMemoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository extends InMemoryRepository<Cliente> {

    @Override
    public Integer getId(Cliente entity) {
        return entity.getId();
    }

    @Override
    public void setId(Cliente entity, Integer id) {
        entity.setId(id);
    }
}
