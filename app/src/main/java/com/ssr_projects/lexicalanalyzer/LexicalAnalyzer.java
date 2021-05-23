package com.ssr_projects.lexicalanalyzer;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.util.Stack;


public class LexicalAnalyzer {

    char[] codeArray;
    char beginningChar;
    int lineCount = 1;
    Activity activity;
    String errorsFound = "", commentsFound = "", stringFound = "", bracketsBalanced = "",
            delimitingSymbols = "", operators = "", keywords = "", literal = "", constants = "";

    TextView operatorBox, keywordBox, literalBox, constantBox, delimiterBox, bracketBox, stringBox, commentBox, errorBox;

    Boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '=';
    }


    public LexicalAnalyzer(String analyzingCode, Activity activity) {
        codeArray = analyzingCode.toCharArray();
        this.activity = activity;

        operatorBox = activity.findViewById(R.id.operations_box);
        keywordBox = activity.findViewById(R.id.keywords_box);
        literalBox = activity.findViewById(R.id.literals_box);
        commentBox = activity.findViewById(R.id.comment_box);
        constantBox = activity.findViewById(R.id.constants_box);
        delimiterBox = activity.findViewById(R.id.delimiter_box);
        bracketBox = activity.findViewById(R.id.bracket_box);
        stringBox = activity.findViewById(R.id.string_box);
        errorBox = activity.findViewById(R.id.errors_box);
    }

    Boolean isDelimiter(char ch) {
        return ch == ' ' || ch == ',' || ch == ';' || ch == '(' || ch == ')' ||
                ch == '[' || ch == ']' || ch == '{' || ch == '}' || ch == '#';
    }

    Boolean validIdentifier(String identifier) {
        char[] str = identifier.toCharArray();
        return str[0] != '0' && str[0] != '1' && str[0] != '2' &&
                str[0] != '3' && str[0] != '4' && str[0] != '5' &&
                str[0] != '6' && str[0] != '7' && str[0] != '8' &&
                str[0] != '9' && !isDelimiter(str[0]);
    }

    /**
     * Add all c++ key words
     *
     * @param buffer
     * @return
     */

    Boolean isKeyword(String buffer) {
        String[] keywords = {"int", "auto", "break", "case", "char", "const", "continue", "default",
                "do", "double", "else", "enum", "extern", "float", "for", "goto",
                "if", "int", "long", "register", "return", "short", "signed",
                "sizeof", "static", "struct", "switch", "typedef", "union",
                "unsigned", "void", "volatile", "while", "include", "using", "namespace", "class"};

        boolean flag = false;
        for (String key : keywords) {
            if (key.equals(buffer)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public void AnalyzeCode() {
        for (int j = 0; j < codeArray.length; j++) {
            char ch = codeArray[j];

            if (ch == '\n') {
                lineCount++;
            }

            if (!(Character.isDigit(ch) || Character.isAlphabetic(ch))) {
                beginningChar = ch;
                if (ch == '/') {
                    if(j + 1 < codeArray.length)
                    if (codeArray[j + 1] == '*' || codeArray[j + 1] == '/') {
                        String str = "";
                        for (int x = j + 2; j < codeArray.length; j++, x++) {
                            if (x < codeArray.length) {
                                ch = codeArray[x];
                                if ((ch == '*' && codeArray[x + 1] == '/') || ch == '\n') {
                                    break;
                                } else {
                                    str += ch;
                                }
                            }
                        }
                        commentsFound += "‣ " + "Comment: " + str + " in line " + lineCount + "\n";

                    } else if (isDelimiter(ch)) {
                        delimitingSymbols += "‣ " + "\"" + ch + "\"" + " is a Delimiting Symbol" + " in line " + lineCount + "\n";
                    }
                } else if (ch == '>') {
                    if(j + 1 < codeArray.length) {
                        if (codeArray[j + 1] == '>') {
                            operators += "‣ " + ">> is an Istream Operator Symbol" + " in line " + lineCount + "\n";
                            j++;
                        } else {
                            delimitingSymbols += "‣ " + "\"" + ch + "\"" + " is a Delimiting Symbol" + " in line " + lineCount + "\n";
                        }
                    }
                    else{
                        delimitingSymbols += "‣ " + "\"" + ch + "\"" + " is a Delimiting Symbol" + " in line " + lineCount + "\n";
                    }
                } else if (ch == '<') {
                    if(j + 1 < codeArray.length) {
                        if (codeArray[j + 1] == '<') {
                            operators += "‣ " + "<< is an Ostream Operator Symbol" + " in line " + lineCount + "\n";
                            j++;
                        } else {
                            delimitingSymbols += "‣ " + "\"" + ch + "\"" + " is a Delimiting Symbol" + " in line " + lineCount + "\n";
                        }
                    }
                    else{
                        delimitingSymbols += "‣ " + "\"" + ch + "\"" + " is a Delimiting Symbol" + " in line " + lineCount + "\n";
                    }
                } else if (isDelimiter(ch)) {
                    delimitingSymbols += "‣ " + "\"" + ch + "\"" + " is a Delimiting Symbol" + " in line " + lineCount + "\n";
                } else {
                    if (isOperator(ch)) {
                        operators += "‣ " + "\"" + ch + "\"" + " is an Operator" + " in line " + lineCount + "\n";
                    }
                }
            } else if (beginningChar == '"') {
                String str = "";
                for (int x = j; j < codeArray.length; j++, x++) {
                    ch = codeArray[x];
                    if (ch == '"') {
                        j--;
                        break;
                    } else {
                        str += ch;
                    }
                }
                stringFound += "‣ " + str + " is string / pre-processor directive in line " + lineCount + "\n";
            }
            else if (beginningChar == '\'') {
                String str = "";
                for (int x = j; j < codeArray.length; j++, x++) {
                    ch = codeArray[x];
                    if (ch == '\'') {
                        j--;
                        break;
                    } else {
                        str += ch;
                    }
                }
                stringFound += "‣ " + str + " is a character in line " + lineCount + "\n";
            }

            else if(beginningChar == '<') {
                String str = "";
                int temp = j, flag = 0;
                for (int x = j; j < codeArray.length; j++, x++) {
                    ch = codeArray[x];
                    if (ch == '>') {
                        j--;
                        flag++;
                        break;
                    } else {
                        str += ch;
                    }
                }
                if(flag == 0){
                    beginningChar = '$';
                    j = temp;
                    j--;
                    Log.e("TAG", "AnalyzeCode: " + j  + str);
                }
                else{
                    stringFound += "‣ " + str + " is a pre-processor directive / template type in line " + lineCount + "\n";
                }
            }
            else {

                String str = "";
                for (int x = j; j < codeArray.length; j++, x++) {
                    ch = codeArray[x];
                    if ((Character.isDigit(ch) || Character.isAlphabetic(ch))) {
                        str += ch;
                    } else {
                        j--;
                        break;
                    }
                }
                if (!str.equals("")) {
                    if (isKeyword(str)) {
                        keywords += "‣ " + str + " is a Keyword" + " in line " + lineCount + "\n";
                    } else if (validIdentifier(str)) {
                        literal += "‣ " + str + " is a Valid Identifier" + " in line " + lineCount + "\n";
                    } else {
                        try {
                            Integer.parseInt(str);
                            constants += "‣ " + str + " is a Constant " + " in line " + lineCount + "\n";
                        } catch (Exception e) {
                            errorsFound += "‣ " + str + " is an Invalid Identifier" + " in line " + lineCount + "\n";
                        }

                    }
                } else {
                    stringFound += "‣ " + str + " is a pre-processor directive or string" + " in line " + lineCount + "\n";
                }
            }
        }
    }

    void checkBrackets() {

        String bracketList = "";

        for (int i = 0; i < codeArray.length; i++) {
            if (codeArray[i] == '{' || codeArray[i] == '(' || codeArray[i] == '['
                    || codeArray[i] == '}' || codeArray[i] == ')' || codeArray[i] == ']' || codeArray[i] == '\n') {
                bracketList += codeArray[i];
            }
        }
        checkIfBalanced(bracketList);
        Log.e("Lexical Analyzer", "Comments Found: " + commentsFound);
        Log.e("Lexical Analyzer", "Strings Found: " + stringFound);
        Log.e("Lexical Analyzer", "Operators Found: " + operators);
        Log.e("Lexical Analyzer", "Constants Found: " + constants);
        Log.e("Lexical Analyzer", "Literals Found: " + literal);
        Log.e("Lexical Analyzer", "Delimiters Found: " + delimitingSymbols);
        Log.e("Lexical Analyzer", "Errors Found: " + errorsFound);
        Log.e("Lexical Analyzer", "Brackets:  " + bracketsBalanced);

    }

    void checkIfBalanced(String expr) {

        int position = 1;
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < expr.length(); i++) {
            char current = expr.charAt(i);
            if (current == '\n') {
                position++;
            }
            if (current == '{' || current == '(' || current == '[') {
                stack.push(current);
                bracketsBalanced += "‣ " + current + " bracket Ends at line " + position + "\n";
            }
            if (current == '}' || current == ')' || current == ']') {
                if (stack.isEmpty()) {
                    bracketsBalanced += "‣ " + current + " not Balanced at line " + position + "\n";
                    errorsFound += "‣ " + current + " not Balanced at line " + position + "\n";
                }
                else{
                char last = stack.peek();
                if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[') {
                    stack.pop();
                    bracketsBalanced += "‣ " + current + " bracket Ends at line " + position + "\n";
                } else {
                    bracketsBalanced += "‣ " + current + " not Balanced at line " + position + "\n";
                    errorsFound += "‣ " + current + " not Balanced at line " + position + "\n";
                }
                }
            }
        }
        bracketsBalanced += stack.isEmpty() ? "‣ All Brackets balanced \n" : "‣ " + stack.pop() + " bracket not Balanced at line " + position + "\n";
    }

    void setData() {
        operatorBox.setText(operators);
        keywordBox.setText(keywords);
        literalBox.setText(literal);
        constantBox.setText(constants);
        delimiterBox.setText(delimitingSymbols);
        bracketBox.setText(bracketsBalanced);
        stringBox.setText(stringFound);
        commentBox.setText(commentsFound);
        errorBox.setText(errorsFound);
    }


}
