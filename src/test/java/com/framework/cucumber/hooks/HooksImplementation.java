package com.framework.cucumber.hooks;

import java.io.FileNotFoundException;

import com.framework.cucumber.base.ProjectSpecificMethods;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;

public class HooksImplementation extends ProjectSpecificMethods{

	@Before
	public void preCondition() {
		startApp("https://login.salesforce.com/", false);
	}

	@After
	public void postCondition() {
		quit();
	}

	@AfterStep
	public void attachScreenshot() throws FileNotFoundException  {
		takeSnap();
	}






}
