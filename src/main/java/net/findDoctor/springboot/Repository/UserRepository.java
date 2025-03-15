package net.findDoctor.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.findDoctor.springboot.model.Doctor;

public interface UserRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String email);
}