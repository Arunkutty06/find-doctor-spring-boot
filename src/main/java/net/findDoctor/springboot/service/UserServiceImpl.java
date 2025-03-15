package net.findDoctor.springboot.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import net.findDoctor.springboot.Repository.UserRepository;
import net.findDoctor.springboot.dto.PasswordChangeDTO;
import net.findDoctor.springboot.dto.UserRegistrationDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import net.findDoctor.springboot.model.Role;
import net.findDoctor.springboot.model.Doctor;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Doctor save(UserRegistrationDto registrationDto) {
        Doctor user = new Doctor(registrationDto.getName(),
                             registrationDto.getEmail(),
                              passwordEncoder.encode(registrationDto.getPassword()),
                             Arrays.asList(new Role("DOCTOR_USER")));

        return userRepository.save(user);
    }

    @Override
    public Doctor findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public User getUserById(long id) {
//        Optional<User> optional = userRepository.findById(id);
//        User user = null;
//        if (optional.isPresent()){
//            user = optional.get();
//        } else {
//            throw new RuntimeException(" User not found for id :: " + id);
//        }
//        return user;
//    }

    @Override
    public List<Doctor> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(Doctor user) {
        this.userRepository.save(user);
    }

    @Override
    public boolean changePassword(Long id, PasswordChangeDTO passwordChangeDTO) {
        Doctor user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found"));

        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPass(), user.getPassword())){
            throw new RuntimeException("Current Password Doesn't Matches");
        }
        String encodedNewPassword = passwordEncoder.encode(passwordChangeDTO.getNewPass());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Doctor user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}