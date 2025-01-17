package gr.aueb.cf.tsaousisfinal.repositories;

import gr.aueb.cf.tsaousisfinal.model.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WardenRepository extends JpaRepository<Warden, Long>, JpaSpecificationExecutor<Warden> {


    Optional<Warden> findByUserId(Long id);

    Optional<Warden> findByUuid(String uuid);
}
