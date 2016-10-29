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
public class mixture_gaussian {
    
    Double clas;
    int attr;
    Double[][] gaussian;
    
    public mixture_gaussian(Double classes, int attribute, Double[][] values, int gaussian_count)
    {
        clas = classes;
        attr = attribute;
        gaussian = new Double[gaussian_count][3];
        for(int i = 0; i < gaussian_count; i++)
        {
            gaussian[i][0] = values[i][0];
            gaussian[i][1] = values[i][1];
            gaussian[i][2] = values[i][2];
        }
    }
}
