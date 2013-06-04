/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  e0_hansen, burghard.britzke
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.archivator.tests.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.*;


import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.archivator.beans.LoginBean;

/**
 * Testet die Funktionen der LoginBean.
 * 
 * @author e0_hansen
 * @author burghard.britzke
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { FacesContext.class })
public class LoginBeanTest {

	private LoginBean proband;
	private FacesContext facesContext;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		proband = new LoginBean();
		facesContext = org.mockito.Mockito.mock(FacesContext.class);
		mockStatic(FacesContext.class);
		org.mockito.Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
	}

	/**
	 * Test method for {@link de.archivator.beans.LoginBean#login()}.
	 * 
	 * @throws Exception
	 *             Z.B. NoSuchFieldException, wenn die Eigenschaft password
	 *             nicht vorhanden ist.
	 */
	@Test
	public void testLoginCorrect() throws Exception {
		proband.setPassword("secret");
				
		proband.login(new ActionEvent(new UICommand()));

		assertTrue(proband.isAngemeldet());
		verify(facesContext).addMessage(Mockito.anyString(), (FacesMessage)Mockito.anyObject());
	}

	/**
	 * Test method for {@link de.archivator.beans.LoginBean#login()}.
	 */
	@Test
	public void testLoginFail() {
		proband.setPassword("public"); // falsches Kennwort
		
		proband.login(new ActionEvent(new UICommand()));
		
		assertFalse(proband.isAngemeldet());	
	}

	/**
	 * Test method for {@link de.archivator.beans.LoginBean#logout()}.
	 */
	@Test
	public void testLogout() {
		proband.setAngemeldet(true);
		
		proband.logout(new ActionEvent(new UICommand()));
		
		assertFalse(proband.isAngemeldet());
	}
}
