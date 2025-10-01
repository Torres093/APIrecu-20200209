package RecuperacionOrdinariaPOO_MarcosTorres.demo.Services.peliculas;

import RecuperacionOrdinariaPOO_MarcosTorres.demo.Entities.Peliculas.PeliculasEntity;
import RecuperacionOrdinariaPOO_MarcosTorres.demo.Exceptions.peliculas.ExceptionsNotFound;
import RecuperacionOrdinariaPOO_MarcosTorres.demo.Models.DTO.peliculasDTO;
import RecuperacionOrdinariaPOO_MarcosTorres.demo.Repositories.peliculasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@Slf4j
@CrossOrigin
public class peliculasService {

    @Autowired
    private peliculasRepository repo;

    /**
     *
     * @param data
     * @return
     */
    public peliculasDTO insertPelicula(peliculasDTO data) throws Exception {
        if (data == null){
            throw new IllegalArgumentException("No se puede enviar valores nulos");
        }try {
            PeliculasEntity entity = ConvertirAEntity(data);
            PeliculasEntity PeliculaGuardada = repo.save(entity);
            return convertirApeliculasDTO(PeliculaGuardada);
        }catch (Exception e){
            log.error("Error al registrar la pelicula: " + e.getMessage());
            throw new Exception("Error al registrar la pelicula");
        }
    }

    public Page<peliculasDTO> getAllPeliculas(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<PeliculasEntity> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::convertirApeliculasDTO);
    }

    public peliculasDTO update(Long id, @Valid peliculasDTO json)  {
        PeliculasEntity peliculas = repo.findById(id).orElseThrow(()-> new ExceptionsNotFound("pelicula no encontrada"));

        peliculas.setTitulo(json.getTitulo());
        peliculas.setDirector(json.getDirector());
        peliculas.setAño(json.getAño());
        peliculas.setGenero(json.getGenero());
        peliculas.setDuracion(json.getDuracion());
        peliculas.setPuntuacion(json.getPuntuacion());

        PeliculasEntity peliculaAct = repo.save(peliculas);
        return convertirApeliculasDTO(peliculaAct);
    }
    public boolean delete(Long id) {
        //1. Verificar la existencia del producto
        PeliculasEntity existencia = repo.findById(id).orElse(null);
        //2. Si el valor existe lo elimina
        if (existencia != null){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private peliculasDTO convertirApeliculasDTO(PeliculasEntity peliculaGuardada) {
        peliculasDTO dto = new peliculasDTO();

        dto.setId(peliculaGuardada.getId());
        dto.setTitulo(peliculaGuardada.getTitulo());
        dto.setDirector(peliculaGuardada.getDirector());
        dto.setAño(peliculaGuardada.getAño());
        dto.setGenero(peliculaGuardada.getGenero());
        dto.setDuracion(peliculaGuardada.getDuracion());
        dto.setPuntuacion(peliculaGuardada.getPuntuacion());
        return dto;

    }

    private PeliculasEntity ConvertirAEntity(@Valid  peliculasDTO json) {
        PeliculasEntity entity = new PeliculasEntity();

        entity.setId(json.getId());
        entity.setTitulo(json.getTitulo());
        entity.setDirector(json.getDirector());
        entity.setAño(json.getAño());
        entity.setGenero(json.getGenero());
        entity.setDuracion(json.getDuracion());
        entity.setPuntuacion(json.getPuntuacion());

        return entity;
    }
}
