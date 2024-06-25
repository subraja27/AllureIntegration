package com.framework.pages;

import com.framework.cucumber.base.ProjectSpecificMethods;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginPage extends ProjectSpecificMethods{
	
	@Given("Enter the username as {string}")
	public LoginPage enterUsername(String uName) {
		clearAndType(locateElement("username"), uName);
		return this;
	}
	
	@And("Enter the password as {string}")
	public LoginPage enterPassword(String pWord) {
		clearAndType(locateElement("password"), pWord);
		return this;
	}
	
	@When("Click on the login button")
	public WelcomePage clickLogin() {
		click(locateElement("Login"));
		return new WelcomePage();
	}

}
