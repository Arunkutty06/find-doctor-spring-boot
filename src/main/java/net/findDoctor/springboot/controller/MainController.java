package net.findDoctor.springboot.controller;



import net.findDoctor.springboot.dto.PasswordChangeDTO;
import net.findDoctor.springboot.model.Doctor;
import net.findDoctor.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
//        List<UserService> doctorId= userService.
        return "login";
    }

//    @GetMapping("/")
//    public String home(){
//        return "dashboard";
//    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }
//    @GetMapping("/temp")
//    public String temp(){
//        return "temp";
//    }

    @GetMapping("/changePassword")
    public String editPassword(){
        return "edit_user";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor user = userService.findByEmail(email);

        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPass(), user.getPassword())){
            model.addAttribute("error", "Current password is incorrect.");
            return "edit_user";
        }
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPass()));
        userService.saveUser(user);
        model.addAttribute("message", "Password Successfully Changed.");
        return "redirect:/";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") Doctor user) {

        userService.saveUser(user);
        return "redirect:/";
    }

}