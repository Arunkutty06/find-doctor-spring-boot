package net.findDoctor.springboot.service;


import net.findDoctor.springboot.dto.PasswordChangeDTO;
import net.findDoctor.springboot.dto.UserRegistrationDto;
import net.findDoctor.springboot.model.Doctor;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Doctor save(UserRegistrationDto registrationDto);
    Doctor findByEmail(String email);
    List<Doctor> getUser();
    void saveUser(Doctor user);
    boolean changePassword(Long id, PasswordChangeDTO passwordChangeDTO);


}