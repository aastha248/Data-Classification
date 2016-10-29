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
public class classification_output {
    
    int obj_ID;
    Double predicted_class;
    Double probability;
    Double true_class;
    Double accuracy;
    
    public classification_output(int id, Double p_class, Double prob, Double t_class, Double acc )
    {
        obj_ID = id;
        predicted_class = p_class;
        probability = prob;
        true_class = t_class;
        accuracy = acc;
    }
    
}
