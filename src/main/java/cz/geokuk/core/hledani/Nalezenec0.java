package cz.geokuk.core.hledani;

import cz.geokuk.core.coordinates.Wgs;

public abstract class Nalezenec0 {

	protected double vzdalenost;
	protected double azimut;

	/**
	 * @return the azimut
	 */
	public double getAzimut() {
		return azimut;
	}

	/**
	 * @return the vzdalenost
	 */
	public double getVzdalenost() {
		return vzdalenost;
	}

	public abstract Wgs getWgs();

	/**
	 * @param aAzimut
	 *            the azimut to set
	 */
	public void setAzimut(final double aAzimut) {
		azimut = aAzimut;
	}

	/**
	 * @param aVzdalenost
	 *            the vzdalenost to set
	 */
	public void setVzdalenost(final double aVzdalenost) {
		vzdalenost = aVzdalenost;
	}

}
