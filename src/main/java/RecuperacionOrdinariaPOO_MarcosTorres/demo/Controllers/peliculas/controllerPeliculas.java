package RecuperacionOrdinariaPOO_MarcosTorres.demo.Controllers.peliculas;

import RecuperacionOrdinariaPOO_MarcosTorres.demo.Exceptions.peliculas.ExceptionColumnDuplicat;
import RecuperacionOrdinariaPOO_MarcosTorres.demo.Exceptions.peliculas.ExceptionPeliculaNotFound;
import RecuperacionOrdinariaPOO_MarcosTorres.demo.Models.DTO.peliculasDTO;
import RecuperacionOrdinariaPOO_MarcosTorres.demo.Services.peliculas.peliculasService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/peliculas")
public class controllerPeliculas {
    @Autowired
    private peliculasService service;

    @GetMapping("/getAllPeliculas")
    private ResponseEntity<Page<peliculasDTO>> getAllPeliculas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5")int size){

        if (size <= 0 || size > 10){
            ResponseEntity.badRequest().body(Map.of(
                "status", "El tamaño de la pagina debe de estar entre 1 y 5"
            ));
            return ResponseEntity.ok(null);
        }
        Page<peliculasDTO> peliculas = service.getAllPeliculas(page, size);
        if (peliculas == null){
            ResponseEntity.badRequest().body(Map.of(
                "status", "error al obtener datos"
            ));
        }
        return ResponseEntity.ok(peliculas);
    }
    @PostMapping("/newPelicula")
    private ResponseEntity<Map<String, Object>> inserPelicula(@Valid @RequestBody peliculasDTO json, HttpServletRequest request){
        try{
            peliculasDTO response =service.insertPelicula(json);
            if (response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "Error", "Inserción incorrecta",
                        "Estatus", "Inserción incorrecta",
                        "Descripción", "Verifique los valores"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Estado", "Completado",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar pelicula",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/updatePelicula/{id}")
    public ResponseEntity<?> modificarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody peliculasDTO peliculas,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            peliculasDTO peliculaActualizada = service.update(id, peliculas);
            return ResponseEntity.ok(peliculaActualizada);
        }
        catch (ExceptionPeliculaNotFound e){
            return ResponseEntity.notFound().build();
        }
        catch (ExceptionColumnDuplicat e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados","campo", e.getColumnDuplicate)
            );
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Map<String, Object>> eliminarPelicula(@PathVariable Long id) {
        try {
            if (!service.delete(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje-Error", "Categoría no encontrada")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "La pelicula no ha sido encontrada",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }

            // Si la eliminación fue exitosa, retorna 200 (OK) con mensaje de confirmación
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Categoría eliminada exitosamente"  // Mensaje de éxito
            ));

        } catch (Exception e) {
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar la pelicula",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }

}
