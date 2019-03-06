package util;

public abstract class Perfomance {

    /**
     *
     * @param str
     * @param c
     * @return amount of c in term
     */
    public static int countChar(String str, char c){
        int count = 0;
        for(int i=0; i < str.length(); i++){
            if(str.charAt(i) == c)
                count++;
        }
        return count;
    }

}
