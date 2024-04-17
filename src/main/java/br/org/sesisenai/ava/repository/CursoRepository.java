package br.org.sesisenai.ava.repository;

import br.org.sesisenai.ava.entity.Curso;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
