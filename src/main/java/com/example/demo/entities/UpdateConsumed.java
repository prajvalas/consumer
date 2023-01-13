package com.example.demo.entities;


public class UpdateConsumed {

    private int value;

    public UpdateConsumed(){
        
    }
    public UpdateConsumed(int value){
        super();
        this.value = value;
    }

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

    @Override
    public String toString(){
        return "PostConsume : " + getValue();
    }

}