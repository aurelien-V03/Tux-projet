package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 *
 * @author valleta
 */
public class Partie {

    private String date;
    private String mot;
    private int niveau;

    // le % de lettres trouvees a la fin de la partie
    private int trouve;
    // le temps en secondes qu'a duré la partie
    private float temps;

    public Partie(String date, String mot, int niveau) {
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
    }

    // reinitialisation d'une partie deja faite issue du XML
    public Partie(Element partieElt) {
        // String date = partieElt.getAttribute("date");

        Element temps = (Element) partieElt.getElementsByTagName("temps").item(0);
        Element mot = (Element) partieElt.getElementsByTagName("mot").item(0);
        Node date = partieElt.getAttributeNode("date");
        Node niveau = mot.getAttributeNode("niveau");

        String tempsValue = temps.getTextContent();
        String motValue = mot.getTextContent();
        String dateValue = date.getTextContent();
        int niveauValue = Integer.parseInt(niveau.getTextContent());

        this.date = dateValue;
        this.niveau = niveauValue;
        this.temps = Float.parseFloat(tempsValue);
        this.mot = motValue;
        
        System.out.println(this);

    }

    // cree le bloc XML a partie de doc
    public Element getPartie(Document doc) {
        // Creer un element <partie> que l'on inserera dans <parties>
       Element partieElt = doc.createElement("partie");
       partieElt.setAttribute("date", getDate());
       partieElt.setAttribute("trouve",String.valueOf(getPourcentageTrouve()));
       
       // element <temps>
       Element temps = doc.createElement("temps");
       Text textTemps = doc.createTextNode( String.valueOf(getTemps()));
       temps.appendChild(textTemps);
       
      // element <mot>
       Element mot = doc.createElement("mot");
       mot.setAttribute("niveau", String.valueOf(getNiveau()));
       Text motText = doc.createTextNode( getMot());
       
       // <partie> += <mot> <temps>
       mot.appendChild(motText);
       temps.appendChild(textTemps);
       
       partieElt.appendChild(temps);
       partieElt.appendChild(mot);
       
       return partieElt;
    }

    public void setTrouve(int LettresRestantes) {
        int tailleMot = mot.toCharArray().length;
        int lettresTrouvees = tailleMot - LettresRestantes;
        
        System.out.print("taille du mot = " + tailleMot + "\n");
        System.out.print("lettre trouve mot = " + lettresTrouvees + "\n");
        System.out.print("pourcentage du mot = " + (lettresTrouvees / tailleMot)*100 + "\n");

    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public int getNiveau() {
        return niveau;
    }
    
    public int getPourcentageTrouve(){
        return this.trouve;
    }

    public String getMot() {
        return this.mot;
    }
    
    public float getTemps(){
        return this.temps;
    }

    public String toString() {
        return "Partie : [Mot = " + this.mot + "] [Temps = " + temps + "]  [niveau  = " + this.niveau + "] [date = " + this.date + "] [% =  " + this.trouve + "]";
    }

    public String getDate() {
        return date;
    }
    
   
}