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
    public void parsseToCSV(String destinationFile)
    {
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
                    if (line.contains("concept:name")) {
                        caseID = Integer.parseInt(line.split("value=")[1].replace("\"","").replace("/>",""));
                    }
                }
                else if (line.startsWith("<event"))
                {
                    activityName =  reader.readLine();
                    if (activityName.contains("org:resource"))
                        activityName = reader.readLine();
                    activityName = activityName.split("value=")[1];
                    activityName = activityName.substring(1, activityName.length()-3);

                    lifeCycle = reader.readLine();

                    sTimestamp = reader.readLine();
                    // read the closing event tag
                    reader.readLine();
                    if (lifeCycle.contains("complete"))
                    {
                        sTimestamp = sTimestamp.split("value=")[1];
                        if (sTimestamp.length()> 13)
                            sTimestamp = sTimestamp.substring(1, sTimestamp.length()-13);
                        else
                            System.out.println("Date too short: "+ sTimestamp);
                        sTimestamp = sTimestamp.replace("T"," ");
                        Date ts = formatter.parse(sTimestamp);
                        lineToWrite = String.valueOf(caseID)+","+activityName+","+ts.getTime()+"\n";
                        writer.write(lineToWrite);
                    }
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
