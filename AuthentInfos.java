package gnz.julaa.kanou;
public class AuthentInfos {
    public String nom_util;
    public String tel;
    public String pays;

    public String getnom_util() {
        return nom_util;
    }

    public String getTel() {
        return tel;
    }

    public String getPays() {
        return pays;
    }

    public AuthentInfos(String nom_util, String tel, String pays) {
        this.nom_util = nom_util;
        this.tel = tel;
        this.pays = pays;
    }
}
