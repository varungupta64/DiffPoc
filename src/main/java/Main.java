import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        String json1 = "{\n" +
                "    \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n" +
                "    \"title\": \"Product\",\n" +
                "    \"description\": \"A product from the catalog\",\n" +
                "    \"type\": \"object\",\n" +
                "    \"required\": [\"id\", \"name\", \"price\"]\n" +
                "}";
        String json2 = "{\n" +
                "    \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n" +
                "    \"title\": \"Product\",\n" +
                "    \"description\": \"A product from the catalog\",\n" +
                "    \"type\": \"object\",\n" +
                "    \"properties\": {\n" +
                "        \"id\": {\n" +
                "            \"description\": \"The unique identifier for a product\",\n" +
                "            \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "            \"description\": \"Name of the product\",\n" +
                "            \"type\": \"string\"\n" +
                "        },\n" +
                "        \"price\": {\n" +
                "            \"type\": \"number\",\n" +
                "            \"minimum\": 0,\n" +
                "            \"exclusiveMinimum\": true\n" +
                "        }\n" +
                "    },\n" +
                "    \"required\": [\"id\", \"name\", \"price\"]\n" +
                "}";
        try {
            JSONObject jsonOne = new JSONObject(json1);
            JSONObject jsonTwo = new JSONObject(json2);


            Iterator<String> keysOne = jsonOne.keys();
            Iterator<String> keysTwo = jsonTwo.keys();
            boolean isCompatible = true;

            while (keysOne.hasNext() && keysTwo.hasNext()) {
                String key = keysOne.next();
                if (jsonOne.get(key) instanceof JSONObject) {
                    if ((jsonTwo.get(key) == null) || !(jsonTwo.get(key) instanceof JSONObject)) {
                        isCompatible = false;
                        break;
                    }
                    if (((JSONObject) jsonTwo.get(key)).keySet().size() < ((JSONObject) jsonOne.get(key)).keySet().size()) {
                        isCompatible = false;
                        break;
                    }

                    for (Object object1 : jsonOne.keySet()) {
                        String keyStr = (String) object1;
                        Object keyvalue1 = jsonOne.get(keyStr);
                        Object keyvalue2 = jsonTwo.get(keyStr);
                        if (keyvalue2 == null) {
                            isCompatible = false;
                            break;
                        }

                        System.out.println("key: " + keyStr + " value1: " + keyvalue1 + " value2: " + keyvalue2);
                    }

                } else if (jsonOne.get(key) instanceof JSONArray) {
                    if (!(jsonTwo.get(key) instanceof JSONArray) || ((JSONArray) jsonTwo.get(key)).length() < ((JSONArray) jsonOne.get(key)).length()) {
                        System.out.println("JsonArray does not exist");
                        isCompatible = false;
                        break;
                    }
                    for (int i = 0; i < ((JSONArray) jsonOne.get(key)).length(); i++) {
                        if (!((JSONArray) jsonOne.get(key)).get(i).equals(((JSONArray) jsonTwo.get(key)).get(i))) {
                            System.out.println("JsonArray: One is: " + ((JSONArray) jsonOne.get(key)).get(i).toString() + " Two is: " + ((JSONArray) jsonTwo.get(key)).get(i).toString());
                            isCompatible = false;
                            break;
                        }
                    }

                } else if (!jsonOne.get(key).equals(jsonTwo.get(key))) {
                    System.out.println("One is: " + jsonOne.get(key) + " Two is: " + jsonTwo.get(key));
                    isCompatible = false;
                    break;
                }
            }
            System.out.println(isCompatible == true? "json2 is backward compatible with json1": "json2 is *not* backward compatible with json1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}