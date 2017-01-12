package cloud.molddata.parser.cv.parser;

import cloud.molddata.parser.cv.model.Contact;
import cloud.molddata.parser.cv.model.CV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Analyzer {
    private static String skills_block;
    private static String experience_block;
    private static String education_block;
    private static String languages_block;
    //------------------------------------

    public static ArrayList<Block> AnalyzeAndrey(Set<Block> blocksSet, CV cv) {
        System.out.println("BLOCK SIZE="+blocksSet.size());
        //System.out.println("BLOCK ="+blocksSet.toArray().toString());
        ArrayList<Block> listUnknownBlock=new ArrayList<>();
        while (blocksSet.size() > 0) {
            System.out.println(blocksSet.size());
            for (Block candidateBlock : SetUnknownBlockFind(blocksSet))
                listUnknownBlock.add(candidateBlock);
            //-----------------
           /* for (Block block:blocksSet){
                System.out.println("TEXT={"+block.getTextBlock()+"\n}");
                System.out.println("type of block: "+block.getTypeBlock());
                System.out.print(" Percent of block: "+block.getPercentLanguages());
                System.out.print(" SIZE of block: "+block.getTextBlockSize());
            }*/
            // max value return Filters;
            if (blocksSet.size() > 0) {
                System.out.println("before maxBLOCK with size=" + blocksSet.size());
                Block maxBlock = findMaxBlock(blocksSet);
                System.out.println("after maxBLOCK");
                maxBlock = findMaxBlock(sameTypeBlockSetCreate(maxBlock, blocksSet));
                //--------------------
           /* System.out.println("type of maxblock: "+maxBlock.getTypeBlock());
            System.out.println("Percent of maxblock: "+maxBlock.getPercentLanguages());
            System.out.println("SIZE of maxblock: "+maxBlock.getTextBlockSize());*/
                // set text block in CV
                //if (maxBlock != null) {
                setTextToCV(maxBlock, cv);
                // wipe of type list
                wipeOffBlockSet(maxBlock.getTypeBlock(), blocksSet);
                // remove this Block from blockSet
                System.out.println("a3");
                //removeBlock(maxBlock, blocksSet);
                blocksSet.remove(maxBlock);
                System.out.println("a4");
            }
        }
        return listUnknownBlock;
    }

    private static Set<Block> sameTypeBlockSetCreate(Block maxBlock,Set<Block> blocksSet){
        //System.out.println("IN sameTYPEblock="+ Arrays.toString(blocksSet.toArray()));
        Set<Block> sameTypeBlockSet=new LinkedHashSet<Block>();
        String blockType=maxBlock.getTypeBlock();
        System.out.println("IN sameTYPEblock --> typeblock="+blockType);
        for(Block block:blocksSet){
            System.out.println(block.getTypeBlock());
            //System.out.println("i'm enter to FOR-EACH");
            if(blockType.equals(block.getTypeBlock())) {
                //System.out.println("i'm enter to sameTypeADD");
                sameTypeBlockSet.add(block);
            }
        }
        /*for(Block block:sameTypeBlockSet)
            blocksSet.remove(block);*/
        System.out.println("OUT sameTYPEblock="+Arrays.toString(sameTypeBlockSet.toArray()));
        return sameTypeBlockSet;
    }

    private static Set<Block> SetUnknownBlockFind(Set<Block> blocksSet){
        System.out.println("I'm entered to SetUnknownBlockFind method!");
        Set<Block> setNoneBlocks=new LinkedHashSet<>();
        for(Block block:blocksSet){
            System.out.println("i'm entered to for-each blockset in SetUnknownBlockFind method!");
            if("none".equals(block.getTypeBlock())) {
                System.out.println("before ADD block");
                setNoneBlocks.add(block);
                System.out.println("before REM block="+blocksSet.size());
                System.out.println("AFTER REMOVE="+blocksSet.size());
            }
        }
        for(Block block:setNoneBlocks)
            blocksSet.remove(block);
        System.out.println("return from SetUnKNWBlcFind=");
        return setNoneBlocks;
    }

    private static void wipeOffBlockSet(String typeList, Set<Block> blockSet) {
        for (Block block : blockSet) {
            block.wipeOffList(typeList);
        }
    }

    private static void removeBlock(Block block, Set<Block> blockSet) {
        if (blockSet.contains(block)) {
            blockSet.remove(block);
            //System.out.println("REMOVE="+block);
        }
    }

    private static void setTextToCV(Block block, CV cv) {
        if ("skills".equals(block.getTypeBlock()))
            cv.setSkills(block.getTextBlock());
        else if ("experience".equals(block.getTypeBlock()))
            cv.setExp(block.getTextBlock());
        else if ("education".equals(block.getTypeBlock()))
            cv.setEdu(block.getTextBlock());
        else if ("languages".equals(block.getTypeBlock()))
            cv.setLang(block.getTextBlock());
        else if ("trainings".equals(block.getTypeBlock()))
            cv.setTrainings(block.getTextBlock());
        else if ("objective".equals(block.getTypeBlock()))
            cv.setObjective(block.getTextBlock());
        //------------------------------
        addNewWords(block);
        //------------------------------
    }

       private static void addNewWords(Block block){
            if("skills".equals(block.getTypeBlock()))
                WordsContent.add_WORD_SKILLS(block.getUnrecognizedWords());
            else if("experience".equals(block.getTypeBlock()))
                WordsContent.add_WORD_EXPERIENCE(block.getUnrecognizedWords());
            else if("education".equals(block.getTypeBlock()))
                WordsContent.add_WORD_EDUCATION(block.getUnrecognizedWords());
            else if("languages".equals(block.getTypeBlock()))
                WordsContent.add_WORD_LANGUAGE(block.getUnrecognizedWords());
            else if("trainings".equals(block.getTypeBlock()))
                WordsContent.add_WORD_TRAININGS(block.getUnrecognizedWords());
            else if("objective".equals(block.getTypeBlock()))
                WordsContent.add_WORD_OBJECTIVE(block.getUnrecognizedWords());
    }

    private static Block findMaxBlock(Set<Block> blockSet) {
        String type = "none";
        int maxPercent = 0;
        int maxPoint = 0;
        int textSize = 0;
        Block maxBlock = null;
        for (Block block : blockSet) {
            type = block.getTypeBlock();
            if ((type.equals("skills") && block.getPointSkills() > maxPoint)||(type.equals("skills") && block.getPercentSkills() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointSkills();
                maxPercent = block.getPercentSkills();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("experience") && block.getPointExperience() > maxPoint )||(type.equals("experience") && block.getPercentExperience() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointExperience();
                maxPercent = block.getPercentExperience();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("education") && block.getPointEducation() > maxPoint)||(type.equals("education") && block.getPercentEducation() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointEducation();
                maxPercent = block.getPercentEducation();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("languages") && block.getPointLanguages() > maxPoint)||(type.equals("languages") && block.getPercentLanguages() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointLanguages();
                maxPercent = block.getPercentLanguages();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("trainings") && block.getPointTrainings() > maxPoint)||(type.equals("trainings") && block.getPercentTrainings() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointTrainings();
                maxPercent = block.getPercentTrainings();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            }else if ((type.equals("objective") && block.getPointSkills() > maxPoint)||(type.equals("objective") &&  block.getPercentObjective() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointObjective();
                maxPercent = block.getPercentObjective();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            }
        }
        System.out.println("FOUNDED max BLOCK="+maxBlock.toString());
        System.out.println("FOUNDED max BLOCK="+maxBlock.getTypeBlock());
        return maxBlock;
    }

    /*private static Block findMaxBlock(Set<Block> blockSet) {
        String type = "none";
        int maxPercent = 0;
        int wordWeight = 0;
        Block maxBlock = null;
        for (Block block : blockSet) {
            type = block.getTypeBlock();
            if (type.equals("skills") && block.getPointSkills() > maxPercent && block.getTextBlockSize() > wordWeight) {
                maxPercent = block.getPointSkills();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("experience") && block.getPointExperience() > maxPercent && block.getTextBlockSize() > wordWeight) {
                maxPercent = block.getPointExperience();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("education") && block.getPointEducation() > maxPercent && block.getTextBlockSize() > wordWeight) {
                maxPercent = block.getPointEducation();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("languages") && block.getPointLanguages() > maxPercent && block.getTextBlockSize() > wordWeight) {
                maxPercent = block.getPointLanguages();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("trainings") && block.getPointTrainings() > maxPercent && block.getTextBlockSize() > wordWeight) {
                maxPercent = block.getPointTrainings();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            }else if (type.equals("objective") && block.getPointObjective() > maxPercent && block.getTextBlockSize() > wordWeight) {
                maxPercent = block.getPointObjective();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            }
        }
        System.out.println("FOUNDED max BLOCK="+maxBlock.toString());
        System.out.println("FOUNDED max BLOCK="+maxBlock.getTypeBlock());
        return maxBlock;
    }*/
    /*private static Block findMaxBlock(Set<Block> blockSet) {
        String type = "none";
        int maxPercent = 0;
        int wordWeight = 0;
        Block maxBlock = null;
        for (Block block : blockSet) {
            type = block.getTypeBlock();
            if (type.equals("skills") && block.getTextBlockSize() > wordWeight && block.getPercentSkills() > maxPercent) {
                maxPercent = block.getPercentSkills();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("experience") && block.getTextBlockSize() > wordWeight && block.getPercentExperience() > maxPercent) {
                maxPercent = block.getPercentExperience();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("education") && block.getTextBlockSize() > wordWeight && block.getPercentEducation() > maxPercent) {
                maxPercent = block.getPercentEducation();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("languages") && block.getTextBlockSize() > wordWeight && block.getPercentLanguages() > maxPercent) {
                maxPercent = block.getPercentLanguages();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("trainings") && block.getTextBlockSize() > wordWeight && block.getPercentTrainings() > maxPercent) {
                maxPercent = block.getPercentTrainings();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            } else if (type.equals("objective") && block.getTextBlockSize() > wordWeight && block.getPercentObjective() > maxPercent) {
                maxPercent = block.getPercentObjective();
                maxBlock = block;
                //---------------
                wordWeight = block.getTextBlockSize();
                //---------------
            }
        }
        return maxBlock;
    }*/

    /*private static Block findMaxBlock(Set<Block> blockSet) {
        String type = "none";
        int maxPercent = 0;
        int wordWeight = 0;
        Block maxBlock = null;
        for (Block block : blockSet) {
            type = block.getTypeBlock();
            if (type.equals("skills") *//*&& block.getPointSkills() > maxPercent*//* && block.getPercentSkills() > wordWeight) {
                //maxPercent = block.getPointSkills();
                maxBlock = block;
                //---------------
                wordWeight = block.getPercentSkills();
                //---------------
            } else if (type.equals("experience") *//*&& block.getPointExperience() > maxPercent*//* && block.getPercentExperience() > wordWeight) {
                //maxPercent = block.getPointExperience();
                maxBlock = block;
                //---------------
                wordWeight = block.getPercentExperience();
                //---------------
            } else if (type.equals("education") *//*&& block.getPointEducation() > maxPercent*//* && block.getPercentEducation() > wordWeight) {
                //maxPercent = block.getPointEducation();
                maxBlock = block;
                //---------------
                wordWeight = block.getPercentEducation();
                //---------------
            } else if (type.equals("languages") *//*&& block.getPointLanguages() > maxPercent*//* && block.getPercentLanguages() > wordWeight) {
                //maxPercent = block.getPointLanguages();
                maxBlock = block;
                //---------------
                wordWeight = block.getPercentLanguages();
                //---------------
            } else if (type.equals("trainings") *//*&& block.getPointTrainings() > maxPercent*//* && block.getPercentTrainings() > wordWeight) {
                //maxPercent = block.getPointTrainings();
                maxBlock = block;
                //---------------
                wordWeight = block.getPercentTrainings();
                //---------------
            } else if (type.equals("objective") *//*&& block.getPointObjective() > maxPercent*//* && block.getPercentObjective() > wordWeight) {
                //maxPercent = block.getPointObjective();
                maxBlock = block;
                //---------------
                wordWeight = block.getPercentObjective();
                //wordWeight = block.getTextBlockSize();
                //---------------
            }
        }
        return maxBlock;
    }*/

    public static void AnalyzeVova(ArrayList<Contact> contactList, Contact contact) {
        int tempWeight = 0;
        for (Contact contacts : contactList) {
            System.out.println(contacts.toString());
            if (contacts.getWeightContact() > tempWeight) {
                if (contacts.getFullName() != "") {
                    contact.setFullName(contacts.getFullName());
                    System.out.println("Namee?=" + contacts.getFullName());
                    System.out.println("weight=" + contacts.getWeightContact());
                }
                if (contacts.getEmail() != null)
                    contact.setEmail(contacts.getEmail());
                if (contacts.getPhone() != null) {
                    contact.setPhone(contacts.getPhone());
                    contact.setLocation(contacts.getLocation());
                }
                tempWeight = contacts.getWeightContact();
            }

        }
    }
}
