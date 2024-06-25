package com.framework.selenium.api.base;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.selenium.api.design.Browser;
import com.framework.selenium.api.design.Element;
import com.framework.selenium.api.design.Locators;

import io.qameta.allure.Allure;

public class SeleniumBase extends DriverInstance implements Browser, Element {
	public Actions act;



	public String getAttribute(WebElement ele, String attributeValue) {
		String val = "";
		try {
			val = ele.getAttribute(attributeValue);
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return val;
	}

	public void takeSnap() throws FileNotFoundException{
		try {
			File src = getDriver().getScreenshotAs(OutputType.FILE);
			Allure.addAttachment("Snaphot:" ,new FileInputStream(src));
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void moveToElement(WebElement ele) {
		try {
			act = new Actions(getDriver());
			act.moveToElement(ele).perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void dragAndDrop(WebElement eleSoutce, WebElement eleTarget) {
		try {
			act = new Actions(getDriver());
			act.dragAndDrop(eleSoutce, eleTarget).perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void contextClick(WebElement ele) {
		try {
			act = new Actions(getDriver());
			act.contextClick(getWait().until(ExpectedConditions.elementToBeClickable(ele))).perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void hoverAndClick(WebElement ele) {
		try {
			act = new Actions(getDriver());
			act.moveToElement(getWait().until(ExpectedConditions.elementToBeClickable(ele))).pause(5000).click()
			.perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void doubleTap(WebElement ele) {
		try {
			act = new Actions(getDriver());
			act.click(getWait().until(ExpectedConditions.elementToBeClickable(ele))).click().perform();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void doubleClick(WebElement ele) {
		try {
			act = new Actions(getDriver());
			act.doubleClick(getWait().until(ExpectedConditions.elementToBeClickable(ele))).perform();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void waitForApperance(WebElement element) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void clickUsingJs(WebElement ele) {
		try {
			ele.isDisplayed(); // @FindBy return the proxy even if it does not exist !!
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}

		String text = "";
		try {
			try {
				getDriver().executeScript("arguments[0].click()", ele);
			} catch (Exception e) {
				boolean bFound = false;
				int totalTime = 0;
				while (!bFound && totalTime < 10000) {
					try {
						Thread.sleep(500);
						getDriver().executeScript("arguments[0].click()", ele);
						bFound = true;

					} catch (Exception e1) {
						bFound = false;
					}
					totalTime = totalTime + 500;
				}
				if (!bFound)
					getDriver().executeScript("arguments[0].click()", ele);
			}
		} catch (StaleElementReferenceException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void click(Locators locatorType, String value) {
		String text = "";
		WebElement ele = locateElement(locatorType, value);
		try {
			try {
				Thread.sleep(500);
				getWait().until(ExpectedConditions.elementToBeClickable(ele));
				text = ele.getText();
				if (ele.isEnabled()) {
					ele.click();
				} else {
					getDriver().executeScript("arguments[0].click()", ele);
				}
			} catch (Exception e) {
				boolean bFound = false;
				int totalTime = 0;
				while (!bFound && totalTime < 10000) {
					try {
						Thread.sleep(500);
						ele = locateElement(locatorType, value);
						ele.click();
						bFound = true;

					} catch (Exception e1) {
						bFound = false;
					}
					totalTime = totalTime + 500;
				}
				if (!bFound)
					ele.click();
			}
		} catch (StaleElementReferenceException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = ele.getText();
		try {
			getWait().until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		} catch (StaleElementReferenceException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void append(WebElement ele, String data) {
		try {
			String attribute = ele.getAttribute("value");
			if (attribute.length() > 1) {
				ele.sendKeys(data);
			} else {
				ele.sendKeys(data);
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void clear(WebElement ele) {
		try {
			ele.clear();
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Overloaded method used to clear the existing value and type the data with
	 * keys for tab or enter kind of
	 * 
	 * @param ele  - WebElement from the DOM
	 * @param data - Use to type and pass Keys as many needed
	 */
	public void clearAndType(WebElement ele, CharSequence... data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			ele.sendKeys(data);
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) { // retry - 1
			pause(500);
			try {
				ele.sendKeys(data);
			} catch (Exception e1) {
				System.out.println(e.getMessage());
			}
		}

	}

	@Override
	public void clearAndType(WebElement ele, String data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			ele.sendKeys("", "", data);
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) { // retry - 1
			pause(500);
			try {
				ele.sendKeys(data);
			} catch (Exception e1) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void typeAndTab(WebElement ele, String data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			ele.sendKeys("", "", data, Keys.TAB);
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void type(WebElement ele, String data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(ele));
			ele.sendKeys("", "", data);
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void typeAndEnter(WebElement ele, String data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			ele.sendKeys("", "", data, Keys.ENTER);
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public String getElementText(WebElement ele) {
		try {
			String text = ele.getText();
			return text;
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public String getBackgroundColor(WebElement ele) {
		String cssValue = null;
		try {
			cssValue = ele.getCssValue("color");
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cssValue;
	}

	@Override
	public String getTypedText(WebElement ele) {
		String attributeValue = null;
		try {
			attributeValue = ele.getAttribute("value");
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return attributeValue;
	}

	@Override
	public void selectDropDownUsingText(WebElement ele, String value) {
		try {
			Select sel = new Select(ele);
			sel.selectByVisibleText(value);
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void selectDropDownUsingIndex(WebElement ele, int index) {
		try {
			Select sel = new Select(ele);
			sel.selectByIndex(index);
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void selectDropDownUsingValue(WebElement ele, String value) {
		try {
			Select sel = new Select(ele);
			sel.selectByValue(value);
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		try {
			String text = ele.getText();
			if (text.contains(expectedText)) {
				return true;
			} else {
				System.out.println("doesn't equals ");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	@Override
	public boolean verifyPartialText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().contains(expectedText)) {
				return true;
			} else {
				System.out.println("The expected text doesn't contain the actual ");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).equals(value)) {
				return true;
			} else {
				System.out.println(" value does not contains the actual ");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).contains(value)) {
				System.out.println(" value contains the actual ");
			} else {
				System.out.println(" value does not contains the actual ");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public boolean verifyDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				return true;
			} else {
				System.out.println(" is not visible");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;

	}

	@Override
	public boolean verifyDisappeared(WebElement ele) {
		try {
			Boolean until = getWait().until(ExpectedConditions.invisibilityOf(ele));
			return until;
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;

	}

	@Override
	public boolean verifyEnabled(WebElement ele) {
		try {
			if (ele.isEnabled()) {
				return true;
			} else {
				System.out.println(" is not Enabled");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				return true;
			} else {
				System.out.println("not selected");
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
		return false;

	}

	@Override
	public void startApp(String url, boolean headless) {
		try {
			setDriver("chrome", headless);
			setWait();
			act = new Actions(getDriver());
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			getDriver().get(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void startApp(String browser, boolean headless, String url) {
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.silentOutput", "true");
				setDriver("chrome", headless);
			} else if (browser.equalsIgnoreCase("firefox")) {
				setDriver("firefox", headless);
			} else if (browser.equalsIgnoreCase("ie")) {
				setDriver("ie", false);
			} else if (browser.equalsIgnoreCase("edge")) {
				setDriver("edge", false);
			}
			setWait();
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
			getDriver().get(url);
		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public WebElement locateElement(Locators locatorType, String value) {
		try {
			switch (locatorType) {
			case CLASS_NAME:
				return getDriver().findElement(By.className(value));
			case CSS:
				return getDriver().findElement(By.cssSelector(value));
			case ID:
				return getDriver().findElement(By.id(value));
			case LINK_TEXT:
				return getDriver().findElement(By.linkText(value));
			case NAME:
				return getDriver().findElement(By.name(value));
			case PARTIAL_LINKTEXT:
				return getDriver().findElement(By.partialLinkText(value));
			case TAGNAME:
				return getDriver().findElement(By.tagName(value));
			case XPATH:
				return getDriver().findElement(By.xpath(value));
			default:
				System.err.println("Locator is not Valid");
				break;
			}
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public WebElement locateElement(String value) {
		try {
			WebElement findElementById = getDriver().findElement(By.id(value));
			return findElementById;
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<WebElement> locateElements(Locators type, String value) {
		try {
			switch (type) {
			case CLASS_NAME:
				return getDriver().findElements(By.className(value));
			case CSS:
				return getDriver().findElements(By.cssSelector(value));
			case ID:
				return getDriver().findElements(By.id(value));
			case LINK_TEXT:
				return getDriver().findElements(By.linkText(value));
			case NAME:
				return getDriver().findElements(By.name(value));
			case PARTIAL_LINKTEXT:
				return getDriver().findElements(By.partialLinkText(value));
			case TAGNAME:
				return getDriver().findElements(By.tagName(value));
			case XPATH:
				return getDriver().findElements(By.xpath(value));
			default:
				System.err.println("Locator is not Valid");
				break;
			}
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void switchToAlert() {
		try {
			getDriver().switchTo().alert();
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void acceptAlert() {
		String text = "";
		try {
			getWait().until(ExpectedConditions.alertIsPresent());
			Alert alert = getDriver().switchTo().alert();
			text = alert.getText();
			alert.accept();
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void dismissAlert() {
		String text = "";
		try {
			Alert alert = getDriver().switchTo().alert();
			text = alert.getText();
			alert.dismiss();
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public String getAlertText() {
		String text = "";
		try {
			Alert alert = getDriver().switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
		return text;
	}

	@Override
	public void typeAlert(String data) {
		try {
			getDriver().switchTo().alert().sendKeys(data);
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void switchToWindow(int index) {
		try {
			Set<String> allWindows = getDriver().getWindowHandles();
			List<String> allhandles = new ArrayList<String>(allWindows);
			getDriver().switchTo().window(allhandles.get(index));
		} catch (NoSuchWindowException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean switchToWindow(String title) {
		try {
			Set<String> allWindows = getDriver().getWindowHandles();
			for (String eachWindow : allWindows) {
				getDriver().switchTo().window(eachWindow);
				if (getDriver().getTitle().equals(title)) {
					break;
				}
			}
			return true;
		} catch (NoSuchWindowException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void switchToFrame(int index) {
		try {
			Thread.sleep(100);
			getDriver().switchTo().frame(index);
		} catch (NoSuchFrameException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void switchToFrame(WebElement ele) {
		try {
			getDriver().switchTo().frame(ele);
		} catch (NoSuchFrameException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void switchToFrameUsingXPath(String xpath) {
		try {
			getDriver().switchTo().frame(locateElement(Locators.XPATH, xpath));
		} catch (NoSuchFrameException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void switchToFrame(String idOrName) {
		try {
			getDriver().switchTo().frame(idOrName);
		} catch (NoSuchFrameException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void defaultContent() {
		try {
			getDriver().switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean verifyUrl(String url) {
		if (getDriver().getCurrentUrl().equals(url)) {
			return true;
		} else {
			System.out.println(" not matched");
		}
		return false;
	}

	@Override
	public boolean verifyTitle(String title) {
		if (getDriver().getTitle().equals(title)) {
			return true;
		} else {
			System.out.println("not matched");

		}
		return false;
	}


	@Override
	public void close() {
		try {
			getDriver().close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void quit() {
		try {
			getDriver().quit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void waitForDisapperance(WebElement element) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
			wait.until(ExpectedConditions.invisibilityOf(element));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void pause(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void chooseDate(WebElement ele, String data) {
		try {
			getDriver().executeScript("arguments[0].setAttribute('value', '" + data + "')", ele);
		} catch (ElementNotInteractableException e) {
			System.out.println(e.getMessage());
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void fileUpload(WebElement ele, String data) {
		try {
			hoverAndClick(ele);
			pause(2000);

			// Store the copied text in the clipboard
			StringSelection stringSelection = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

			// Paste it using Robot class
			Robot robot = new Robot();

			// Enter to confirm it is uploaded
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(5000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void fileUploadWithJs(WebElement ele, String data) {
		try {

			clickUsingJs(ele);
			;
			pause(2000);

			// Store the copied text in the clipboard
			StringSelection stringSelection = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

			// Paste it using Robot class
			Robot robot = new Robot();

			// Enter to confirm it is uploaded
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(5000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void executeTheScript(String js, WebElement ele) {
		getDriver().executeScript(js, ele);
	}


	@Override
	public void click(WebElement ele) {
		try {
			ele.isDisplayed(); // @FindBy return the proxy even if it does not exist !!
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String text = "";
		try {
			try {
				Thread.sleep(500);
				getWait().until(ExpectedConditions.elementToBeClickable(ele));
				text = ele.getText();
				if (ele.isEnabled()) {
					ele.click();
				} else {
					getDriver().executeScript("arguments[0].click()", ele);
				}
			} catch (Exception e) {
				boolean bFound = false;
				int totalTime = 0;
				while (!bFound && totalTime < 10000) {
					try {
						Thread.sleep(500);
						ele.click();
						bFound = true;

					} catch (Exception e1) {
						bFound = false;
					}
					totalTime = totalTime + 500;
				}
				if (!bFound)
					ele.click();
			}
		} catch (StaleElementReferenceException e) {
			System.err.println(e);
		} catch (WebDriverException e) {
			System.err.println(e);
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	public WebElement locateShadowElement(String value) {
		WebElement findElementByXpath;
		try {
			getShadowElement().setImplicitWait(5);
			findElementByXpath = getShadowElement().findElementByXPath(value);
			return findElementByXpath;
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<WebElement> locateShadowElements(String value) {

		try {
			getShadowElement().setImplicitWait(20);
			List<WebElement> findElementsByXpath = getShadowElement().findElementsByXPath(value);
			return findElementsByXpath;
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}


}
