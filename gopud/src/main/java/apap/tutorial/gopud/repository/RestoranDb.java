package apap.tutorial.gopud.repository;

import apap.tutorial.gopud.model.RestoranModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.math.BigInteger;

@Repository
public interface RestoranDb extends JpaRepository<RestoranModel, Long> {
    Optional<RestoranModel> findByIdRestoran(Long idRestoran);
}
