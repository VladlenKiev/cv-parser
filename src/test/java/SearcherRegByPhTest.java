import cloud.molddata.parser.cv.parser.SeacrherRegByPh;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The user enters text into a search box. This class is used
 * to parse that text into specific search terms (or tokens).
 * It eliminates common words, and allows for the quoting of text, using
 * double quotes.
 * JDK 7+.
 */
public final class SearcherRegByPhTest {
    //@Test
    public void testFindFileCountryCode(){
        SeacrherRegByPh seacrherRegByPhTest = new SeacrherRegByPh();

        String result0 = seacrherRegByPhTest.findFileCountryCode("+38050");
        String result = seacrherRegByPhTest.findFileCountryCode("+38050");

        assertEquals("resources/location/phone-codes.json", result0);
        assertEquals("resources/location/phone-codes.json", result);

    }

    //@Test
    public void testFindFileCountryCode0(){
        SeacrherRegByPh seacrherRegByPhTest = new SeacrherRegByPh();

        String result0 = seacrherRegByPhTest.findFileCountryCode("+38050");
        String result = seacrherRegByPhTest.findFileCountryCode("+38050");

        assertEquals("resources/location/phone-codes.json", result0);
        assertEquals("resources/location/phone-codes.json", result);

    }
 //@Test
    public void testFindFileCountryCode1(){
        SeacrherRegByPh seacrherRegByPhTest = new SeacrherRegByPh();

        String result0 = seacrherRegByPhTest.findFileCountryCode("+38050");
        String result = seacrherRegByPhTest.findFileCountryCode("+38050");

        assertEquals("resources/location/phone-codes.json", result0);
        assertEquals("resources/location/phone-codes.json", result);

    }

}
