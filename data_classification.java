/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment7;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aastha
 */
public class data_classification {
    
    public Double calc_prob(Double[] prob_pattern_given_class)
    {
        Double prob_pattern = 0.0;
        for(int i = 0; i < prob_pattern_given_class.length; i++)
        {
            prob_pattern = prob_pattern + (prob_pattern_given_class[i] * ((double)1/prob_pattern_given_class.length));
        }
        return prob_pattern;
    }
    
    public List Classify_histogram(List<List<Double>> examples, List<Double> classes, int attribute_count,int bin_count, List<histogram> list_histogram)
    {
        List<classification_output> output = new ArrayList<classification_output>();
        for(int i = 0; i < examples.size(); i++)
        {
            Double[] prob_pattern_given_class = new Double[classes.size()];
            for (int a = 0; a < classes.size(); a++)
            {
                prob_pattern_given_class[a] = 1.0;
            }
            for(int k = 0; k < classes.size(); k++)
            {
                for(int j = 0; j < attribute_count; j++)
                {
                    for(int l = 0; l < list_histogram.size(); l++)
                    {
                        if(Double.compare(classes.get(k), list_histogram.get(l).clas) == 0 && j == list_histogram.get(l).attr)
                        {
                            for(int m = 0; m < bin_count; m++)
                            {
                                if(((Double.compare(examples.get(i).get(j), list_histogram.get(l).bin[m][0]) > 0) || (Double.compare(examples.get(i).get(j), list_histogram.get(l).bin[m][0]) == 0)) && Double.compare(examples.get(i).get(j), list_histogram.get(l).bin[m][1]) < 0)
                                {
                                    prob_pattern_given_class[k]= prob_pattern_given_class[k] * list_histogram.get(l).bin[m][3];
                                    break;
                                }
                                else if(m == bin_count - 1 && Double.compare(examples.get(i).get(j), list_histogram.get(l).bin[bin_count - 1][1]) == 0)
                                {
                                    prob_pattern_given_class[k]= prob_pattern_given_class[k] * list_histogram.get(l).bin[m][3];
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            Double[][] prob_class_given_pattern = new Double[classes.size()][2];
            Double max = 0.0;
            Double max_class = 0.0;
            for(int b = 0; b < classes.size(); b++)
            {
                Double prob_pattern = calc_prob(prob_pattern_given_class);
                prob_class_given_pattern[b][0] = (prob_pattern_given_class[b] * ((double)1/classes.size()))/prob_pattern;
                prob_class_given_pattern[b][1] = classes.get(b);
                if(prob_class_given_pattern[b][0] > max)
                {
                    max = prob_class_given_pattern[b][0];
                    max_class = classes.get(b);
                }
            }
            Double acc = 0.0;
            if(Double.compare(max_class, examples.get(i).get(attribute_count)) == 0)
                acc = 1.0;
            classification_output out = new classification_output(i, max_class, max, examples.get(i).get(attribute_count), acc);
            output.add(out);
        }
        return output;
    }
    
    public List Classify_gaussian(List<List<Double>> examples, List<Double> classes, int attribute_count,List<gaussian> list_gaussian)
    {
        List<classification_output> output = new ArrayList<classification_output>();
        for(int i = 0; i < examples.size(); i++)
        {
            Double[] prob_pattern_given_class = new Double[classes.size()];
            for (int a = 0; a < classes.size(); a++)
            {
                prob_pattern_given_class[a] = 1.0;
            }
            for(int k = 0; k < classes.size(); k++)
            {
                for(int j = 0; j < attribute_count; j++)
                {
                    for(int l = 0; l < list_gaussian.size(); l++)
                    {
                        if(Double.compare(classes.get(k), list_gaussian.get(l).clas) == 0 && j == list_gaussian.get(l).attr)
                        {
                            Double prob_n = Math.exp(- (Math.pow(examples.get(i).get(j) - list_gaussian.get(l).mean, 2))/(2 * Math.pow(list_gaussian.get(l).sd, 2)))/(list_gaussian.get(l).sd * Math.sqrt(2 * Math.PI));
                            if(Double.compare(prob_n, Double.NaN) != 0)    
                                prob_pattern_given_class[k] = prob_pattern_given_class[k] * prob_n;
                            break;
                        }
                    }
                }
            }
            Double[][] prob_class_given_pattern = new Double[classes.size()][2];
            Double max = 0.0;
            Double max_class = 0.0;
            for(int b = 0; b < classes.size(); b++)
            {
                Double prob_pattern = calc_prob(prob_pattern_given_class);
                prob_class_given_pattern[b][0] = (prob_pattern_given_class[b] * ((double)1/classes.size()))/prob_pattern;
                prob_class_given_pattern[b][1] = classes.get(b);
                if(prob_class_given_pattern[b][0] > max)
                {
                    max = prob_class_given_pattern[b][0];
                    max_class = classes.get(b);
                }
            }
            Double acc = 0.0;
            if(Double.compare(max_class, examples.get(i).get(attribute_count)) == 0)
                acc = 1.0;
            classification_output out = new classification_output(i, max_class, max, examples.get(i).get(attribute_count), acc);
            output.add(out);
        }
        return output;
    }
    
    public List Classify_mixture(List<List<Double>> examples, List<Double> classes, int attribute_count, List<mixture_gaussian> list_gaussian, int gaussian_count)
    {
        List<classification_output> output = new ArrayList<classification_output>();
        for(int i = 0; i < examples.size(); i++)
        {
            Double[] prob_pattern_given_class = new Double[classes.size()];
            for (int a = 0; a < classes.size(); a++)
            {
                prob_pattern_given_class[a] = 1.0;
            }
            for(int k = 0; k < classes.size(); k++)
            {
                for(int j = 0; j < attribute_count; j++)
                {
                    for(int l = 0; l < list_gaussian.size(); l++)
                    {
                        if(Double.compare(classes.get(k), list_gaussian.get(l).clas) == 0 && j == list_gaussian.get(l).attr)
                        {
                            Double prob_n = 0.0, prob_m = 0.0; 
                            for(int c = 0; c < gaussian_count; c++)
                            {
                                prob_n = Math.exp(- (Math.pow(examples.get(i).get(j) - list_gaussian.get(l).gaussian[c][0], 2))/(2 * Math.pow(list_gaussian.get(l).gaussian[c][1], 2)))/(list_gaussian.get(l).gaussian[c][1] * Math.sqrt(2 * Math.PI));
                                prob_m = prob_m + (list_gaussian.get(l).gaussian[c][2] * prob_n );
                            }
                            if(Double.compare(prob_m, Double.NaN) != 0)    
                                prob_pattern_given_class[k] = prob_pattern_given_class[k] * prob_m;
                            break;
                        }
                    }
                }
            }
            Double[][] prob_class_given_pattern = new Double[classes.size()][2];
            Double max = 0.0;
            Double max_class = 0.0;
            for(int b = 0; b < classes.size(); b++)
            {
                Double prob_pattern = calc_prob(prob_pattern_given_class);
                prob_class_given_pattern[b][0] = (prob_pattern_given_class[b] * ((double)1/classes.size()))/prob_pattern;
                prob_class_given_pattern[b][1] = classes.get(b);
                if(prob_class_given_pattern[b][0] > max)
                {
                    max = prob_class_given_pattern[b][0];
                    max_class = classes.get(b);
                }
            }
            Double acc = 0.0;
            if(Double.compare(max_class, examples.get(i).get(attribute_count)) == 0)
                acc = 1.0;
            classification_output out = new classification_output(i, max_class, max, examples.get(i).get(attribute_count), acc);
            output.add(out);
        }
        return output;
    }  
}
