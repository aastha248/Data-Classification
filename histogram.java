/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment7;

/**
 *
 * @author Aastha
 */
public class histogram {
    
    Double clas;
    int attr;
    Double[][] bin;
  
    public histogram(Double classes, int attribute, int bin_count, Double S, Double L, Double G)
    {
        clas = classes;
        attr = attribute;
        bin = new Double[bin_count][4];
        for(int i = 0; i < bin_count; i++)
        {
            bin[i][0] = S;
            bin[i][1] = S+G;
            bin[i][2] = 0.0;
            bin[i][3] = 0.0;
            S = S+G;
        }
    }
}
