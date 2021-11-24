import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstructTree {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String in=sc.nextLine();
        String [] strings=new String[in.length()];
        char[] chars=in.toCharArray();
        Stack<treeNode> nodeStack=new Stack<>();
        treeNode root=new treeNode();

        nodeStack.push(root);

        String patNum="/?[0-9]+";

        //split string into nums and parenthesis and operators

        for(int i=0;i<in.length();i++){

            if(in.charAt(i)=='(' || in.charAt(i)==')' || in.charAt(i)=='+' || in.charAt(i)=='*'){
                strings[i]= String.valueOf(in.charAt(i));
            }
            if(in.charAt(i)>='0' && in.charAt(i)<='9'){
                String num=String.valueOf(in.charAt(i));
                while (in.charAt(i+1)>='0' && in.charAt(i+1)<='9'){
                    num=num+in.charAt(i+1);
                    i++;
                }
                strings[i]=num;
            }
            if(in.charAt(i)>='a' && in.charAt(i)<='z'){
                String operate=String.valueOf(in.charAt(i));
                while (in.charAt(i+1)>='a' && in.charAt(i+1)<='z'){
                    operate=operate+in.charAt(i);
                    i++;
                }
            }
        }

        //construct tree

        for(int i=0;i< chars.length;i++){
            if(strings[i]==null){
                continue;
            }
            else if(strings[i].equals("(")){
                treeNode node=new treeNode();
                nodeStack.peek().nodeList.add(node);
                nodeStack.push(node);
            }
            else if(strings[i].equals("+") || strings[i].equals("*") || strings[i].equals("-") || strings[i].equals("/")){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
            else if(strings[i].matches(patNum)){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
            else if(strings[i].equals(")")){
                nodeStack.pop();
            }
            else{
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
        }
        System.out.println();

        System.out.print(root);


    }

    public static treeNode construct(String s){
        String [] strings=new String[s.length()];
        char[] chars=s.toCharArray();
        Stack<treeNode> nodeStack=new Stack<>();
        treeNode root=new treeNode();

        nodeStack.push(root);

        String patString="^[A-Za-z0-9]+$";

        //split string into nums and parenthesis and operators

        for(int i=0;i<s.length();i++){

            if(s.charAt(i)=='(' || s.charAt(i)==')' || s.charAt(i)=='+' || s.charAt(i)=='*'){
                strings[i]= String.valueOf(s.charAt(i));
            }
            if(s.charAt(i)>='a' && s.charAt(i)<='z'){
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(s.charAt(i));
                while (s.charAt(i+1)>='a' && s.charAt(i+1)<='z'){
                    stringBuilder.append(s.charAt(i+1));
                    i++;
                }
                strings[i]=stringBuilder.toString();
            }
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

        for(int i=0;i< chars.length;i++){
            if(strings[i]==null){
                continue;
            }
            if(strings[i].equals("(")){
                treeNode node=new treeNode();
                nodeStack.peek().nodeList.add(node);
                nodeStack.push(node);
            }
            if(strings[i].equals("+") || strings[i].equals("*") || strings[i].equals("-") || strings[i].equals("/")){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
            if(strings[i].matches(patString)){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
            if(strings[i].equals(")")){
                nodeStack.pop();
            }
        }
        return root;

    }

}

