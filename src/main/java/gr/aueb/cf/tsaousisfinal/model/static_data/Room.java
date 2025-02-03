package gr.aueb.cf.tsaousisfinal.model.static_data;


import jakarta.persistence.*;
import lombok.*;
import gr.aueb.cf.tsaousisfinal.model.Student;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_capacity")
    private int RoomCapacity;


    @Column(name = "available")
    private boolean available = true;

    public boolean getAvailable() {
        return this.students.size() < this.RoomCapacity; // Example logic
    }



    @OneToMany(mappedBy = "room")
    private Set<Student> students = new HashSet<>();

//    @OneToMany(mappedBy = "complaint")
//    private Set<Complaint> complaints = new HashSet<>();

    @PostLoad
    @PostUpdate
    private void updateAvailability() {
        this.available = students.size() < RoomCapacity;
    }


}
