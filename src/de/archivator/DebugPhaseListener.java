
package de.archivator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author bubi
 *
 */
public class DebugPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("after Phase: "+event.getPhaseId());
		FacesContext f= event.getFacesContext();
		List<FacesMessage> ml = f.getMessageList();
		for (FacesMessage m:ml){
			System.out.println(m.getSummary()+": "+m.getDetail());
		}
	}

	/**
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("before Phase: "+event.getPhaseId());
	}

	/**
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
