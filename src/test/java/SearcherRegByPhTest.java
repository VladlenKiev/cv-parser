import cloud.molddata.parser.cv.parser.SeacrherRegByPh;
import org.junit.Test;
import static org.junit.Assert.*;

public class SearcherRegByPhTest {
    @Test
    public void testFindFileCountryCode(){
        SeacrherRegByPh seacrherRegByPhTest = new SeacrherRegByPh();

        String result = seacrherRegByPhTest.findFileCountryCode("+38050");

        assertEquals("resources/location/phone-codes.json", result);

    }

}
