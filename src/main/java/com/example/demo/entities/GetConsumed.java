package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GetConsumed {

    @Id
    private int id;
    private int total;
    private int remaining;

    public GetConsumed(){
        
    }
    public GetConsumed(int total, int remaining){
        super();
        this.total = total;
        this.remaining = remaining;
        this.setID(1);
    }

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

    public int getRemaining() {
		return this.remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
    
    @JsonIgnore
    public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}

}