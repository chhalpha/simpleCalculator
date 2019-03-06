package calulator;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static java.lang.Double.NaN;

public class Calculator implements MathMethods{

    public static Double RESULT;

    private static Double LEFT_OPERAND;
    private static Double RIGHT_OPERAND;
    private static int OPERAND;

    private static final int multiply = 42;
    private static final int plus = 43;
    private static final int minus = 45;
    private static final int devide = 47;

    public static int[] operators = {multiply,plus,minus,devide}; //sortOrder ascending is important for binarySearch

    public static Double calculateSimpleExpression(String expression){

        try{
            Double d = Double.parseDouble(expression);
            return d;
        }catch (Exception e){

        }

        findOperands(expression);

        RESULT = NaN;

        switch (OPERAND){
            case plus:
                RESULT = add(LEFT_OPERAND,RIGHT_OPERAND);
                break;
            case minus:
                RESULT = substract(LEFT_OPERAND,RIGHT_OPERAND);
                break;
            case multiply:
                RESULT = multiply(LEFT_OPERAND,RIGHT_OPERAND);
                break;
            case devide:
                RESULT = devide(LEFT_OPERAND,RIGHT_OPERAND);
                break;

        }

        return RESULT;

    }

    public static Double calculate(String expression){

        String parsed = calulateComplex(expression);
        return calculateExpression(parsed);

    }



    private static Double add(Double a, Double b) {
        return a + b;
    }

    private static Double substract(Double a, Double b) {
        return a - b;
    }

    private static Double multiply(Double a, Double b) {
        return a * b;
    }

    private static Double devide(Double a, Double b) {
        if(b == 0)
            return NaN;

        return a / b;
    }
    /**
     * @param expression must be a [operand] b
     * */
    public static void findOperands(String expression){

        boolean firstNumberIsNegative = false;
        String[] operands;

        if(expression.startsWith("-")){
            expression = expression.substring(1);
            firstNumberIsNegative = true;
        }

        if(expression.contains("+-"))
            operands = expression.split("[+]");
        else if(expression.contains("--")) {
            expression = expression.replace("--","+");
            operands = expression.split("[+]");
        }else if(expression.contains("*-"))
            operands = expression.split("[*]");
        else if(expression.contains("/-"))
            operands = expression.split("[/]");
        else {
            try {
                operands = expression.split("[*+\\-/]");
            }catch(Exception e){
                return;
            }
        }


        int len;
        if(firstNumberIsNegative){
            LEFT_OPERAND = -1 * Double.valueOf(operands[0]);
        }else{
            LEFT_OPERAND = Double.valueOf(operands[0]);
        }

        RIGHT_OPERAND = Double.valueOf(operands[1]);
        len = operands[0].length();
        OPERAND = (int)expression.charAt(len); //to ascii


    }



    public static Double calculateExpression(String expression){

        while(expression.contains("*") || expression.contains("/")){
            clearMaps();
            fillOperatorMaps(expression);
            expression = makeOperation(expression,operatorPositionMultiplyDevideMap);
        }

        while (!isSimpleExpression(expression)){
            clearMaps();
            fillOperatorMaps(expression);
            expression = makeOperation(expression,operatorPositionPlusMinusMap);
        }

        return calculateSimpleExpression(expression);
    }


    private static boolean isSimpleExpression(String expression){
        int operandCount = 0;
        for(int i = 1; i < expression.length(); i++){
            char operator = expression.charAt(i);

            if(Arrays.binarySearch(operators, operator) >= 0){
                //System.out.println("Operator: " + operator);
                operandCount++;
                i++; //because of e.g. 5.0/-2 the - is not en operator so jump 1 char
                if(operandCount > 1)
                    return false;
            }
        }

        return true;
    }

    static LinkedHashMap<Integer,Integer> operatorPositionMultiplyDevideMap = new LinkedHashMap<>();
    static LinkedHashMap<Integer,Integer> operatorPositionPlusMinusMap = new LinkedHashMap<>();
    static LinkedHashMap<Integer,Integer> operatorPositionAllMap = new LinkedHashMap<>();
    static LinkedHashMap<Integer,Character> operatorMap = new LinkedHashMap<>();

    private static void clearMaps(){
        operatorPositionMultiplyDevideMap.clear();
        operatorPositionPlusMinusMap.clear();
        operatorPositionAllMap.clear();
        operatorMap.clear();
    }

    private static void fillOperatorMaps(String expression){
        int operandCount = 0;


        char[] operators1 = {'*','/'};
        char[] operators2 = {'+','-'};

        for(int i = 1; i < expression.length(); i++){
            char operator = expression.charAt(i);
            boolean added = false;

            if(Arrays.binarySearch(operators1, operator) >= 0){
                operatorPositionMultiplyDevideMap.put(operandCount,i);
                operatorMap.put(operandCount, operator);
                operatorPositionAllMap.put(operandCount, i);
                added = true;
            }else if(Arrays.binarySearch(operators2, operator) >= 0) {
                operatorPositionPlusMinusMap.put(operandCount, i);
                operatorMap.put(operandCount, operator);
                operatorPositionAllMap.put(operandCount, i);
                added = true;
            }
            if(added){
                operandCount++;
                i++;
            }
        }
    }

    private static String makeOperation(String expression, LinkedHashMap<Integer,Integer> map){

        String originalExpression = expression;
        //System.out.println("Exp: " + expression);

        Double result;

        for(Integer i : map.keySet()) {
            int l1, l2, r1, r2;
            char operator = operatorMap.get(i);

            if(operatorPositionAllMap.size() == 1){
                l1 = 0;
                l2 = operatorPositionAllMap.get(i);
                r1 = operatorPositionAllMap.get(i) + 1;
                r2 = originalExpression.length();
            }else {

                if (i == 0) {
                    l1 = 0;
                    l2 = operatorPositionAllMap.get(i);
                    r1 = operatorPositionAllMap.get(i) + 1;
                    r2 = operatorPositionAllMap.get(i + 1);
                } else if (i == operatorPositionAllMap.keySet().size() - 1) {
                    l1 = operatorPositionAllMap.get(i - 1) + 1;
                    l2 = operatorPositionAllMap.get(i);
                    r1 = operatorPositionAllMap.get(i) + 1;
                    r2 = originalExpression.length();
                } else {
                    l1 = operatorPositionAllMap.get(i - 1) + 1;
                    l2 = operatorPositionAllMap.get(i);
                    r1 = operatorPositionAllMap.get(i) + 1;
                    r2 = operatorPositionAllMap.get(i + 1);
                }
            }
            String leftOperand = originalExpression.substring(l1, l2);
            String rightOperand = originalExpression.substring(r1, r2);

            String newExpression = leftOperand + operator + rightOperand;
            result = Calculator.calculateSimpleExpression(newExpression);
            expression = originalExpression.replace(newExpression,String.valueOf(result));
            return expression;
        }

        return expression;
    }



    private static String calulateComplex(String expression){

        if(!expression.contains(")")) {
            expression = String.valueOf(Calculator.calculateExpression(expression));
            return expression;
        }

        int lastIndex = expression.indexOf(')');
        String cutExpression = expression.substring(0,lastIndex);
        int firstIndex = cutExpression.lastIndexOf('(');

        String subexpression = expression.substring(firstIndex + 1,lastIndex);

        expression = expression.replace("("+subexpression+")",String.valueOf(Calculator.calculateExpression(subexpression)));

        return calulateComplex(expression);

    }

}
