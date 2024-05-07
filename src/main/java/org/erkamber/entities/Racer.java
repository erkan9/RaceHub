package org.erkamber.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.erkamber.enums.Expertise;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "racers")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Racer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long racerId;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @Column(name = "age_range", length = 10, nullable = false)
    private String ageRange;

    @Column(name = "email", length = 30, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "photo", length = 255)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "expertise", length = 20)
    private Expertise expertise = Expertise.BEGINNER;

    @OneToMany(mappedBy = "racer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> races;
}