package ee.ut.cs.dsg.miner.dfg;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertorTest {

    @Test
    public void testConvertStringToDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateString = "2019-11-12 18:00:00";

        Date d = null;
        try {
            d = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(d);
    }
}
