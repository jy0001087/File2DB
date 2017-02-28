package latex.tools.single.excelReader.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bud on 2017/2/28.
 */
public class textmain {
    public static void main(String[] xargs){
        String a="/excel/定期_1.xlsx";
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]{2}");
        Matcher matcher = pattern.matcher(a);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
        Pattern patternNum = Pattern.compile("[0-9]");
        Matcher matcherNum = patternNum.matcher(a);
        while (matcherNum.find()){
            System.out.println(matcherNum.group());
        }
    }
}
