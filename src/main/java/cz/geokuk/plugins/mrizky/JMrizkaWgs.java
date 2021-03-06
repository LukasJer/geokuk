/**
 *
 */
package cz.geokuk.plugins.mrizky;

import cz.geokuk.core.coordinates.Mou;
import cz.geokuk.core.coordinates.Wgs;

/**
 * @author Martin Veverka
 *
 */
public abstract class JMrizkaWgs extends JMrizka0 {

	protected static final double MINUTA = 1.0 / 60;
	protected static final double VTERINA = MINUTA / 60;

	private static final long serialVersionUID = 4558815639199835559L;

	/*
	 * (non-Javadoc)
	 *
	 * @see mrizka.JMrizka0#convertToMou(double, double)
	 */
	@Override
	public Mou convertToMou(final double aX, final double aY) {
		final Wgs wgs = new Wgs(aY, aX);
		final Mou mou = wgs.toMou();
		return mou;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mrizka.JMrizka0#convertToX(coordinates.Mou)
	 */
	@Override
	public double convertToX(final Mou aMou) {
		return aMou.toWgs().lon;
		// throw new RuntimeException("Spadlo to");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mrizka.JMrizka0#convertToY(coordinates.Mou)
	 */
	@Override
	public double convertToY(final Mou aMou) {
		return aMou.toWgs().lat;
	}

}
