/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
