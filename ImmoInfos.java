package gnz.julaa.kanou.imobillier;

/**
 * Created by a11 on 22/06/2017.
 */

public class ImmoInfos {
    String image0;
    String detail;
    String auteur;
    String dat;
    String id_pub;
    String sup_path;
    String tel;
    String pay;
    String rep;
    String igm_name;
    String dernier;

    public String getDernier() {
        return dernier;
    }

    public String getIgm_name() {
        return igm_name;
    }

    public String getPay() {
        return pay;
    }

    public String getRep() {
        return rep;
    }

    public String getId_pub() {
        return id_pub;
    }

    public String getTel() {
        return tel;
    }

    public String getDetail() {

        return detail;
    }

    public String getSup_path() {

        return sup_path;
    }

    public String getAuteur() {
        return auteur;
    }
    public String getDat() {
        return dat;
    }
    public String getImage0() {
        return image0;
    }
    public ImmoInfos() {
    }

    public ImmoInfos(String image0, String detail, String auteur, String dat,String id_pub,String sup_path,String tel, String pay,String rep, String igm_name) {
        this.image0 = image0;
        this.detail = detail;
        this.auteur = auteur;
        this.dat = dat;
        this.id_pub=id_pub;
        this.sup_path=sup_path;
        this.tel=tel;
        this.pay=pay;
        this.rep=rep;
        this.igm_name=igm_name;
    }

    public ImmoInfos(String detail, String auteur, String dat,String id_pub, String sup_path,String tel,String pay,String rep) {
        this.detail = detail;
        this.auteur = auteur;
        this.dat = dat;
        this.id_pub=id_pub;
        this.sup_path=sup_path;
        this.tel=tel;
        this.pay=pay;
        this.rep=rep;
    }
    /*
public String getQuartier(){
        return quartier;

    }
    public String getDetail(){
        return prix;
    }
    public String getMotif(){
        return motif;
    }


    public String getDat() {
        return dat;
    }

    public String getEtat() {
        return etat;
    }

   public String getTel(){
        return tel;
    }
    public String getObjet(){
        return objet;
    }

    public String getDevise() {
        return devise;
    }

    public String getUs_id() {
        return ville;
    }


    public String getDate() {
        return dat;
    }

    public ImmoInfos(String quartier, String motif, String tel, String objet, String image0, String etat
            , String adresse, String ville, String dat, String prix, String id, String auteur) {
        if (quartier.trim().equals("")){
            quartier="Non fourni";
        }
        if (tel.trim().equals(""))
        {
            tel="Non fourni";
        }
        if (ville.trim().equals("")){
            ville="Non fourni";
        }
        if (adresse.trim().equals("")){
            adresse="Non fourni";
        }
        if (auteur.trim().equals("")){
            auteur="Non fourni";
        }
        this.quartier = quartier;
        this.motif = motif;
        this.tel = tel;
        this.objet = objet;
        this.image0 = image0;
        this.etat = etat;
        this.devise = adresse;
        this.ville = ville;
        this.dat = dat;
        this.prix = prix;
        this.id=id;
        this.auteur=auteur;
    }
 */






}

