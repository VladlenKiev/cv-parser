import np.com.mshrestha.dropzonetest.Parser.SeacrherRegByPh;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by admin on 11.01.2017.
 */
public class SearcherRegByPhTest {
    @Test
    public void testFindFileCountryCode(){
        SeacrherRegByPh seacrherRegByPhTest = new SeacrherRegByPh();

        String result = seacrherRegByPhTest.findFileCountryCode("+38050");

        assertEquals("resources/location/phone-codes.json", result);

    }

}
