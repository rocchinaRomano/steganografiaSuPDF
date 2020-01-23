/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf;

import java.util.HashMap;
import java.util.Map;
import steganografiasupdf.embedding.ApriEmbedding;
import steganografiasupdf.embedding.InserisciMessaggio;
import steganografiasupdf.embedding.MostraPannelloEmbedding;
import steganografiasupdf.embedding.Ok;
import steganografiasupdf.estrazione.ApriEstrazione;
import steganografiasupdf.estrazione.EstraiMessaggio;
import steganografiasupdf.estrazione.MostraPannelloEstrazione;

/**
 *
 * @author Rocchina
 */
public class SteganografiaSuPdf {

    private final FramePrincipale frame;
    private Esci esci;
    private Info info;
    private MostraPannelloEmbedding mostra;
    private Ok ok;
    private ApriEmbedding apri;
    private InserisciMessaggio inserisci;
    private MostraPannelloEstrazione mostraEstrazione;
    private ApriEstrazione apriEstrazione;
    private EstraiMessaggio estraiMess;
    private final Map<String, Object> mappa = new HashMap<>();
    
    public SteganografiaSuPdf(){
        inizializzaAzioni();
        this.frame = new FramePrincipale(this);
    };
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //richiamare l'evento per aprire la finestra principale:
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SteganografiaSuPdf steganografia = new SteganografiaSuPdf();
            }
        });
    }

    private void inizializzaAzioni() {
        this.esci = new Esci(this);
        this.info = new Info(this);
        this.mostra = new MostraPannelloEmbedding(this);
        this.ok = new Ok(this);
        this.apri = new ApriEmbedding(this);
        this.inserisci = new InserisciMessaggio(this);
        this.mostraEstrazione = new MostraPannelloEstrazione(this);
        this.apriEstrazione = new ApriEstrazione(this);
        this.estraiMess = new EstraiMessaggio(this);
    }

    /**
     * @return the frame
     */
    public FramePrincipale getFrame() {
        return frame;
    }

    /**
     * @return the esci
     */
    public Esci getEsci() {
        return esci;
    }

    /**
     * @return the info
     */
    public Info getInfo() {
        return info;
    }

    /**
     * @return the mostra
     */
    public MostraPannelloEmbedding getMostra() {
        return mostra;
    }

    /**
     * @return the ok
     */
    public Ok getOk() {
        return ok;
    }

    /**
     * @return the apri
     */
    public ApriEmbedding getApriEmbedding() {
        return apri;
    }

    /**
     * @return the inserisci
     */
    public InserisciMessaggio getInserisci() {
        return inserisci;
    }
    
    public Object getObject(String chiave){
        return mappa.get(chiave);
    }
    
    public void addObject(String chiave, Object obj){
        mappa.put(chiave, obj);
    }
    
    public int getSize(){
        return mappa.size();
    }

    /**
     * @return the mostraEstrazione
     */
    public MostraPannelloEstrazione getMostraEstrazione() {
        return mostraEstrazione;
    }

    /**
     * @return the apriEstrazione
     */
    public ApriEstrazione getApriEstrazione() {
        return apriEstrazione;
    }

    /**
     * @return the estraiMess
     */
    public EstraiMessaggio getEstraiMess() {
        return estraiMess;
    }
}