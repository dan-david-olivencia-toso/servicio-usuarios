package com.dan.dot.lab01.repository;

import com.dan.dot.lab01.domain.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer> {
    Optional<Obra> findObraById(Integer id);
    List<Obra> findObrasByClienteId(Integer id);
    List<Obra> findObrasByTipoId(Integer id);
    boolean existsById(Integer id);
    Obra save(Obra obra);
}
