package com.uncleYar.InfaProject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

/**
 * author: Yaroslav.Bilko
 * date: 17/04/2018
 * version: v1.1
 */

public class App 
{
    public static void main( String[] args )
    {
        String logPath = args[0];
        String parsedLog = args[1];
        String dimPath = args[2];
        
        //Date format objects
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        //Object for Informatica file errors
        List<String> errorCode = new ArrayList<>();
        
        System.out.println(df.format(date) + System.lineSeparator());
        System.out.println("Starting execute LogParse-v1.1.jar file.");
        System.out.println("Source log file is " + logPath);
        System.out.println("Parsed log what will attach to the letter is " + parsedLog);
        
        // FR_3067 - error code sample
        
        StringBuilder letterText = new StringBuilder();
        letterText.append(df.format(date) + System.lineSeparator());
        letterText.append("Issues in file need to fix: " + System.lineSeparator());

        try(FileReader fr = new FileReader(dimPath); Scanner sc = new Scanner(fr)) {
            while(sc.hasNext()) {
                errorCode.add(sc.nextLine());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Starting to read file " + logPath + "." + System.lineSeparator());
        try(FileReader fr = new FileReader(logPath); Scanner sc = new Scanner(fr)) {
            while (sc.hasNextLine()) {
                String logLine = sc.nextLine();
                //System.out.println(logLine); // for debuging
                if (logLine.contains("FR_")) {
                    for (String seq : logLine.split("\\D\\D\\D\\D\\D\\D_._._.")) {
                        //System.out.println(seq); // for debuging
                    	if(seq.contains("FR_")) {
                            int pos = seq.indexOf("FR_");
                            String errCodeFromLog = seq.substring(pos, pos+7).trim();
                            //System.out.println(seq); // for debuging
                            if(errorCode.contains(errCodeFromLog.trim())) {
                            	String prepStr = seq.substring(pos+7).trim();
                            	//System.out.println("*** DEBUG ----- >  " + prepStr); // for debuging
                                letterText.append(System.lineSeparator());
                            	letterText.append(prepStr.substring(1));
                            	//System.out.println("*****> DEBUG" + prepStr.trim().substring(1));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //System.out.println(letterText); // for debuging
        
        System.out.println("Starting to write file " + parsedLog + ".");
        try(FileWriter fw = new FileWriter(parsedLog)) {
            fw.write(String.valueOf(letterText));
            System.out.println("File " + parsedLog + " was writed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(System.lineSeparator() + "End of program.");
    }
}