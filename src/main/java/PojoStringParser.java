import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PojoStringParser {
    public static Set<Info> parse(String pojo) {
        Set<Info> set = new HashSet<>();

        String subString = pojo.substring(pojo.indexOf("{")+1,pojo.lastIndexOf("}"));
        String[] tokens = subString.split(";");

        for(String token : tokens) {
            if(token.contains("{") || token.contains("return") || token.contains("this") | token.contains("}")) {
                continue;
            }
            String[] fieldInfo = token.split(" ");
            set.add(new Info(fieldInfo[fieldInfo.length-1],fieldInfo[fieldInfo.length-2]));
        }
        return set;
    }
}
