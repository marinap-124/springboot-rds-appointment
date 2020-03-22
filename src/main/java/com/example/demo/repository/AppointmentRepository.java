package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.data.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Integer> 
{

}
