package pe.edu.upeu.microserviciosventa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upeu.microserviciosventa.entity.DireccionCliente;
@Repository
public interface DireccionClienteRepository extends JpaRepository<DireccionCliente, Long> {
    List<DireccionCliente> findByClienteId(Long clienteId); // Método personalizado

}
