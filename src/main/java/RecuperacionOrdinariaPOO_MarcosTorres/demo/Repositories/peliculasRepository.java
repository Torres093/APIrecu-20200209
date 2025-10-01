package RecuperacionOrdinariaPOO_MarcosTorres.demo.Repositories;

import RecuperacionOrdinariaPOO_MarcosTorres.demo.Entities.Peliculas.PeliculasEntity;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface peliculasRepository extends JpaRepository<PeliculasEntity, Long> {
    Page<PeliculasEntity> findAll(Pageable pageable);
}
