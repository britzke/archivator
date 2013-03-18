package de.archivator.tests.integration;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Testet das Loginverhalten mit Hilfe von FireFox
 * 
 * @author Janine Naumann
 * @author Atilla Schulz
 * 
 */
public class LoginTest {

	FirefoxProfile profile;
	WebDriver driver;

	/**
	 * Die Umgebungsvariable PATH muss auf das Firefox-Verzeichnis verwiesen
	 * werden. Erstellt den Firefox-Webdriver, um die gewünschte Seite
	 * aufzurufen. Wartet nach dem aufruf 3sec auf den DOM-Tree
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		profile = new FirefoxProfile();
		driver = new FirefoxDriver();
		driver.get("http://localhost:8080/archivator/faces/index.xhtml");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * Testet den erfolgreichen Redakteur-Login automatisch
	 */
	@Test
	public void testLoginErfolgreich() {
		// Enter the query string "Cheese"
		WebElement query = driver.findElement(By
				.xpath("//input[@type='password']"));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		query.sendKeys("secret");

		WebElement query2 = driver.findElement(By
				.xpath("//button[contains(@id, 'login')]"));
		query2.click();

		// Wirft eine NoSuchElementException, falls das Element nicht
		// vorhanden ist
		try {
			driver.findElement(By.xpath("//button[contains(@name, 'logout')]"));
		} catch (NoSuchElementException e) {
			fail("Es wurde kein Logout-Button gefunden. Der Login war nicht erfolgreich.");
		}

		WebElement e = driver.findElement(By
				.className("ui-messages-info-summary"));
		assertEquals("Sie haben sich erfolgreich angemeldet!", e.getText());
	}

	/**
	 * Testet das Fehlschlagen des Redakteur-Logins automatisch
	 */
	@Test
	public void testLoginNichtErfolgreich() {

		// Enter the query string "Cheese"
		WebElement query = driver.findElement(By
				.xpath("//input[@type='password']"));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		query.sendKeys("asdf");

		WebElement query2 = driver.findElement(By
				.xpath("//button[contains(@id, 'login')]"));
		query2.click();

		// Wirft eine NoSuchElementException, falls das Element nicht
		// vorhanden ist
		try {
			driver.findElement(By.xpath("//button[contains(@id, 'login')]"));
		} catch (NoSuchElementException e) {
			fail("Es wurde kein Login-Button gefunden. Der Logout war nicht erfolgreich.");
		}
		WebElement e = driver.findElement(By
				.className("ui-messages-info-summary"));
		assertEquals("Falsches Kennwort!", e.getText());

	}

	/**
	 * Testet den Redakteur-Logout
	 */
	@Test
	public void testLogout() {
		testLoginErfolgreich();

		WebElement e = driver.findElement(By
				.xpath("//button[contains(@id, 'logout')]"));
		e.click();

		driver.findElement(By.xpath("//button[contains(@id, 'login')]"));

		e = driver.findElement(By.className("ui-messages-info-summary"));
		assertEquals("Sie haben sich erfolgreich abgemeldet!", e.getText());

	}

	/**
	 * Schließt den Test-Driver
	 */
	@After
	public void onTearDown() {
		driver.close();
	}

}
