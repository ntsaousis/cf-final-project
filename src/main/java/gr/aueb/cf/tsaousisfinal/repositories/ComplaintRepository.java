package gr.aueb.cf.tsaousisfinal.repositories;

import gr.aueb.cf.tsaousisfinal.core.enums.RequestStatus;
import gr.aueb.cf.tsaousisfinal.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>, JpaSpecificationExecutor<Complaint> {

    // Fetch all complaints by a specific student
    List<Complaint> findByStudentId(Long studentId);

    // Fetch all complaints by a specific status
    List<Complaint> findByStatus(RequestStatus status);

    // Fetch all complaints associated with a specific warden
    List<Complaint> findByWardenId(Long wardenId);

    List<Complaint> findByRoomId(Long roomId);
}
