package com.example.demo.controllers;

import com.example.demo.entities.ExcessConsume;
import com.example.demo.entities.GetConsumed;
import com.example.demo.entities.Quota;
import com.example.demo.entities.UpdateConsumed;
import com.example.demo.services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
	
    @Autowired
    ConsumerService consumerService;

    @PostMapping("/consume")
	public ResponseEntity<Object> consume(@RequestBody UpdateConsumed object) {
        GetConsumed result = consumerService.addValue(object);
        if(result == null){
            ExcessConsume excess = consumedExcess(object);
            return new ResponseEntity<Object>(excess, HttpStatus.SERVICE_UNAVAILABLE);
            
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

    public ExcessConsume consumedExcess(UpdateConsumed object){
        return consumerService.consumedExcess(object);
    }

    @GetMapping("/consume")
    public GetConsumed consumeGet(){
        return consumerService.consumeGet();
    }

    @PostMapping("/reset")
    public GetConsumed reset(@RequestBody Quota object){
        return consumerService.reset(object);
    }

}