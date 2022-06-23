import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        String[] input=new String[10000];
        ///////改变输入方式？目前只能一行行读取，无记忆性
        int idx = 0;
        while(true) {
            
             String s = sc.nextLine();
            input[idx]=s;
            idx++;
            if(s.equals("-1")){
                break;
            }
            if(s.equals("c")){
                compile(input);
            }
        }
    }

    static void compile(String[] args){
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("c"))
                break;
            treeNode cal = ConstructTree.construct(args[i]); // build expression tree
            ////// 当操作数中出现字符时需要从环境中拿（在建树的时候就拿到/evaluate时拿到）惰性求值？
            evaluate(cal.nodeList.get(0));
            System.out.println(cal.nodeList.get(0).val);
        }
    }

    static void evaluate(treeNode node){
        //reach the end
        if(node.nodeList.size()==0){
            return ;
        }
        //recursion
        for(int i=1;i<node.nodeList.size();i++){
            evaluate(node.nodeList.get(i));
        }

        String varPattern="^[a-zA-Z]+$";
        String numPattern="^[0-9]+$";
        switch (node.nodeList.get(0).val){ //get(0) is operator, the rest are operands
            case "+":
                long val=0;
                for(int i=1;i<node.nodeList.size();i++){
                    String operand=node.nodeList.get(i).val;
                    if(operand.matches(varPattern)){ //get from env
                        /////变量不一定存在于当前的环境中，应当一级一级往上找
                        long valKey=(long)node.env.getValue(operand, node.env);
                        val+=valKey;
                    }
                    else if(operand.matches(numPattern)){
                        val = val + Integer.parseInt(node.nodeList.get(i).val);
                    }
                }
                node.val=String.valueOf(val);
                break;
            case "-":
                long val1=Integer.parseInt(node.nodeList.get(1).val);
                for(int i=2;i<node.nodeList.size();i++){
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
                long val3 = Integer.parseInt(node.nodeList.get(1).val);
                for(int i=2;i<node.nodeList.size();i++){
                    val3=val3/Integer.parseInt(node.nodeList.get(i).val);
                }
                node.val=String.valueOf(val3);
                break;
            
            case "define": //(define key value) 相当于把变量名和变量值看作键值对，放到当前节点的环境
                String key=node.nodeList.get(1).val; //variable name(key)
                String val4=node.nodeList.get(2).val;//varibale value
                node.env.map.put(key,val4);
                break;
            case "lambda":
                
           

        }


    }

    static Object evalFunc(Function function, List args){
        Environment env=new Environment();
        for(int i=0;i<args.size();i++){
            env.map.put(String.valueOf(i), (String) args.get(i));
        }
        env.father=function.father;
        evaluate(function.tree);
        return function.tree.val;
    }

}
