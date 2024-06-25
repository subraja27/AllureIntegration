package com.framework.pages;

import com.framework.cucumber.base.ProjectSpecificMethods;

import io.cucumber.java.en.Then;

public class WelcomePage extends ProjectSpecificMethods{
	
	@Then("Welcomepage should be displayed")
	public WelcomePage verifyHomePage() {
		verifyTitle("Home | Salesforce");
        return this;
        
	}

}
