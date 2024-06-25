package com.framework.testcases;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features",
				 glue = {"com.framework.pages","com.framework.cucumber.hooks"},
				 monochrome = true,
				 plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})

public class CucumberRunner extends AbstractTestNGCucumberTests{

}

