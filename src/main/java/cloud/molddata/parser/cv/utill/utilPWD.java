package cloud.molddata.parser.cv.utill;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by admin on 08.11.2016.
 */
public class utilPWD {
    public static void main(String[] args) {
        String p0="admin";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(p0));

    }
}
