package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.data.Appointment;
import com.example.demo.data.Service;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.ServiceRepository;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
@RequestMapping(value = "/api/v1")
public class AppointmentController {
	@Autowired
	private  AppointmentRepository appointmentRepository;
	
	@Autowired
	private  ServiceRepository serviceRepository;	
	
	public AppointmentController(AppointmentRepository appointmentRepository,
			ServiceRepository serviceRepository)
	{
		this.appointmentRepository = appointmentRepository;
		this.serviceRepository = serviceRepository ;
	}
	
    @GetMapping("/appointments")
    public List<Appointment> all() {
    	log.info("start find all appointments  ...");
    	log.info("count appointments ..." + this.appointmentRepository.findAll().size());
    	
        return this.appointmentRepository.findAll();
    }
    
    
    @GetMapping("/appointments/{id}")
    public Appointment loadAppointment(@PathVariable(value = "id") String appointmentId) {
    	log.info("start loading one  ...");
    	Appointment appointment = appointmentRepository.findById(Integer.valueOf(appointmentId))
       .orElseThrow(() -> new ResourceNotFoundException("appointment not found for this id :: " + appointmentId));
        
        log.info("Loading :" + appointment.getId());
        
        return appointment;
    }     

    @PostMapping("/appointments")
    public Appointment create(@RequestBody Appointment appointment) {
    	log.info("start creating appointment  ...");
    	
    	//appointment.
    	
        return this.appointmentRepository.save(appointment);
    } 
    
    @DeleteMapping("/appointments/{id}")
    public Map<String, Boolean> deleteAppointment(@PathVariable(value = "id") String appointmentId)
         throws ResourceNotFoundException {
    	log.info("start deleting " + appointmentId);
    	Appointment appointment = appointmentRepository.findById(Integer.valueOf(appointmentId))
       .orElseThrow(() -> new ResourceNotFoundException("Appointment not found for this id :: " + appointmentId));

        log.info("Deleting appointment" + appointment.getId());
        appointmentRepository.delete(appointment);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    } 
    
    @PutMapping("/appointments/{id}")
    public Map<String, Boolean> updateAppointment(@PathVariable(value = "id") String appointmentId,
    		 @RequestBody Appointment data
    		)
         throws ResourceNotFoundException {
    	log.info("start updating " + appointmentId);
    	Appointment appointment = appointmentRepository.findById(Integer.valueOf(appointmentId))
       .orElseThrow(() -> new ResourceNotFoundException("appointment not found for this id :: " + appointmentId));

    	appointment.setService(data.getService());
    	appointment.setComment(data.getComment());
    	appointment.setTime(data.getTime());
        log.info("Update " + appointment.getId());
        appointmentRepository.save(appointment);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }       
    
}
