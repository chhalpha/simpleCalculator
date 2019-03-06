import calulator.Calculator;
import org.junit.jupiter.api.Test;

public class TestPerformance {

    @Test
    public void startSimpleTermTest() {

        long startTime = System.currentTimeMillis();
        int max = 10000;
        for(int i = 0; i < max; i++)
            Calculator.calculate("-(((2+3)*4)*2+(6-2/-2.0-1*3+1))/(1-2)");

        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;

        System.out.println(max + " times: " + durationMillis / 3600.0 + " Min");

    }

}
