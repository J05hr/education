package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

    public static String delims = " \t*+-/()[]";

    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     *
     * @param expr   The expression
     * @param vars   The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        StringBuilder sb = new StringBuilder("");
        //remove spaces
        for (int ch = 0; ch < expr.length(); ch++) {
            if (expr.charAt(ch) != ' ') {
                sb.append(expr.charAt(ch));
            }
        }
        String StrNoSpaces = sb.toString();
        //arrayOfSplits the string by delimiters, keep arrays intact
        String[] Splits = StrNoSpaces.split("[-+*/()]", 0);


        //scan through the arrayOfSplits strings
        for (int str = 0; str < Splits.length; str++) {
            //deal with arrays first
            if (Splits[str].contains("[")) {

                //scan through another split for array names and contents
                String[] arrSplits = Splits[str].split("\\[", 0);

                for (int str2 = 0; str2 < arrSplits.length; str2++) {

                    //no brackets and it isn't the last variable add the array
                    if (!arrSplits[str2].contains("]") && str2 != arrSplits.length-1 ) {
                        //check the existing list
                        for (int i = 0; i < arrays.size(); i++) {
                            if (arrays.get(i).name.equals(arrSplits[str2])){
                                break;
                            }
                            if (i == arrays.size()-1){
                                arrays.add(new Array(arrSplits[str2]));
                            }
                        }
                        //if arrays is empty add the first array
                        if (arrays.size()==0){
                            arrays.add(new Array(arrSplits[str2]));
                        }



                    //add the contents
                    //closed brackets add the var leave out the ]
                    } else if (arrSplits[str2].contains("]")) {
                        if (!arrSplits[str2].equals("") && !arrSplits[str2].matches("-?\\d*\\.*\\d+]?")) {
                            //check the existing list
                            for (int i = 0; i < vars.size(); i++) {
                                if (vars.get(i).name.equals(arrSplits[str2].substring(0, arrSplits[str2].indexOf("]")))){
                                    break;
                                }
                                if (i == vars.size()-1){
                                    vars.add(new Variable(arrSplits[str2].substring(0, arrSplits[str2].indexOf("]"))));
                                }
                            }
                            //if vars is empty add the first var
                            if (vars.size()==0){
                                vars.add(new Variable(arrSplits[str2].substring(0, arrSplits[str2].indexOf("]"))));
                            }
                        }

                    //last item with no bracket must be a variable from an split operation
                    } else {
                        if (!arrSplits[str2].equals("") && !arrSplits[str2].matches("-?\\d*\\.*\\d+]?")) {
                            //check the existing list
                            for (int i = 0; i < vars.size(); i++) {
                                if (vars.get(i).name.equals(arrSplits[str2])){
                                    break;
                                }
                                if (i == vars.size()-1){
                                    vars.add(new Variable(arrSplits[str2]));
                                }
                            }
                            //if vars is empty add the first var
                            if (vars.size()==0){
                                vars.add(new Variable(arrSplits[str2]));
                            }
                        }
                    }
                }

            } else {
                //skip null strings
                if (Splits[str].contains("]")) {
                    if (!Splits[str].equals("") && !Splits[str].matches("-?\\d*\\.*\\d+]?")) {
                        //check the existing list
                        for (int i = 0; i < vars.size(); i++) {
                            if (vars.get(i).name.equals(Splits[str].substring(0, Splits[str].indexOf("]")))){
                                break;
                            }
                            if (i == vars.size()-1){
                                vars.add(new Variable(Splits[str].substring(0, Splits[str].indexOf("]"))));
                            }
                        }
                        //if vars is empty add the first var
                        if (vars.size()==0){
                            vars.add(new Variable(Splits[str].substring(0, Splits[str].indexOf("]"))));
                        }

                    }
                } else if (!Splits[str].equals("") && !Splits[str].matches("-?\\d*\\.*\\d+]?")) {
                    //check the existing list
                    for (int i = 0; i < vars.size(); i++) {
                        if (vars.get(i).name.equals(Splits[str])){
                            break;
                        }
                        if (i == vars.size()-1){
                            vars.add(new Variable(Splits[str]));
                        }
                    }
                    //if vars is empty add the first var
                    if (vars.size()==0){
                        vars.add(new Variable(Splits[str]));
                    }
                }
            }
        }


        //print out the empty variables and arrays
        //for testing
        for (int i = 0; i < vars.size(); i++) {
            System.out.println(vars.get(i));
        }
        for (int i = 0; i < arrays.size(); i++) {
            System.out.println(arrays.get(i));
        }
    }



    /**
     * Loads values for variables and arrays in the expression
     *
     * @param sc     Scanner for values input
     * @param vars   The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     * @throws IOException If there is a problem with the input
     */
    public static void
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
            throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
                continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
                arr = arrays.get(arri);
                arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok, " (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;
                }
            }
        }
    }

    /**
     * Evaluates the expression.
     *
     * @param vars   The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {

        //print out the variables and arrays with their values
        //for testing
        for (int i = 0; i < vars.size(); i++) {
            System.out.println(vars.get(i));
        }
        for (int i = 0; i < arrays.size(); i++) {
            System.out.println(arrays.get(i));
        }


        //edit the expr to remove spaces and set +- and replace --
        if (expr.matches(".*\\+--.*")){
            expr = expr.replaceAll("\\+--","+");
        }else{
            expr = expr.replaceAll("--","+");
        }

        //start neg checks from index 1 to avoid adding a + to a negative starting number
        if (expr.contains(" ") || expr.contains("-")) {
            StringBuilder sb = new StringBuilder("");
            for (int ch = 0; ch < expr.length(); ch++) {
                if( expr.charAt(ch)== '-' && !expr.matches(".*[*/+]-.*") && !expr.matches("-?\\d*\\.*\\d+E?-?\\d*") && ch > 0 ) {
                    sb.append("+-");
                }else if(expr.charAt(ch) != ' ') {
                    sb.append(expr.charAt(ch));
                }
            }
            expr = sb.toString();
        }





        //*******************************************
        //base case, handle lone numbers or variables
        if (!expr.matches(".*[+/*()\\[\\]].*") || expr.matches("-?\\d*\\.*\\d+E?-?\\d*") ) {

            //lone number, just parse it
            if (expr.matches("-?\\d*\\.*\\d+E?-?\\d*")) {
                System.out.println("expr " + expr);
                return Float.parseFloat(expr);

            } else {
                //lone variable, find it and evaluate
                for (int i = 0; i < vars.size(); i++) {
                    if (vars.get(i).name.equals(expr)) {
                        return vars.get(i).value;
                    }
                }
            }




        //*******************************************
        //2nd case, simple arithmetic
        } else if (!expr.contains("[") && !expr.contains("(")) {

            //replace the expression's variables with values
            for (int i = 0; i < vars.size(); i++) {
                expr = expr.replace(vars.get(i).name, "" + vars.get(i).value);
            }
            System.out.println("expr " + expr);

            //initialize stacks
            Stack<String> storedOps = new Stack<>();
            Stack<Float> storedValues = new Stack<>();
            Stack<String> ops = new Stack<>();
            Stack<Float> values = new Stack<>();

            System.out.println("expr " + expr);

            //split into variable and operators arrays
            String[] arrayOfvaris = expr.trim().split("[*+/]");
            String[] arrayOfops = expr.trim().split("-?\\d*\\.*\\d+E?-?\\d*");

            //store all the values in a stack backwards
            for (int str = arrayOfvaris.length-1; str >= 0; str--) {
                if (!arrayOfvaris[str].equals("")) {
                    //push constant number
                    if (arrayOfvaris[str].matches("-?\\d*\\.*\\d+E?-?\\d*")) {
                        storedValues.push(Float.parseFloat(arrayOfvaris[str]));
                    } else {
                        //push variables by evaluating first
                        for (int i = 0; i < vars.size(); i++) {
                            if (vars.get(i).name.equals(arrayOfvaris[str])) {
                                storedValues.push(Float.valueOf(vars.get(i).value));
                                break;
                            }
                        }
                    }
                }
            }

            //store all the ops in a stack backwards
            for (int str = arrayOfops.length-1; str >= 0; str--) {
                if (!arrayOfops[str].equals("")) {
                    storedOps.push(arrayOfops[str]);
                }
            }

            //first run through to do * and / first, add anything else to stacks to do later
            while (!storedOps.isEmpty()) {
                //push the stored variable to the working stack
                if (!storedValues.isEmpty()) {
                    values.push(storedValues.pop());
                }

                //push operand to the stack or if it has a higher order use it right away
                if ((storedOps.peek().contains("*") || storedOps.peek().contains("/"))) {

                    //push the operation back to the stack
                    storedValues.push(operation(storedOps.pop(), values.pop(), storedValues.pop()));

                } else {
                    ops.push(storedOps.pop());
                }
            }

            //final run through to complete + and - operations
            while (!ops.isEmpty()) {
                //push the last variable to the working stack if exists
                if (!storedValues.isEmpty()) {
                    values.push(storedValues.pop());
                }

                //push the operation back to the stack
                values.push(operation(ops.pop(), values.pop(), values.pop()));
            }

            //final result get the last value from either values stack
            if (!values.isEmpty()) {
                return values.pop();
            } else {
                return storedValues.pop();
            }




        //*******************************************
        //3rd case, contains arrays, evaluate them and recurse with a new string
        } else if (expr.contains("[")){

            //initialize variables
            int startBracketIndex = expr.indexOf("[");
            int endBracketIndex = -1;
            int nesteds = 0;
            int endings = 0;
            int precedingOpIndex = -1;
            String preceding = "";
            String arrayName = "";
            String arrayIndexString = "";
            String trailing = "";
            String nuExpr = "";
            float subValue = 0;


            //find largest balanced array location
            for (int ptr = 0; ptr < expr.length(); ptr++) {
                if (expr.substring(ptr, ptr + 1).contains("[")) {
                    nesteds++;
                } else if (expr.substring(ptr, ptr + 1).contains("]")) {
                    endings++;
                    if (nesteds == endings) {
                        endBracketIndex = ptr;
                        break;
                    }
                }
            }

            //find the preceding oper
            for (int ptr = startBracketIndex-1; ptr >=0; ptr--) {
                if (expr.substring(ptr, ptr + 1).matches("[-+/*()]")) {
                    precedingOpIndex = ptr;
                    break;
                }
            }

            //create strings for the array name and its index
            //if exists substring the preceding string;
            if (precedingOpIndex > -1) {
                preceding = expr.substring(0, precedingOpIndex+1);
                arrayName = expr.substring(precedingOpIndex+1, startBracketIndex);
            }else{
                arrayName = expr.substring(0, startBracketIndex);
            }
            arrayIndexString = expr.substring(startBracketIndex+1, endBracketIndex);


            //if exists substring the trailing string;
            if (endBracketIndex < expr.length() - 1) {
                trailing = expr.substring(endBracketIndex + 1);
            }

            //evaluate the array index
            for (int i = 0; i < arrays.size(); i++) {
                if (arrays.get(i).name.equals(arrayName)) {
                    subValue = arrays.get(i).values[(int) evaluate(arrayIndexString, vars, arrays)];
                    break;
                }
            }


            nuExpr = preceding+(Float.toString(subValue))+trailing;

            //recursive call to evaluate the pre+(sub)+tail by finding and splicing in the toSting float result of the array;
            return evaluate(nuExpr, vars, arrays);




        //*******************************************
        //4th case contains parenthesis, evaluate them and recurse with a new string
        } else if (expr.contains("(")){

            //initialize variables
            int startBracketIndex = expr.indexOf("(");
            int endBracketIndex = -1;
            int nesteds = 0;
            int endings = 0;
            String preceding = "";
            String subexpression = "";
            String trailing = "";
            String nuExpr = "";
            float subValue = 0;


            //find largest balanced parenthesis locations
            for (int ptr = 0; ptr < expr.length(); ptr++) {
                if (expr.substring(ptr, ptr + 1).contains("(")) {
                    nesteds++;
                } else if (expr.substring(ptr, ptr + 1).contains(")")) {
                    endings++;
                    if (nesteds == endings) {
                        endBracketIndex = ptr;
                        break;
                    }
                }
            }



            //if exists substring the preceding string;
            if (startBracketIndex > 0) {
                preceding = expr.substring(0, startBracketIndex);
            }

            //find the largest parenthesis subexpression and substring the inside
            subexpression = expr.substring(startBracketIndex + 1, endBracketIndex);

            //if exists substring the trailing string;
            if (endBracketIndex < expr.length() - 1) {
                trailing = expr.substring(endBracketIndex + 1);
            }

            subValue = evaluate(subexpression, vars, arrays);


            nuExpr = preceding+(Float.toString(subValue))+trailing;

            //recursive call to evaluate the pre+(sub)+tail by finding and splicing in the toSting float result of the substring;
            return evaluate(nuExpr, vars, arrays);
        }


        //missed-steak
        return -1;

    }




    //helper method for switching the operations
    private static float operation(String op, float val, float val2){
        switch (op)
        {
            case "*":
                return val * val2;
            case "/":
                if(val2 == 0){
                    throw new IllegalArgumentException("Daddy Math here: Sorry kiddo you tried your hardest but you can't divide by zero, try again next time little buddy");
                } else {
                    return val / val2;
                }
            case "+":
                return val + val2;
            case "-":
                return val - val2;

        }
        return 0;
    }

}
