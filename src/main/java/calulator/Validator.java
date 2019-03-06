package calulator;

import util.Perfomance;

public abstract class Validator {

    public static boolean validate(String term) throws ValidatorException{

        if(term.length() < 3)
            throw new ValidatorException("Term can not be smaller than 3 characters");

        if(!validateParentheses(term))
            throw new ValidatorException("You must have the same amount of parentheses '(' and parentheses ')'");

        if(!validateCharacters(term))
            throw new ValidatorException("Invalid Characters in term");

        return true;

    }

    /**
    * check if the count of open and closed parentheses is equal
     * @param term the mathematical term
    * */
    private static boolean validateParentheses(String term){
        return Perfomance.countChar(term, '(') == Perfomance.countChar(term, ')');
    }


    private static boolean validateCharacters(String term){
        //valid are 0-9 ( ) + - . , * /
        return term.matches("^[0-9()+\\-.*/\"]*$");
    }

    //todo check for -- = + check for (-6) = -6 ...

}
