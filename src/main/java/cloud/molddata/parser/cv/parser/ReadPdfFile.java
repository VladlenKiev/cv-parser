package cloud.molddata.parser.cv.parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;

//import org.apache.pdfbox.io.ScratchFileBuffer;

/**
 * Created by Андрей on 21.07.2016.
 */
public class ReadPdfFile {
    public static void main(String[] args) {
        for(String s:pdfParse()){
            System.out.println(s);
            System.out.println("----------------------");
        }

    }

    private static String[] pdfParse(){
        String[] strings=null;
        try {
            PDDocument document = null;
            File file= new File("C:\\PR\\Malitsev CV(1).pdf");
            document = PDDocument.load(file);
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();

                String st = Tstripper.getText(document);
                /*strings=st.split("\n");
                for(int n=0;n<=strings.length-1;++n){
                    System.out.println((n+1)+" "+strings[n]);
                }*/
                String regularParag="(?m)(?=^\\s{"+delimAnalyzer(st.split("\n"))+"})";
                System.out.println(regularParag);
                return st.split(regularParag);
                //System.out.println("separator=" + Tstripper.getLineSeparator()+".");
                //String regular="(?m)(?=^\\s{"+delimAnalyzer(Tstripper.)+"})";
                //strings = st.trim().split("(?m)(?=^\\s{3})");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings;
    }

    private static int delimAnalyzer(String[] strings ){
        int[] space=spaceFiller(strings);
        int spaceType=spaceTyper(space);
        int result=resultTyper(spaceType);
        System.out.println(result);
        return result;
    }

    private static int[] spaceFiller(String[] strings){
        int[] space=new int[4];
        int counter=0;
        for(int i=0;i<=strings.length-1;++i){
            if(strings[i].length()==1||strings[i].length()==2){
                ++counter;
            }else if(counter>0&&strings[i].length()>2){
                switch (counter){
                    case 1: space[0]++;
                        break;
                    case 2: space[1]++;
                        break;
                    case 3: space[2]++;
                        break;
                    case 4: space[3]++;
                        break;
                }
                counter=0;
            }
        }
        return space;
    }

    private static int spaceTyper(int[] space){
        int spaceCounter=Integer.MAX_VALUE;
        int spaceType=0;
        for(int i=0;i<=space.length-1;++i){
            if(space[i]>0&&space[i]<=spaceCounter){
                spaceCounter=space[i];
                spaceType=i;
            }
        }
        return spaceType;
    }

    private static int resultTyper(int spaceType){
        int result=0;
        switch (spaceType+1){
            case 1: result=1;
                break;
            case 2: result=2;
                break;
            case 3: result=4;
                break;
            case 4: result=6;
                break;
        }
        return result;
    }

}
