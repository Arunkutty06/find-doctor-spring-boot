package net.findDoctor.springboot.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Doctor_Name", nullable = true)
    private String name;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctor_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Doctor(String name, String email, String password, Collection<Role> roles) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Doctor() {}

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
