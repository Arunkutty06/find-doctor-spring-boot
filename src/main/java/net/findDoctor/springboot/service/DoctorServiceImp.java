package net.findDoctor.springboot.service;

import net.findDoctor.springboot.Repository.DoctorRepository;
import net.findDoctor.springboot.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImp implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
}
