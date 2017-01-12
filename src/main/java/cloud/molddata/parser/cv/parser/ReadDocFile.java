package cloud.molddata.parser.cv.parser;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;

public class ReadDocFile
{
    public static void main(String[] args)    {
        File file = null;
        WordExtractor extractor = null;
        try
        {
            file = new File("C:\\PR\\Nesterov_Andrey(Junior_java)2.doc");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            //String fileData = extractor.getText();
            String regularParag="(?m)(?=^\\s{"+delimAnalyzer(extractor.getParagraphText())+"})";
            System.out.println(regularParag);
            //delimAnalyzer(extractor.getParagraphText());
            String[] strings=extractor.getText().split(regularParag);
            //String[] strings=fileData.split("(?m)(?=^\\s{4})");
            for(String text:strings) {
                System.out.println(text);
                System.out.println("----------------");
            }
        }
        catch (Exception exep)
        {
            exep.printStackTrace();
        }
    }

    /*private static int spaceAnalyzer(String[] strings ){
        int[] space=new int[4];
        int counter=0;
        //System.out.println(strings.length);
        for(int i=0;i<=strings.length-1;++i){
            System.out.println(i+":"+strings[i]+" length:"+strings[i].length());
            System.out.println("-------------");
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
        System.out.println("1:"+space[0]);
        System.out.println("2:"+space[1]);
        System.out.println("3:"+space[2]);
        System.out.println("4:"+space[3]);
        System.out.println(Arrays.toString(space));
        int spaceCounter=Integer.MAX_VALUE;
        int spaceType=0;
        for(int i=0;i<=space.length-1;++i){
            if(space[i]>0&&space[i]<=spaceCounter){
                spaceCounter=space[i];
                spaceType=i;
            }
        }
        System.out.println(spaceCounter);
        System.out.println(spaceType);
        int result=0;
        switch (spaceType+1){
            case 1: result=3;
                break;
            case 2: result=5;
                break;
            case 3: result=7;
                break;
            case 4: result=9;
                break;
        }
        System.out.println(result);
        return result;
    }*/

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
        //System.out.println(++spaceType);
        if (++spaceType==1)
            return 1;
        else return 2*(spaceType-1);
        /*int result=0;
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
        return result;*/
    }
}
