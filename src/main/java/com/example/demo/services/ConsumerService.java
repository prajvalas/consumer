package com.example.demo.services;

import com.example.demo.entities.ExcessConsume;
import com.example.demo.entities.GetConsumed;
import com.example.demo.entities.Quota;
import com.example.demo.entities.UpdateConsumed;
import com.example.demo.repositories.ConsumeRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConsumerService {
 
	@Autowired
	ConsumeRepo consumeRepo;

    private final int START_QUOTA = 1000;
	
	public GetConsumed addValue(@NotNull UpdateConsumed consumed) {

        int curr_val = consumed.getValue(); 

        if(!consumeRepo.existsById(1)){
            GetConsumed getConsume = new GetConsumed(0, START_QUOTA);
            consumeRepo.save(getConsume);
        }

        GetConsumed previous = consumeRepo.findById(1).get();
        int remain = previous.getRemaining();
        int total = previous.getTotal();
        
        if(remain - curr_val < 0){
            return null;
        }
           
        GetConsumed result = new GetConsumed(total+curr_val, remain-curr_val);
        consumeRepo.save(result);
        return result;
	}

    public ExcessConsume consumedExcess(UpdateConsumed consumed) {

        GetConsumed previous = consumeRepo.findById(1).get();
        int curr_val = consumed.getValue();
        int remain = previous.getRemaining();
        int diff = remain - curr_val;
        return new ExcessConsume(Math.abs(diff));
    }

    public GetConsumed consumeGet(){

        if(!consumeRepo.existsById(1)){
            GetConsumed getConsume = new GetConsumed(0, START_QUOTA);
            consumeRepo.save(getConsume);
        }
        return consumeRepo.findById(1).get();
    }

    public GetConsumed reset(Quota body){
        
        GetConsumed getConsume = new GetConsumed(0, body.getQuota());
        consumeRepo.save(getConsume);
        return getConsume;
    }
	
}