import calulator.Calculator;
import calulator.Validator;
import calulator.ValidatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class TestCalculator {

    private static final String expression001 = "2+3";
    private static final String expression002 = "4-6";
    private static final String expression003 = "5*9";
    private static final String expression004 = "15/5";

    private static final String expression005 = "5.380+9.72";
    private static final String expression006 = "9.72-3.72";
    private static final String expression007 = "17.87*0.1";
    private static final String expression008 = "0.15/-0.15";

    private static final String expression009 = "-2.4+-1.6";
    private static final String expression010 = "-2.4--1.4";
    private static final String expression011 = "-2.41*-2.00";
    private static final String expression012 = "-1.00/-2.0";

    private static final String expression013 = "(((2+3)*4)*2+(6-2/-2.0-1*3+1))/(1-2)";

    @Test
    public void test() {

        Assertions.assertEquals(validate(expression001),true);
        Assertions.assertEquals(validate(expression002),true);
        Assertions.assertEquals(validate(expression003),true);
        Assertions.assertEquals(validate(expression004),true);
        Assertions.assertEquals(validate(expression005),true);
        Assertions.assertEquals(validate(expression006),true);
        Assertions.assertEquals(validate(expression007),true);
        Assertions.assertEquals(validate(expression008),true);
        Assertions.assertEquals(validate(expression009),true);
        Assertions.assertEquals(validate(expression010),true);
        Assertions.assertEquals(validate(expression011),true);
        Assertions.assertEquals(validate(expression012),true);
        Assertions.assertEquals(validate(expression013),true);

        Assertions.assertEquals(calculateSimple(expression001),5.0);
        Assertions.assertEquals(calculateSimple(expression002),-2.0);
        Assertions.assertEquals(calculateSimple(expression003),45.0);
        Assertions.assertEquals(calculateSimple(expression004),3.0);
        Assertions.assertEquals(calculateSimple(expression005),15.100000000000001);
        Assertions.assertEquals(calculateSimple(expression006),6.0);
        Assertions.assertEquals(calculateSimple(expression007),1.7870000000000001);
        Assertions.assertEquals(calculateSimple(expression008),-1.0);
        Assertions.assertEquals(calculateSimple(expression009),-4.0);
        Assertions.assertEquals(calculateSimple(expression010),-1.0);
        Assertions.assertEquals(calculateSimple(expression011),4.82);
        Assertions.assertEquals(calculateSimple(expression012),0.5);

        Assertions.assertEquals(calculateComplex("-6+6/2"),-3.0);
        Assertions.assertEquals(calculateComplex("-6.2-3.2+6.0/2.0*1.0"),-6.4);
        Assertions.assertEquals(calculateComplex("-(((2+3)*4)*2+(6-2/-2.0-1*3+1))/(1-2)"),45.0);


    }

    private boolean validate(String expression) {
        System.out.println("Validating Test..." + expression);

        try {
            Validator.validate(expression);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private Double calculateSimple(String expression) {
        System.out.println("calculate Test..." + expression);
        Double result = Calculator.calculateSimpleExpression(expression);
        System.out.println("result: " + result);
        return result;
    }
    
    private Double calculateComplex(String expression){
        System.out.println("calculate complex Test..." + expression);
        Double result = Calculator.calculate(expression);
        System.out.println("result: " + result);
        return result;
    }


}
