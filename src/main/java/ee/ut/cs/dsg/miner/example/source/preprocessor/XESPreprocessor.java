package ee.ut.cs.dsg.miner.example.source.preprocessor;

import scala.runtime.ScalaRunTime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XESPreprocessor {

    private String filePath;
    public XESPreprocessor(String xesFile)
    {
        this.filePath = xesFile;
    }
    public void parseToCSV(String destinationFile)
    {
        int localCaseID = 0;
        try{
            int caseID = 0;
            BufferedReader reader;
            BufferedWriter writer;
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(destinationFile));
            String line = reader.readLine();
            String activityName;
            String sTimestamp;
            String lifeCycle;
            String lineToWrite;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(line != null)
            {
                line = line.replaceAll("\\s","");
                if (line.startsWith("<trace"))
                {
                    line = reader.readLine();
                    while (!line.contains("concept:name") && !line.startsWith("<event"))
                        line = reader.readLine();
                    if (line.contains("concept:name")) {
                        //String _case = line.split("value=")[1].replace("\"","").replace("/>","");
                        try {
                            caseID = Integer.parseInt(line.split("value=")[1].replace("\"", "").replace("/>", ""));
                        }
                        catch (NumberFormatException nfe)
                        {
                            System.out.println(line);
                            caseID = localCaseID++;
                        }
                    }
                    else
                    {
                        caseID = localCaseID++;
                    }
                }
                if (line.startsWith("<event"))
                {
                    boolean activityFound=false, timestampFound=false;
                    Date ts=null;
                    activityName="";
                    while (!activityFound || !timestampFound)
                    {
                        line = reader.readLine();
                        if (line.contains("concept:name"))
                        {
                            activityName = line;
                            activityName = activityName.split("value=")[1];
                            activityName = activityName.substring(1, activityName.length()-3);
                            activityFound = true;
                        }
                        else if (line.contains("time:timestamp"))
                        {
                            sTimestamp = line;
                            sTimestamp = sTimestamp.split("value=")[1];
                            if (sTimestamp.length()> 13)
                                sTimestamp = sTimestamp.substring(1, sTimestamp.length()-9);
                            else
                                System.out.println("Date too short: "+ sTimestamp);
                            sTimestamp = sTimestamp.replace("T"," ");
                            ts = formatter.parse(sTimestamp);
                            timestampFound=true;
                        }

                    }



               //     lifeCycle = reader.readLine();


                    // read the closing event tag
//                    reader.readLine();
//                    if (lifeCycle.contains("complete"))
//                    {

                        lineToWrite = String.valueOf(caseID)+","+activityName+","+ts.getTime()+"\n";
                        writer.write(lineToWrite);
//                    }
                }
                line = reader.readLine();
            }
            reader.close();
            writer.flush();
            writer.close();


        }
        catch(Exception e)
        {
            e.printStackTrace();

        }

    }
}
