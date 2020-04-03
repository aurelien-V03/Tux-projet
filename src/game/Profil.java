/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Profil {

    public Document _doc;

    private String nom;
    private ArrayList<Partie> parties;
    private String avatar;
    private String dateNaissance;

    // Initialise un profil a partir d'un nom et de la date de naissance 
    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.parties = new ArrayList<Partie>();

    }

    // Cree un DOM à partir d'un fichier XML
    public Profil(String nomFichier) {
         this.parties = new ArrayList<Partie>();
        _doc = fromXML(nomFichier);
        // parsing à compléter
        // ?!#?!
    }

    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // ajoute a la liste des partie un nouvelle partie
    public void ajouterPartie(Partie p) {
        this.parties.add(p);
    }

    public int getDernierNiveau() {
        int dernierNiveau = 0;
        // récupère la liste des parties
        NodeList listeParties = _doc.getElementsByTagName("partie");
        System.out.println("Nombre de partie = " + listeParties.getLength());
        Element lastPartie = (Element) listeParties.item(listeParties.getLength() - 1);
        Node mot = lastPartie.getElementsByTagName("mot").item(0);
        dernierNiveau = Integer.parseInt(mot.getAttributes().getNamedItem("niveau").getNodeValue());
        return dernierNiveau;
    }

    public String toString() {
        return "[Profil = " + this.nom + this.dateNaissance + this.avatar + "]";
    }

    // sauvegarde le document DOM dans un fichier XML
    public void sauvegarder(String filename) {
        System.out.println("Sauvegarde de la partie");
        this.toXML(filename);
        
        
        
    }

    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }

    // retourne vrai si le fichier nom.xml existe 
    // et charge le profil 
    public boolean charge(String nom) {
        File f = new File("src/XML/" + nom + ".xml");
        if (f.exists()) {
            
            // On recupere les node correspondant a aux attributs
            this._doc = fromXML("src/XML/" + nom + ".xml");
            Element name = (Element) this._doc.getElementsByTagName("nom").item(0);
            Element avatar = (Element) this._doc.getElementsByTagName("avatar").item(0);
            Element anniversaire = (Element) this._doc.getElementsByTagName("anniversaire").item(0);          
            NodeList listeParties = this._doc.getElementsByTagName("partie");
            
            String nameValue = name.getTextContent();
            String avatarValue = avatar.getTextContent();
            String anniversaireValue = anniversaire.getTextContent();

            this.avatar = avatarValue;
            this.nom = nameValue;
            this.dateNaissance = anniversaireValue;
            
            // On ajoute chaque partie
            for(int i = 0 ;i <listeParties.getLength(); i++)
            {
                Element partieNode = (Element)listeParties.item(i);
                Partie p = new Partie(partieNode);
                this.parties.add(p);
            }
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Partie> getParties() {
        return parties;
    }
    

}
