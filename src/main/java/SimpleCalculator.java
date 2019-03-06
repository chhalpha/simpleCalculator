import calulator.Calculator;
import calulator.Validator;
import calulator.ValidatorException;
import config.Configuration;
import persitance.ConnectionHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;


/**
 * @author Christian Held
 * @version 1.0.001
 */

public class SimpleCalculator {

    public static void main(String[] args) {

        /**
         * creates a connection to a free hosted 20MB ElephantSQL (PostegresSQL) Database
         * */
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connectionHelper.connect(Configuration.URL, Configuration.USER, Configuration.PWD);

        String userInput;
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Operation and press Enter, allowed operators are '(' , '+' , '-' , '*' , '/' , ')' e.g. -3.5*(1-7), enter exit to quit :");
            try {
                userInput = br.readLine();
                if(userInput.toLowerCase().equals("exit")){
                    System.exit(0);
                }
                userInput = userInput.replaceAll("\\s", ""); //remove all whitespaces
                userInput = userInput.replaceAll(",","."); //replace commmas for floating point numbers


                if(ConnectionHelper.CONNECTION != null) { //internet Connection and DB still running ?
                    try {
                        /**
                        * this could be for perfomance if expression would be parsed to a "always same" format and if expression would be very very long
                         * but here in this demo its just for testing Database
                        * */
                        String sql = "SELECT result FROM knowledgebase WHERE expression = '%s';";
                        sql = String.format(sql, userInput);
                        ResultSet resultSet = connectionHelper.executeQuery(sql);
                        if (resultSet != null && resultSet.next()) {
                            System.out.println("Fount in knowledgebase: " + resultSet.getString("result"));
                            connectionHelper.resultSet.close();
                            connectionHelper.statement.close();
                        } else {

                            /**
                             * now here comes the "real" part
                             * */
                            try {
                                Validator.validate(userInput);
                                Double result = Calculator.calculate(userInput);
                                System.out.println(String.valueOf(result));

                                /**
                                 * just for DB tests
                                 * */
                                try {
                                    sql = "INSERT INTO knowledgebase (expression, result) VALUES ('%s',%f);";
                                    sql = String.format(sql, userInput, result);
                                    connectionHelper.executeQuery(sql);
                                    System.out.println("Your expression: " + userInput + " and result: " + result + " were added to the knowledgebase");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (ValidatorException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        connectionHelper.statement.close();
                    }
                }else{
                    /**
                     * No internet Connection or DB is down...
                     * */

                    Validator.validate(userInput);
                    Double result = Calculator.calculate(userInput);
                    System.out.println(String.valueOf(result));

                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

        }
    }

}
