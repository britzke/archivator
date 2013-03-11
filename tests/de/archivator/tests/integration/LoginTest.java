package de.archivator.tests.integration;

import static org.junit.Assert.*;

import java.io.File;
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
 * Testet das Loginverhalten mit Hilfe von FireFox
 * @author Janine Naumann
 * @author Atilla Schulz
 *
 */
public class LoginTest {

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
	}

	/**
	 * Testet den erfolgreichen Redakteur-Login automatisch
	 */
	@Test
	public void testLoginErfolgreich() {
		try {
			// Enter the query string "Cheese"
			WebElement query = driver.findElement(By.name("j_id_q:password"));
			query.sendKeys("secret");

			WebElement query2 = driver.findElement(By.name("j_id_q:login"));
			query2.click();

			Thread.sleep(3000);

			// Wirft eine NoSuchElementException, falls das Element nicht
			// vorhanden ist
			driver.findElement(By.name("j_id_q:logout"));
			WebElement e = driver.findElement(By
					.className("ui-messages-info-summary"));
			assertEquals("Sie haben sich erfolgreich angemeldet!", e.getText());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Testet das Fehlschlagen des Redakteur-Logins automatisch
	 */
	@Test
	public void testLoginNichtErfolgreich() {
		try {
			// Enter the query string "Cheese"
			WebElement query = driver.findElement(By.name("j_id_q:password"));
			query.sendKeys("asdf");

			WebElement query2 = driver.findElement(By.name("j_id_q:login"));
			query2.click();

			Thread.sleep(3000);

			// Wirft eine NoSuchElementException, falls das Element nicht
			// vorhanden ist
			driver.findElement(By.name("j_id_q:login"));
			WebElement e = driver.findElement(By
					.className("ui-messages-info-summary"));
			assertEquals("Falsches Kennwort!", e.getText());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Testet den Redakteur-Logout
	 */
	@Test
	public void testLogout() {
		testLoginErfolgreich();
		try {
			WebElement e = driver.findElement(By.name("j_id_q:logout"));
			e.click();
			Thread.sleep(3000);

			driver.findElement(By.name("j_id_q:login"));

			e = driver.findElement(By.className("ui-messages-info-summary"));
			assertEquals("Sie haben sich erfolgreich abgemeldet!", e.getText());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Schließt den Test-Driver
	 */
	@After
	public void onTearDown() {
		driver.close();
	}

}
