package br.org.sesisenai.ava.repository;

import br.org.sesisenai.ava.entity.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
}
