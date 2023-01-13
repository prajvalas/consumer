package com.example.demo.repositories;

import com.example.demo.entities.GetConsumed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumeRepo extends CrudRepository<GetConsumed, Integer>{

}