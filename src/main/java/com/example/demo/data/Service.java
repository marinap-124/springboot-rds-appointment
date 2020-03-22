package com.example.demo.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Entity
@Table(name = "service")
public class Service  implements Serializable{
	static final long serialVersionUID = 0l;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column  
    private String serviceName;
    
    @Column
    private String comment;     
}
