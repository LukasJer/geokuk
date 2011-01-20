package cz.geokuk.core.coordinates;

public class Mou {

  public int xx;
  public int yy;

  public Mou() {}

  public Mou(int xx, int yy) {
    this.xx = xx;
    this.yy = yy;
  }

  public Mou(Mou mou) {
    xx = mou.xx;
    yy = mou.yy;
  }

  public Mou add(int dxx, int dyy) {
    return new Mou(xx + dxx, yy + dyy);
  }


  public Mou add(Moud moud) {
    return new Mou(xx + moud.dxx, yy + moud.dyy);
  }

  public Mou sub(Moud moud) {
    return new Mou(xx - moud.dxx, yy - moud.dyy);
  }

  public Moud sub(Mou mou) {
    return new Moud(xx - mou.xx, yy - mou.yy);
  }

  @Override
  public int hashCode() {
    return xx ^ yy;
  }


  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (! (obj instanceof Mou)) return false;
    Mou m = (Mou) obj;
    return xx == m.xx && yy == m.yy;
  }


  @Override
  public String toString() {
    return "[" + Integer.toHexString(xx)  + "," + Integer.toHexString(yy)  + "]";
  }

  public Utm toUtm() {
    double ux = (xx * 0.03125) - 3700000;
    double uy = (yy * 0.03125) + 1300000;
    return new Utm(ux, uy);
  }

  public Wgs toWgs() {
    return toUtm().toWgs();
  }
  
  @Override
  public Mou clone()  {
  	return new Mou(xx,yy);
  }

}
