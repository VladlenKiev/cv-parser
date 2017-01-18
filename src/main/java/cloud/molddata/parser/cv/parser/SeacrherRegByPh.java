package cloud.molddata.parser.cv.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.ResourceUtils;

import java.io.*;

public class SeacrherRegByPh {

    public String[] searcherCountryCode(String requestPhone){
        String[] result = new String[3];
        String[] resultNone = new String[3];
        String tempCountryCode = null;
        requestPhone = requestPhone.replaceAll("\\s*[\\(*|\\)*|\\s*]+\\s*", "");
        String path = findFileCountryCode(requestPhone);
        JSONCC[] jsoNmy = parseCountryCode(path);

        for (int j = 6;j>1;--j){
            if (requestPhone.length()<j) //for phone codes with digits less than 6
                continue;
            tempCountryCode = requestPhone.substring(0,j);
            for (int i = 0; i<jsoNmy.length;++i){
                if (jsoNmy[i].mask.contentEquals(tempCountryCode)){
                    result[0]=jsoNmy[i].mask;
                    result[1]=jsoNmy[i].name_en;
                    result[2]=jsoNmy[i].desc_en;
                    //System.out.println("index="+tempCountryCode+" its="+jsoNmy[i].name_en);
                    return result;
                    //result = jsoNmy[i].name_en;
                }
            }
        }
        return new String[]{(resultNone[0] = requestPhone),(resultNone[1] = "unknown"),(resultNone[2] = "unknown")};
    }

    public String findFileCountryCode(String requestPhone){
        String path;
        if (!requestPhone.matches("\\+\\d+")){
            requestPhone="+"+requestPhone;
        }
        switch (requestPhone.substring(1, 2)){
            case "1":
                path = "resources/location/phones-us.json";
                //path = "\\web-resources\\resources\\location\\phones-us.json";
                break;
            case "7":
                path = "resources/location/phones-ru.json";
                //path = "src\\main\\resources\\location\\phones-ru.json";
                break;
            default:
                //path = "C:\\Users\\admin\\IdeaProjects\\springmvc-dropzonejs-app-jpa-front_1\\target\\springmvc-dropzonejs-1.0.0-BUILD-SNAPSHOT\\resources\\location\\phone-codes.json";
                //path = "\\src\\main\\resources\\location\\phone-codes.json";
                path = "resources/location/phone-codes.json";
                break;
        }
        return path;
        //return String.valueOf(getClass().getResource(path));
    }

    private JSONCC[] parseCountryCode(String path){

        String resultJSON = null;

        try {
            resultJSON = RequestToCodesList(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        JSONCC[] jsoNmy = gson.fromJson(resultJSON, JSONCC[].class);
        //System.out.println("JSON: \n\t" + gson.toJson(jsoNmy));
        return jsoNmy;
    }

    private static String RequestToCodesList(String path) throws IOException {
        ClassLoader classLoader = new SeacrherRegByPh().getClass().getClassLoader();
        File file = ResourceUtils.getFile(classLoader.getResource(path).getFile());
        //File file = ResourceUtils.getFile(path);
        //File file = new File(path);
        //System.out.println("to method "+path);
        //System.out.println("after method "+file.getCanonicalPath());
        //System.out.println("classpath:web-inf/location/phone.codes.json");
        System.out.println();
        FileReader fr = new FileReader(file);
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(fr);
            char[] buf = new char[1000000];

            int r = 0;
            do {
                if ((r = br.read(buf)) > 0)
                    sb.append(new String(buf, 0, r));
            } while (r > 0);
        } finally {
            fr.close();
        }

        return sb.toString();
    }

}
