/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment7;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aastha
 */
public class train_data {
    
    public List get_list(List<List<Double>> examples, int i, Double classes, int attribute_count)
    {
        List<Double> attribute_list = new ArrayList<Double>();
        for(int k = 0; k < examples.size(); k++)
        {
            if(Double.compare(examples.get(k).get(attribute_count), classes) == 0)
                attribute_list.add(examples.get(k).get(i));
        }
        return attribute_list;
    }
    
    public List create_histogram(List<List<Double>> examples, List<Double> classes, int bin_count, int attribute_count)
    {
        java.util.Collections.sort(classes);
        List<histogram> list_histogram = new ArrayList<histogram>();
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        for(int j = 0; j < classes.size(); j++)
        {
            for(int i = 0; i < attribute_count; i++)
            {
                 List<Double> attribute_list = get_list(examples, i, classes.get(j), attribute_count);
                java.util.Collections.sort(attribute_list);
                Double S = attribute_list.get(0);
                Double L = attribute_list.get(attribute_list.size() - 1);
                Double G = (L-S)/bin_count;
                histogram htm = new histogram(classes.get(j), i, bin_count, S, L, G);
                for(int k = 0; k < attribute_list.size(); k++)
                {
                    if(Double.compare(attribute_list.get(k), L) == 0)
                    {
                        htm.bin[bin_count - 1][2]++;
                        continue;
                    }
                    for(int l = 0; l < bin_count; l++)
                    {
                        if(((Double.compare(attribute_list.get(k), htm.bin[l][0]) > 0) || (Double.compare(attribute_list.get(k), htm.bin[l][0]) == 0)) && Double.compare(attribute_list.get(k), htm.bin[l][1]) < 0)
                            htm.bin[l][2]++;
                    }
                }
                for(int l = 0; l < bin_count; l++)
                {
                    htm.bin[l][3] = htm.bin[l][2]/attribute_list.size();
                }
                list_histogram.add(htm);
            }
        }
        return list_histogram;
    }
    
    public double calculate_mean(List<Double> attribute_list)
    {
        double mean = 0, sum = 0;
        for(int i = 0; i < attribute_list.size(); i++)
        {
            sum = sum + attribute_list.get(i);
        }
        mean = sum/attribute_list.size();
        return mean;
    }
    
    public double calculate_sd(List<Double> attribute_list, double mean)
    {
        double sd = 0;
        List<Double> list_temp = new ArrayList<Double>();
        for(int i = 0; i < attribute_list.size(); i++)
        {
            Double temp = Math.pow(attribute_list.get(i) - mean, 2);
            list_temp.add(temp);
        }
        double temp_mean = calculate_mean(list_temp);
        sd = Math.sqrt(temp_mean);
        return sd;
    }
    
    public List create_gaussian(List<List<Double>> examples, List<Double> classes, int attribute_count)
    {
        java.util.Collections.sort(classes);
        List<gaussian> list_gaussian = new ArrayList<gaussian>();
        for(int j = 0; j < classes.size(); j++)
        {
            for(int i = 0; i < attribute_count; i++)
            {
                 List<Double> attribute_list = get_list(examples, i, classes.get(j), attribute_count);
                java.util.Collections.sort(attribute_list);
                double mean = calculate_mean(attribute_list);
                double sd = calculate_sd(attribute_list, mean);
                gaussian gsn = new gaussian(classes.get(j), i , (Double) mean, (Double) sd);
                list_gaussian.add(gsn);
            }
        }
        return list_gaussian;
    }
    
    public List calc_pij(List<Double> attribute_list, Double[][] values, int gaussian_count)
    {
        List<List<Double>> pij = new ArrayList<List<Double>>();
        for(int i = 0; i < gaussian_count; i++)
        {
            List<Double> row = new ArrayList<Double>();
            for(int j = 0; j < attribute_list.size(); j++)
                row.add(0.0);
            pij.add(row);
        }
        for(int i = 0; i < attribute_list.size(); i++ )
        {
            for(int j = 0; j < gaussian_count; j++)
            {
                Double temp = Math.exp(- (Math.pow(attribute_list.get(i) - values[j][0], 2))/(2 * Math.pow(values[j][1], 2)))/(values[j][1] * Math.sqrt(2 * Math.PI));
                if(Double.compare(temp, Double.NaN) != 0)    
                    values[j][3] = temp;
                else 
                    values[j][3] = 0.0;
            }
            Double p_x = 0.0;
            for(int j = 0; j < gaussian_count; j++)
            {
                p_x = p_x + (values[j][2] * values[j][3]);
            }
            for(int j = 0; j < gaussian_count; j++)
            {
                pij.get(j).set(i, (values[j][3] * values[j][2])/ p_x);
            }
        }
        return pij;
    }
    
    public List create_mixture(List<List<Double>> examples, List<Double> classes, int attribute_count, int gaussian_count)
    {
        java.util.Collections.sort(classes);
        List<mixture_gaussian> list_gaussian = new ArrayList<mixture_gaussian>();
        for(int j = 0; j < classes.size(); j++)
        {
            for(int i = 0; i < attribute_count; i++)
            {
                List<Double> attribute_list = get_list(examples, i, classes.get(j), attribute_count);
                java.util.Collections.sort(attribute_list);
                Double S = attribute_list.get(0);
                Double L = attribute_list.get(attribute_list.size() - 1);
                Double G = (L-S)/gaussian_count;
                Double[][] values = new Double[gaussian_count][4];
                for(int k = 0; k < gaussian_count; k++)
                {
                    values[k][0] = S + (k * G) + (G/2) ;
                    values[k][1] = 1.0;
                    values[k][2] = (double)1/gaussian_count;
                }
                for(int l = 0; l < 50; l++)
                {
                    List<List<Double>> temp_list = calc_pij(attribute_list, values, gaussian_count);
                    Double sum_numerator = 0.0,sum_denominator = 0.0;    
                    for(int k = 0; k < gaussian_count; k++)
                    {
                        for(int a = 0; a < attribute_list.size(); a++)
                        {
                            sum_numerator = sum_numerator + (temp_list.get(k).get(a) * attribute_list.get(a));
                            sum_denominator = sum_denominator + temp_list.get(k).get(a);
                        }
                        values[k][0] = sum_numerator/sum_denominator;
                    }
                    for(int k = 0; k < gaussian_count; k++)
                    {
                        for(int a = 0; a < attribute_list.size(); a++)
                        {
                            sum_numerator = sum_numerator + (temp_list.get(k).get(a) * (Math.pow(attribute_list.get(a) - values[k][0], 2)));
                            sum_denominator = sum_denominator + temp_list.get(k).get(a);
                        }
                        values[k][1] = Math.sqrt(sum_numerator/sum_denominator);
                    }
                    for(int k = 0; k < gaussian_count; k++)
                    {
                        for(int a = 0; a < attribute_list.size(); a++)
                            sum_denominator = sum_denominator +temp_list.get(k).get(a);
                    }
                    for(int k = 0; k < gaussian_count; k++)
                    {
                        for(int a = 0; a < attribute_list.size(); a++)
                            sum_numerator = sum_numerator +temp_list.get(k).get(a);
                        values[k][2] = sum_numerator/sum_denominator;
                    }
                }
                mixture_gaussian gsn = new mixture_gaussian(classes.get(j),i ,values , gaussian_count);
                list_gaussian.add(gsn);
            }
        }
        return list_gaussian;
    }
}
