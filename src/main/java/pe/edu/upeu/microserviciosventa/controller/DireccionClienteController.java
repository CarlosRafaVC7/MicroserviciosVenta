package pe.edu.upeu.microserviciosventa.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.microserviciosventa.entity.DireccionCliente;
import pe.edu.upeu.microserviciosventa.repository.ClienteRepository;
import pe.edu.upeu.microserviciosventa.repository.DireccionClienteRepository;
import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/direcciones")
public class DireccionClienteController {
	@Autowired
    private DireccionClienteRepository direccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // 1. Listar todas las direcciones de un cliente
    @GetMapping
    public List<DireccionCliente> listarDirecciones(@PathVariable Long clienteId) {
        return direccionRepository.findByClienteId(clienteId);
    }

    // 2. Crear una nueva dirección para un cliente
    @PostMapping
    public ResponseEntity<DireccionCliente> crearDireccion(
            @PathVariable Long clienteId,
            @RequestBody DireccionCliente direccion) {

        return clienteRepository.findById(clienteId)
                .map(cliente -> {
                    direccion.setCliente(cliente);
                    DireccionCliente nuevaDireccion = direccionRepository.save(direccion);
                    return ResponseEntity.ok(nuevaDireccion);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
    }

    // 3. Actualizar una dirección existente
    @PutMapping("/{direccionId}")
    public ResponseEntity<DireccionCliente> actualizarDireccion(
            @PathVariable Long clienteId,
            @PathVariable Long direccionId,
            @RequestBody DireccionCliente direccionActualizada) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clienteId);
        }

        return direccionRepository.findById(direccionId)
                .map(direccion -> {
                    direccion.setDireccion(direccionActualizada.getDireccion());
                    direccion.setCiudad(direccionActualizada.getCiudad());
                    direccion.setCodigoPostal(direccionActualizada.getCodigoPostal());
                    DireccionCliente direccionGuardada = direccionRepository.save(direccion);
                    return ResponseEntity.ok(direccionGuardada);
                })
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + direccionId));
    }

 // 4. Eliminar una dirección
    @DeleteMapping("/{direccionId}")
    public ResponseEntity<Void> eliminarDireccion(
            @PathVariable Long clienteId,
            @PathVariable Long direccionId) {

                                                           if (!clienteRepository.existsById(clienteId)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clienteId);
        }

                                                                 return direccionRepository.findById(direccionId)
                .map(direccion -> {
                    direccionRepository.delete(direccion);
                    return ResponseEntity.noContent().<Void>build(); 
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }
}
