/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aastha
 */
public class read_file {
    
    List<List<Double>> examples = new ArrayList<List<Double>>();
    List<Double> classes = new ArrayList<Double>();
    int count_column = 0;
    int count_attribute = 0;
    public static String format_string(String last_line)
    {
        String temp = "";
        for(int i = 0; i < last_line.length(); i++)
        {
            if(last_line.charAt(i) != ' ')
            {
                int index = last_line.indexOf(" ", i);
                if(index == -1)
                    index = i+1;
                temp = temp + last_line.substring(i, index)+ " ";
                i = index;
            }
        }
        temp = temp.substring(0, temp.length() - 1);
        return temp;
    }
    
    public void get_values(String input_file)
    {
        String line = null;
        try
        {
            int i = 0;
            FileReader file_reader = new FileReader(input_file);
            BufferedReader buffer_reader = new BufferedReader(file_reader);
            String last_line = format_string(buffer_reader.readLine());
            count_column = last_line.split(" ").length;
            count_attribute = count_column - 1;
            while((line = buffer_reader.readLine()) != null)
            {
                boolean unique = true;
                line = format_string(line);
                String[] temp = line.split(" ");
                List<Double> row = new ArrayList<Double>();
                for(int j = 0; j < count_column ; j++)
                {
                    row.add(Double.parseDouble(temp[j]));
                    if(j == count_column - 1)
                    {
                        for(int k = 0; k < classes.size(); k++)
                        {
                            if(Integer.parseInt(temp[j]) == classes.get(k))
                            {
                                unique = false;
                                break;
                            }
                        }
                        if(unique == true)
                            classes.add(Double.parseDouble(temp[j]));
                    }
                }
                examples.add(row);
            }
        }
        catch(FileNotFoundException ex) 
        {
            System.out.println("Unable to open file '" + input_file + "'");                
        }
        catch(IOException ex) 
        {
            System.out.println("Error reading file '" + input_file + "'");                   
        } 
    }
    
}
