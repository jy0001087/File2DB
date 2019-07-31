package latex.tools.single.FileReader.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bud on 2017/2/28.
 * 仅用于测试文件读取和文件名的正则匹配测试
 */
public class textmain {
    public static void main(String[] xargs){
        String a="/excel/DQ_1.xlsx";
        Pattern pattern = Pattern.compile("[A-Z]{2}");
        Matcher matcher = pattern.matcher(a);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
        Pattern patternNum = Pattern.compile("[0-9]{1,2}");
        Matcher matcherNum = patternNum.matcher(a);
        while (matcherNum.find()){
            System.out.println(matcherNum.group());
        }
    }
}
