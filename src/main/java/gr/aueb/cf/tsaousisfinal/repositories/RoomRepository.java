package gr.aueb.cf.tsaousisfinal.repositories;

import gr.aueb.cf.tsaousisfinal.model.static_data.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    Optional<Room> findById(Long id);

}
