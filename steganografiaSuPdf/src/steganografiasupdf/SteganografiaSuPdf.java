/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.embedding.AggiungiSchermataEmbedding;
import steganografiasupdf.embedding.EsploraRisorseEmbedding;
import steganografiasupdf.embedding.InserisciMessaggio;
import steganografiasupdf.embedding.LeggiMessaggio;
import steganografiasupdf.embedding.LeggiPasswordEmbedding;
import steganografiasupdf.extraction.AggiungiSchermataEstrazione;
import steganografiasupdf.extraction.EsploraRisorseEstrazione;
import steganografiasupdf.extraction.EstraiMessaggio;

/**
 *
 * @author Rocchina
 */
public class SteganografiaSuPDF {

    /**
     * @param args the command line arguments
     */
    
    private final SchermataPrincipale sp;
    
    private final Log logger = LogFactory.getLog(SteganografiaSuPDF.class);
    
    //mappa oggetti:
    private final Map<String, Object> mappaObject = new HashMap<>();
    
    //Azioni:
    private Esci esci;
    private Info info;
    private AggiungiSchermataEmbedding se;
    private LeggiMessaggio lm;
    private LeggiPasswordEmbedding lpe;
    private EsploraRisorseEmbedding eRembedding;
    private InserisciMessaggio im;
    private AggiungiSchermataEstrazione sEstrazione;
    private EsploraRisorseEstrazione eRestrazione;
    private EstraiMessaggio eMessaggio;
    
    public SteganografiaSuPDF(){
        inizializzaAzioni();
        this.sp = new SchermataPrincipale(this);
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SteganografiaSuPDF steganografia = new SteganografiaSuPDF();
            }
        });
    }

    private void inizializzaAzioni() {
        logger.info("In inizializzaAzioni()");
        this.esci = new Esci(this);
        this.info = new Info(this);
        this.se = new AggiungiSchermataEmbedding(this);
        this.lm = new LeggiMessaggio(this);
        this.lpe = new LeggiPasswordEmbedding(this);
        this.eRembedding = new EsploraRisorseEmbedding(this);
        this.im = new InserisciMessaggio(this);
        this.sEstrazione = new AggiungiSchermataEstrazione(this);
        this.eRestrazione = new EsploraRisorseEstrazione(this);
        this.eMessaggio = new EstraiMessaggio(this);
    }
    
    public void addObject(String key, Object obj){
        this.mappaObject.put(key, obj);
    }
    
    public Object getObject(String key){
        return this.mappaObject.get(key);
    }

    /**
     * @return the esci
     */
    public Esci getEsci() {
        return esci;
    }

    /**
     * @return the sp
     */
    public SchermataPrincipale getSp() {
        return sp;
    }

    /**
     * @return the info
     */
    public Info getInfo() {
        return info;
    }

    /**
     * @return the se
     */
    public AggiungiSchermataEmbedding getSe() {
        return se;
    }

    /**
     * @return the lm
     */
    public LeggiMessaggio getLm() {
        return lm;
    }

    /**
     * @return the lpe
     */
    public LeggiPasswordEmbedding getLpe() {
        return lpe;
    }

    /**
     * @return the eRembedding
     */
    public EsploraRisorseEmbedding geteRembedding() {
        return eRembedding;
    }

    /**
     * @return the im
     */
    public InserisciMessaggio getIm() {
        return im;
    }

    /**
     * @return the sEstrazione
     */
    public AggiungiSchermataEstrazione getsEstrazione() {
        return sEstrazione;
    }

    /**
     * @return the eRestrazione
     */
    public EsploraRisorseEstrazione geteRestrazione() {
        return eRestrazione;
    }

    /**
     * @return the eMessaggio
     */
    public EstraiMessaggio geteMessaggio() {
        return eMessaggio;
    }

    public void removeObject(String key) {
        this.mappaObject.remove(key);
    }
}