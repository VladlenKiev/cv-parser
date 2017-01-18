package cloud.molddata.parser.cv.utill;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by admin on 05.01.2017.
 */
class BlockTest{
    String typeBlock;
    int point;
    int textBlockSize;
    double percent;

    public BlockTest(String typeBlock, int point, int textBlockSize) {
        this.typeBlock = typeBlock;
        this.point = point;
        this.textBlockSize = textBlockSize;
        this.percent = (point*100)/textBlockSize;
    }
    public String getTypeBlock() {       return typeBlock;    }
    public int getPoint() {        return point;    }
    public int getTextBlockSize() {        return textBlockSize;    }
    public double getPercent() {        return percent;    }

    @Override
    public String toString() {
        return "BlockTest{" +
                "typeBlock='" + typeBlock + '\'' +
                ", point=" + point +
                ", textBlockSize=" + textBlockSize +
                ", percent=" + percent +
                '}';
    }
}

public class TestLogic {
    public static void main(String[] args) {
        BlockTest blockTestStr1 = new BlockTest("skills", 1, 1);
        BlockTest blockTestStr10 = new BlockTest("skills", 1, 11);
        BlockTest blockTestStr2 = new BlockTest("languages", 1, 13);
       // BlockTest blockTestStr3 = new BlockTest("languages", 8, 25);
        //BlockTest blockTestStr4 = new BlockTest("languages", 4, 25);
        //BlockTest blockTestStr40 = new BlockTest("languages", 4, 20);
       // BlockTest blockTestStr5 = new BlockTest("languages", 60, 200);
        //BlockTest blockTestStr6 = new BlockTest("languages", 4, 5);
        //BlockTest blockTestStr40 = new BlockTest("languages", 2, 3);
        //BlockTest blockTestStr5 = new BlockTest("languages", 6, 27);
        //BlockTest blockTestStr6 = new BlockTest("languages", 6, 10);

        Set<BlockTest> blockTestSet = new LinkedHashSet<>();
        blockTestSet.add(blockTestStr1);
        blockTestSet.add(blockTestStr10);
      blockTestSet.add(blockTestStr2);
         /* blockTestSet.add(blockTestStr3);
        blockTestSet.add(blockTestStr4);
        blockTestSet.add(blockTestStr40);
        blockTestSet.add(blockTestStr5);*/
        //blockTestSet.add(blockTestStr6);

        System.out.println("SET BLOCK:");
        for (BlockTest block : blockTestSet){
            System.out.println(block.toString());
        }
        //Scanner keyboard = new Scanner(System.in);
        //System.out.println("enter an integer");
        //int myint = keyboard.nextInt();

        BlockTest findedBlock = findMaxBlockTest2(blockTestSet);
        System.out.println("FOUNDED MAX BLOCK="+findedBlock.toString());
    }

    private static BlockTest findMaxBlockTest1(Set<BlockTest> blockSet) {
        String type = "none";
        int maxPoint = 0;
        double maxPercent = 0;
        int wordWeight = 0;
        BlockTest maxBlock = null;
        for (BlockTest block : blockSet) {
            type = block.getTypeBlock();
            if ((type.equals("languages") && block.getPercent() > maxPercent)||(type.equals("languages") && block.getPoint() > maxPoint && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if ((type.equals("experience") && block.getPercent() > maxPercent)||(type.equals("experience") && block.getPoint() > maxPoint && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if ((type.equals("education") && block.getPercent() > maxPercent)||(type.equals("education") && block.getPoint() > maxPoint && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if ((type.equals("skills") && block.getPercent() > maxPercent)||(type.equals("skills") && block.getPoint() > maxPoint && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            }
        }
        return maxBlock;
    }
    private static BlockTest findMaxBlockTest2(Set<BlockTest> blockSet) {
        String type = "none";
        int maxPoint = 0;
        double maxPercent = 0;
        int wordWeight = 0;
        BlockTest maxBlock = null;
        for (BlockTest block : blockSet) {
            type = block.getTypeBlock();
            if ((type.equals("languages") && block.getPoint() > maxPoint)||(type.equals("languages") && block.getPercent() > maxPercent && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if ((type.equals("experience") && block.getPoint() > maxPoint)||(type.equals("experience") && block.getPercent() > maxPercent && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if ((type.equals("education") && block.getPoint() > maxPoint)||(type.equals("education") && block.getPercent() > maxPercent && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if ((type.equals("skills") && block.getPoint() > maxPoint)||(type.equals("skills") && block.getPercent() > maxPercent  && block.getTextBlockSize() > wordWeight)) {
                maxPoint = block.getPoint();
                maxPercent = block.getPercent();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            }
        }
        return maxBlock;
    }

}
