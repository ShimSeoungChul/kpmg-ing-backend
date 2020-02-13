package com.kpmg.dataprep.cmmn;
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
}
