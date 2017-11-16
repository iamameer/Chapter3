package wci.frontend.pascal.tokens;

import wci.frontend.*;
import wci.frontend.pascal.*;

import static wci.frontend.Source.EOL;
import static wci.frontend.Source.EOF;
import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.*;

public class PascalMacroNumToken extends PascalToken{

    /**
     * Constructor.
     * @param source the source from where to fetch the token's characters.
     * @throws Exception if an error occurred.
     */
    public PascalMacroNumToken(Source source)
            throws Exception
    {
        super(source);
    }

    /**
     * Extract a Pascal string token STARTS with #MACRO from the source.
     * @throws Exception if an error occurred.
     */
    protected void extract()
            throws Exception {
        StringBuilder macroText = new StringBuilder("#MACRO"); //string to be tested
        StringBuilder macroBuffer = new StringBuilder(); //extract that will hold #MACRO from the source
        StringBuilder valueBuffer = new StringBuilder(); //extract that will hold VALUE

        char currentChar = nextChar();  // consume initial quote = M

        macroBuffer.append('#');
        //eat MACRO > after the #hastag
        while(Character.isLetter(currentChar)){
            macroBuffer.append(currentChar);
            currentChar = nextChar();
        }

        do {
            // Replace any whitespace character with a blank.
            if (Character.isWhitespace(currentChar)) {
                //do nothing
            }else if (Character.isDigit(currentChar) || Character.isLetter(currentChar)){
                valueBuffer.append(currentChar);
            }
            currentChar = nextChar();
        }while ((currentChar != '#') && (currentChar != EOF)); //repeat until find the next # or EOF



        //check #MACRO prefix same or not
        if (macroBuffer.toString().equals(macroText.toString())){
            type = MACRO;
            value = valueBuffer.toString();
       }else{
            type = ERROR;
            value = UNRECOGNIZABLE;
        }

        text = macroBuffer.toString();
    }

}
