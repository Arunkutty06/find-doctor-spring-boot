package net.findDoctor.springboot.Repository;

import net.findDoctor.springboot.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
Doctor findByEmail(String email);
}
