package gr.aueb.cf.tsaousisfinal.model;


import gr.aueb.cf.tsaousisfinal.core.enums.GenderType;
import gr.aueb.cf.tsaousisfinal.core.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String vat;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column
    private GenderType genderType;



    @Enumerated(EnumType.STRING)
    @Column
    private RoleType role;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Warden Warden;


}
