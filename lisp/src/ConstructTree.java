import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstructTree {

    /*
     * list of operators/keywords:
     *  + - * /
     *  define
     *  lambda
     *  ()
     *  
     */

    public static treeNode construct(String s){
        String [] strings=new String[s.length()];
        Stack<treeNode> nodeStack=new Stack<>();
        treeNode root=new treeNode();

        nodeStack.push(root);

        //use regex expressions to match strings
        String operand="^[A-Za-z0-9]+$";  
        String mathOperator="^[+-/*//]*$";
        String wordOperator="^(lambda|define)$";

        //tokenizer

        for(int i=0;i<s.length();i++){
            
            //单字符操作符和括号
            if(s.charAt(i)=='(' || s.charAt(i)==')' || s.charAt(i)=='+' 
            || s.charAt(i)=='*' || s.charAt(i)=='-' || s.charAt(i)=='/'){
                strings[i]= String.valueOf(s.charAt(i));
            }
            // //多字符操作符(define, lambda)等
            // if(s.charAt(i)>='a' && s.charAt(i)<='z'){
            //     StringBuilder stringBuilder=new StringBuilder();
            //     stringBuilder.append(s.charAt(i));
            //     while (s.charAt(i+1)>='a' && s.charAt(i+1)<='z'){
            //         stringBuilder.append(s.charAt(i+1));
            //         i++;
            //     }
            //     strings[i]=stringBuilder.toString();
            // }
            //英文操作数或操作数
            if(s.charAt(i)>='a' && s.charAt(i)<='z' || s.charAt(i) >= 'A' && s.charAt(i)<='Z'){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s.charAt(i));
                while (s.charAt(i + 1) >= 'a' && s.charAt(i + 1) <= 'z') {
                    stringBuilder.append(s.charAt(i + 1));
                    i++;
                }
                strings[i] = stringBuilder.toString();
            }
            //数字操作数
            if(s.charAt(i)>='0' && s.charAt(i)<='9'){
                String num=String.valueOf(s.charAt(i));
                while (s.charAt(i+1)>='0' && s.charAt(i+1)<='9'){
                    num=num+s.charAt(i+1);
                    i++;
                }
                strings[i]=num;
            }
        }

        //construct tree

        for(int i=0;i< strings.length;i++){
            if(strings[i]==null){
                continue;
            }
            //遇到左括号就新建一个节点，父节点为栈顶 新节点放到栈顶
            else if(strings[i].equals("(")){
                treeNode node=new treeNode(nodeStack.peek());
                nodeStack.peek().nodeList.add(node);
                nodeStack.push(node);
            }
            //操作符
            else if(strings[i].matches(wordOperator)||strings[i].matches(mathOperator)){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
            // if(strings[i].equals("+") || strings[i].equals("*") 
            // || strings[i].equals("-") || strings[i].equals("/")||strings[i].equals(operator)){
            //     nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            // }
            //操作数
            else if(strings[i].matches(operand)){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
            else if(strings[i].equals(")")){
                nodeStack.pop();
            }
        }
        return root;

    }

}

