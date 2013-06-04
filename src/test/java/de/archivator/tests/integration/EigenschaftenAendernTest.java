package de.archivator.tests.integration;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Testet das ändern und erfolgreiche Speichern der Eigenschaften einer Archivalie,
 * nach Testfall 7 des Testplans.
 * Wird noch Berarbeitet.
 * @author Christopher Hansen
 *
 */
public class EigenschaftenAendernTest {
	FirefoxProfile profile;
	WebDriver driver;
	
	/**
	 * Der Firefoxpfad muss gegebenenfalls angepasst werden
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		profile = new FirefoxProfile();
		driver = new FirefoxDriver(new FirefoxBinary(new File(
				"D:/Programme/Mozilla Firefox/firefox.exe")), profile);
		driver.get("http://localhost:8080/archivator/faces/index.xhtml");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement query = driver.findElement(By
				.xpath("//input[@type='password']"));
		query.sendKeys("secret");
		WebElement query2 = driver.findElement(By
				.xpath("//button[contains(@id, 'login')]"));
		query2.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement query3 = driver.findElement(By
				.xpath("//input[@type='text']"));
		query3.sendKeys("*");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement query4 = driver.findElement(By
				.xpath("//button[contains(@id, 'j_id_h')]"));
		query4.click();		
		
	}
	
	/**
	 * 
	 */
	@Test
	public void	SchubfachTest(){
		
	}
}
