/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment7;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author Aastha
 */
public class naive_bayes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        read_file train = new read_file();
        train.get_values(args[0]);
        read_file classify = new read_file();
        classify.get_values(args[1]);
        train_data td = new train_data();
        data_classification dc = new data_classification();
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        if(args[2].contains("histo"))
        {
            List<histogram> list_histogram = td.create_histogram(train.examples, train.classes, Integer.parseInt(args[3]), train.count_attribute);
           Double p_class = 0.0;
            for(int i = 0; i < list_histogram.size(); i++)
            {
                for(int j = 0; j < Integer.parseInt(args[3]); j++)
                {
                    p_class = p_class + list_histogram.get(i).bin[j][2];
                }
                for(int j = 0; j < Integer.parseInt(args[3]); j++)
                {
                    System.out.println("Class " + list_histogram.get(i).clas + ",Attribute " + list_histogram.get(i).attr + ",Bin " + list_histogram.get(i).bin[j][2] + ",P(bin | class) = " + df.format(list_histogram.get(i).bin[j][3]));//df.format(list_histogram.get(i).bin[j][2]/p_class));
                }
            }
            List<classification_output> output = dc.Classify_histogram(classify.examples, train.classes, train.count_attribute, Integer.parseInt(args[3]), list_histogram);
            int count = 0;
            for(int j = 0; j < output.size(); j++)
            {
                System.out.println("ObjectID " + output.get(j).obj_ID + ",Predicted Class " + output.get(j).predicted_class + ",Probability " + df.format(output.get(j).probability) + ",True Class " + output.get(j).true_class + ",Accuracy " + output.get(j).accuracy);
                if(Double.compare(output.get(j).accuracy, 1.0) == 0)
                    count++;
            }
            double aa = ((double)count/classify.examples.size()) * 100;
            System.out.println("Accuracy : " + aa);
        }
        else if(args[2].contains("gaussi"))
        {
            List<gaussian> list_gaussian = td.create_gaussian(train.examples, train.classes, train.count_attribute);
            for(int i = 0; i < list_gaussian.size(); i++)
            {
                System.out.println("Class " + list_gaussian.get(i).clas + ",Attribute " + list_gaussian.get(i).attr + ",mean = " + df.format(list_gaussian.get(i).mean) + ",std = " + df.format(list_gaussian.get(i).sd));
            }
            List<classification_output> output = dc.Classify_gaussian(classify.examples, train.classes, train.count_attribute, list_gaussian);
            int count = 0;
            for(int j = 0; j < output.size(); j++)
            {
                System.out.println("ObjectID " + output.get(j).obj_ID + ",Predicted Class " + output.get(j).predicted_class + ",Probability " + df.format(output.get(j).probability) + ",True Class " + output.get(j).true_class + ",Accuracy " + output.get(j).accuracy);
                if(Double.compare(output.get(j).accuracy, 1.0) == 0)
                    count++;
            }
            double aa = ((double)count/classify.examples.size()) * 100;
            System.out.println("Accuracy : " + aa);
        }
        else if(args[2].contains("mix"))
        {
            List<mixture_gaussian> list_gaussian = td.create_mixture(train.examples, train.classes, train.count_attribute, Integer.parseInt(args[3]));
            for(int i = 0; i < list_gaussian.size(); i++)
            {
                for(int j = 0; j < Integer.parseInt(args[3]); j++)
                    System.out.println("Class " + list_gaussian.get(i).clas + ",Attribute " + list_gaussian.get(i).attr + ",Gaussian " + j + ",mean = " + df.format(list_gaussian.get(i).gaussian[j][0]) + ",std = " + df.format(list_gaussian.get(i).gaussian[j][1]));
            }
            List<classification_output> output = dc.Classify_mixture(classify.examples, train.classes, train.count_attribute, list_gaussian, Integer.parseInt(args[3]));
            int count = 0;
            for(int j = 0; j < output.size(); j++)
            {
                System.out.println("ObjectID " + output.get(j).obj_ID + ",Predicted Class " + output.get(j).predicted_class + ",Probability " + df.format(output.get(j).probability) + ",True Class " + output.get(j).true_class + ",Accuracy " + output.get(j).accuracy);
                if(Double.compare(output.get(j).accuracy, 1.0) == 0)
                    count++;
            }
            double aa = ((double)count/classify.examples.size()) * 100;
            System.out.println("Accuracy : " + aa);
        }
        else
            System.out.println("Wrong Classification Method");
    }
    
}
