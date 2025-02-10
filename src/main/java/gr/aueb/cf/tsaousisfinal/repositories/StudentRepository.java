package gr.aueb.cf.tsaousisfinal.repositories;

import gr.aueb.cf.tsaousisfinal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    List<Student> findByRoomId(Long id);

    Optional<Student> findByUserId(long id);

    Optional<Student> findByUuid(String uuid);

    Optional<Student> findByUserUsername(String username);


}
