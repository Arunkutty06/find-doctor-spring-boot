package net.findDoctor.springboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import net.findDoctor.springboot.model.Appointment;
import net.findDoctor.springboot.Repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void saveAppointments(Appointment appointments) {
        this.appointmentRepository.save(appointments);
    }

    @Override
    public Appointment getAppointmentId(long id) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        Appointment appointments = null;
        if (optional.isPresent()) {
            appointments = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return appointments;
    }

    @Override
    public void deleteAppointmentById(long id) {
        this.appointmentRepository.deleteById(id);
    }

    @Override
    public Page<Appointment> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Long doctorId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.appointmentRepository.findByDoctorId(doctorId, pageable);
    }
}