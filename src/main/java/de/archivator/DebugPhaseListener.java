/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke
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
package de.archivator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * Ein DebugPhaseListener ist ein JSF PhaseListener,
 * der den Beginn und das Ende jeder Phase des JSF-Lebenszyklus'
 * auf der Konsole anzeigt.
 * 
 * @author burghard.britzke
 */
public class DebugPhaseListener implements PhaseListener {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Zeig das Ende einer Phase des JSF-Lebenszyklus'
	 * auf der Konsole an. Zusätzlich werden alle Messages
	 * angezeigt, die zum Ende der Phase in der JSF Message Queue
	 * gespeichert sind.
	 * 
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {
		System.out.println("after Phase: "+event.getPhaseId());
		FacesContext f= event.getFacesContext();
		List<FacesMessage> ml = f.getMessageList();
		for (FacesMessage m:ml){
			System.out.println("after Phase Message: "+m.getSummary()+": "+m.getDetail());
		}
	}

	/**
	 * Zeigt den Beginn einer Phase des JSF-Lebenszyklus'
	 * auf der Konsole an.
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
		System.out.println("before Phase: "+event.getPhaseId());
	}

	/**
	 * Zeigt an, dass der PhaseListener für jede Phase
	 * registriert werden soll.
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
