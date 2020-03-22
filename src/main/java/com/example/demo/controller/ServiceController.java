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
import com.example.demo.data.Service;
import com.example.demo.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
@RequestMapping(value = "/api/v1")
public class ServiceController {
	@Autowired
	private  ServiceRepository serviceRepository;
	
	public ServiceController(ServiceRepository serviceRepository)
	{
		this.serviceRepository = serviceRepository;
	}
	
    @GetMapping("/services")
    public List<Service> all() {
    	//init();
    	log.info("start find all  ...");
    	log.info("count  ..." + this.serviceRepository.findAll().size());
    	
        return this.serviceRepository.findAll();
    }
    
    @GetMapping("/services/{id}")
    public Service loadService(@PathVariable(value = "id") String serviceId) {
    	log.info("start loading one  ...");
        Service service = serviceRepository.findById(Integer.valueOf(serviceId))
       .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id :: " + serviceId));
        
        log.info("Loading :" + service.getId());
        
        return service;
    }    
    
    @PostMapping("/services")
    public Service create(@RequestBody Service service) {
        return this.serviceRepository.save(service);
    } 
    
    @DeleteMapping("/services/{id}")
    public Map<String, Boolean> deleteService(@PathVariable(value = "id") String serviceId)
         throws ResourceNotFoundException {
    	log.info("start deleting " + serviceId);
        Service service = serviceRepository.findById(Integer.valueOf(serviceId))
       .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id :: " + serviceId));

        log.info("Deleting " + service.getId());
        serviceRepository.delete(service);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }    
    
    @PutMapping("/services/{id}")
    public Map<String, Boolean> updateService(@PathVariable(value = "id") String serviceId,
    		 @RequestBody Service data
    		)
         throws ResourceNotFoundException {
    	log.info("start updating " + serviceId);
        Service service = serviceRepository.findById(Integer.valueOf(serviceId))
       .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id :: " + serviceId));

        service.setServiceName(data.getServiceName());
        service.setComment(data.getComment());
        log.info("Update " + service.getId());
        serviceRepository.save(service);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }    
    
  

}
