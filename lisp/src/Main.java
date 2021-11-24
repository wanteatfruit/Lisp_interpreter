import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        while(true) {
            String s = sc.nextLine();

            if(s.equals("-1")){
                break;
            }
            treeNode cal = ConstructTree.construct(s); //build lisp tree

            evaluate(cal.nodeList.get(0), cal.env); //call function

            System.out.println(cal.nodeList.get(0).val);
        }
    }

    static void evaluate(treeNode node,Environment env){
        //reach the end
        if(node.nodeList.size()==0){
            return ;
        }

        //recursion
        for(int i=1;i<node.nodeList.size();i++){
            evaluate(node.nodeList.get(i),node.env);
        }


        switch (node.nodeList.get(0).val){ //get(0) is operator, the rest are operands
            case "+":
                long val=0;
                for(int i=1;i<node.nodeList.size();i++){
                    val=val+Integer.parseInt(node.nodeList.get(i).val);
                }
                node.val=String.valueOf(val);

                break;
            case "-":
                long val1=0;
                for(int i=1;i<node.nodeList.size();i++){
                    val1=val1-Integer.parseInt(node.nodeList.get(i).val);
                }
                node.val=String.valueOf(val1);
                break;

            case "*":
                long val2=1;
                for(int i=1;i<node.nodeList.size();i++){
                    val2=val2*Integer.parseInt(node.nodeList.get(i).val);
                }
                node.val=String.valueOf(val2);

                break;
            case "/":
                long val3=1;
                for(int i=1;i<node.nodeList.size();i++){
                    val3=val3/Integer.parseInt(node.nodeList.get(i).val);
                }
                node.val=String.valueOf(val3);
                break;
            case "define": //put key and val into current environment
                String key=node.nodeList.get(1).val;
                String val4=node.nodeList.get(2).val;
                node.env.map.put(key,val4);
                break;
            case "defineFunc": //defineFunc is operator, get(1) is function name, get(2) is expression
                //evalFunc();

        }


    }

    static Object evalFunc(Function function, List args){
        Environment env=new Environment();
        for(int i=0;i<args.size();i++){
            env.map.put(String.valueOf(i), (String) args.get(i));
        }
        env.father=function.father;
        evaluate(function.tree,env);
        return function.tree.val;
    }

}
