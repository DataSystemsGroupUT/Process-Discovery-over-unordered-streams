package ee.ut.cs.dsg.miner.example.source;

import ee.ut.cs.dsg.miner.example.source.preprocessor.XESPreprocessor;

public class XESToCSVConverter {

    public static  void main(String[] args)
    {
        XESPreprocessor preprocessor;

        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\BPI 2015 Reduced 2014.xml");
        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\BPI 2015 Reduced 2014.csv");


//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\2.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\2.csv");

//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\3.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\3.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\4.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\4.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\5.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\5.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\6.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\6.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\7.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\7.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\8.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\8.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\9.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\9.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\10.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\10.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\11.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\11.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\12.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\12.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\13.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\13.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\14.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\14.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\15.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\15.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\16.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\16.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\17.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\17.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\18.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\18.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\19.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\19.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\20.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\20.csv");
//
//        preprocessor = new XESPreprocessor("C:\\Work\\Data\\Process Logs\\21.xes");
//        preprocessor.parseToCSV("C:\\Work\\Data\\Process Logs\\21.csv");
    }

}
