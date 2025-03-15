package net.findDoctor.springboot.service;

import net.findDoctor.springboot.model.Doctor;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    Doctor findByEmail(String email);
}
