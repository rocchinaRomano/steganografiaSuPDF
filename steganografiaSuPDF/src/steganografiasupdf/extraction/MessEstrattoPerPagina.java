/*
 *  Steganografia su PDF is a software developed for embedding and extracting 
 *  a secret message within PDF files.
 *  This software is property of Rocchina Romano.
 *  mail: rocchina.romano@yahoo.it
 *
 *	Copyright Rocchina Romano 2020
 *
 *  class MessEstrattoPerPagina
 */
package steganografiasupdf.extraction;

/**
 *
 * @author Rocchina
 */
public class MessEstrattoPerPagina {
    
    private final int numPagina;
    private final String messEstratto;
    
    public MessEstrattoPerPagina(int numPagina, String messEstratto){
        this.numPagina = numPagina;
        this.messEstratto = messEstratto;
        
    }

    /**
     * @return the numPagina
     */
    public int getNumPagina() {
        return numPagina;
    }

    /**
     * @return the messEstratto
     */
    public String getMessEstratto() {
        return messEstratto;
    }
    
}
