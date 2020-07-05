package gnz.julaa.kanou;

/**
 * Created by a11 on 26/11/2017.
 */

public class ButteurInfos {
    String nom;
    String but;
    String eq;

    public void setProtrait(String protrait) {
        this.protrait = protrait;
    }

    public String getProtrait() {

        return protrait;
    }

    String protrait;

    public String getNom() {
        return nom;
    }

    public String getEq() {
        return eq;
    }

    public String getBut() {
        return but;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public void setBut(String but) {
        this.but = but;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
