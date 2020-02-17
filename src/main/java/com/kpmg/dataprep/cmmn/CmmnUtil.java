package com.kpmg.dataprep.cmmn;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.UUID; //Universally Unique Identifier) https://www.baeldung.com/java-uuid

public class CmmnUtil {

    /**
     * 난수 생성
     * @return
     */
    public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "")+lpad(Integer.toString((int)(Math.random()*1000)), 3, "0");
    }

    /**
     * LPAD
     * @param str
     * @param len
     * @param addStr
     * @return
     */
    public static String lpad(String str, int len, String addStr){
        String result = str;
        int templen = len - result.length();

        for(int i=0; i<templen; i++){
            result = addStr + result;
        }
        return result;
    }

    public static String getJsnStr(HttpServletRequest request) throws Exception {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        return jb.toString();
    }

    public static String getStrValFromJsnStr(String jsnStr, String key) {
        try {
            JSONObject jsonObject = new JSONObject(jsnStr);
//			System.out.println(key + ":" + jsonObject.getString(key));

            String result = jsonObject.getString(key).trim().replaceAll("&quot;", "\"").replaceAll("&gt;", ">").replaceAll("&lt;", "<");
            if(result.equals("null")) {
                result = "";
            }

            return result;
        } catch (Exception e) {
            // LOGGER.error("error : " + getExceptionMsg(e));
            return "";
        }
    }

    public static byte[] decode(String base64) {
        int pad = 0;
        for (int i = base64.length() - 1; base64.charAt(i) == '='; i--)
            pad++;
        byte[] raw = new byte[(base64.length() * 6 / 8 - pad)];
        int rawIndex = 0;
        int base64Len = base64.length();
        for (int i = 0; i < base64Len; i += 4) {
            int block = (getValue(base64.charAt(i)) << 18)
                    + (getValue(base64.charAt(i + 1)) << 12)
                    + (getValue(base64.charAt(i + 2)) << 6)
                    + (getValue(base64.charAt(i + 3)));
            for (int j = 0; j < 3 && rawIndex + j < raw.length; j++)
                raw[rawIndex + j] = (byte) ((block >> (8 * (2 - j))) & 0xff);
            rawIndex += 3;
        }
        return raw;
    }

    protected static int getValue(char c) {
        if (c >= 'A' && c <= 'Z')
            return c - 'A';
        if (c >= 'a' && c <= 'z')
            return c - 'a' + 26;
        if (c >= '0' && c <= '9')
            return c - '0' + 52;
        if (c == '+')
            return 62;
        if (c == '/')
            return 63;
        if (c == '=')
            return 0;
        return -1;
    }
}
