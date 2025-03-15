package net.findDoctor.springboot.controller;


import net.findDoctor.springboot.model.Category;
import net.findDoctor.springboot.model.Doctor;
import net.findDoctor.springboot.service.CategoryService;
import net.findDoctor.springboot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.findDoctor.springboot.model.Appointment;
import net.findDoctor.springboot.service.AppointmentService;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
     private AppointmentService appointmentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DoctorService doctorService;


    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/showNewPatientForm/{id}")
    public String showNewPatientForm(@PathVariable(value = "id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            // Handle the case where the category is not found
            model.addAttribute("error", "Category not found");
            return "error_page";
        }
        // Create a new Appointment and set the category and doctor details
        Appointment appointment = new Appointment();
        appointment.setCategory(category);
        appointment.setDoctor(category.getDoctor());
        appointment.setDoctorName(category.getDoctor().getName());
        appointment.setCategoryName(category.getCategory());
        // Add the appointment to the model
        model.addAttribute("appointment", appointment);
        return "new_appointments";
    }

    @PostMapping("/saveAppointment")
    public String savePatient(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.saveAppointments(appointment);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentId(id);
        model.addAttribute("appointment", appointment);
        return "update_appointments";
    }

    @GetMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable(value = "id") long id) {
        appointmentService.deleteAppointmentById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        Doctor doctor = doctorService.findByEmail(email);
        Long doctorId = doctor.getId();

        Page <Appointment> page = appointmentService.findPaginated(pageNo, pageSize, sortField, sortDir, doctorId);
        List <Appointment> listAppointments = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listAppointments", listAppointments);
        return "dashboard";
    }
}