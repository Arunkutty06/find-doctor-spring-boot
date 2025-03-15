package net.findDoctor.springboot.service;

import net.findDoctor.springboot.model.Appointment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {
        List<Appointment> getAllAppointments();
        void saveAppointments(Appointment appointments);
        Appointment getAppointmentId(long id);
        void deleteAppointmentById(long id);
        Page<Appointment> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Long doctorId);
    }

