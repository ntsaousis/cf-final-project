package gr.aueb.cf.tsaousisfinal.model;


import gr.aueb.cf.tsaousisfinal.model.static_data.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "students")
public class Student extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Complaint> complaints = new HashSet<>();

    @PrePersist
    public void initializeUUID() {
        if (uuid == null) uuid = UUID.randomUUID().toString();
    }



}
