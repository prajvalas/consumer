package com.example.demo.entities;

public class Quota {

    private int quota;

    public Quota(){
        
    }

    public Quota(int quota){
        super();
        this.quota = quota;
    }

	public int getQuota() {
		return this.quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

    @Override
    public String toString(){
        return "QuotaReset : " + getQuota();
    }
}