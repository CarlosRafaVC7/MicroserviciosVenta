package pe.edu.upeu.microserviciosventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upeu.microserviciosventa.entity.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
