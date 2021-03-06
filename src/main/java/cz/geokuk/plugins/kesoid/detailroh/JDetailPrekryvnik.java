/**
 *
 */
package cz.geokuk.plugins.kesoid.detailroh;

import javax.swing.Timer;

import cz.geokuk.core.coord.*;
import cz.geokuk.core.coordinates.Mou;
import cz.geokuk.plugins.cesty.PridavaniBoduEvent;

/**
 * @author Martin Veverka
 *
 */
public class JDetailPrekryvnik extends JCoordPrekryvnik0 {
	private static final int SPOZDENI_ZOBRAZENI_DETAILU = 100;
	private static final int DETAIL_MOUMER = 17;
	private static final long serialVersionUID = -5996655830197513951L;
	private Poziceq poziceq;
	private boolean probihaPridavani;
	private Mou moucur;

	// Timer, který spožďuje vykreslení detailu,k dyž jen hejbeme myší se stisknutým controlem
	private Timer zpozdovaciTimer;

	/**
	 *
	 */
	public JDetailPrekryvnik() {}

	public void onEvent(final PoziceChangedEvent aEvent) {
		poziceq = aEvent.poziceq;
		nastav();
	}

	public void onEvent(final PridavaniBoduEvent aEvent) {
		probihaPridavani = aEvent.probihaPridavani;
		nastav();
	}

	public void onEvent(final ZmenaSouradnicMysiEvent aEvent) {
		moucur = aEvent.upravenaMys == null ? aEvent.moucur : aEvent.upravenaMys.getMou();
		nastav();
	}

	private void nastav() {
		if (probihaPridavani) {
			if (moucur != null) {
				nastavSeSpozdenim();
				repaint(); // musíme překreslit při změně středu
				setVisible(true);
			} else {
				nastavNaPozici();
			}
		} else {
			nastavNaPozici();
		}

	}

	private void nastavNaPozici() {
		if (poziceq == null || poziceq.isNoPosition()) {
			setVisible(false);
		} else {
			setSoord(getSoord().derive(DETAIL_MOUMER, poziceq.getWgs().toMou()));
			repaint(); // musíme překreslit při změně středu
			setVisible(true);
		}
	}

	private void nastavSeSpozdenim() {
		if (zpozdovaciTimer != null) {
			zpozdovaciTimer.stop();
		}
		zpozdovaciTimer = new Timer(SPOZDENI_ZOBRAZENI_DETAILU, e -> {
			zpozdovaciTimer = null;
			if (moucur != null) {
				setSoord(getSoord().derive(DETAIL_MOUMER, moucur));
			}
		});
		zpozdovaciTimer.setRepeats(false);
		zpozdovaciTimer.start();
	}
}
