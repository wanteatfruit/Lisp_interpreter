import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc =new Scanner(System.in);
        String[] input=new String[10000];
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
                //re-init
                idx=0;
                input = new String[10000];
            }
        }
        sc.close();
    }
    static void compile(String[] args){

        treeNode root=new treeNode(); //最外层的环境节点
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("c")){
                break;
            }
            treeNode curArg = ConstructTree.construct(args[i], root.env);
            //curArg.env.map.putAll(root.env.map);
            root.nodeList.add(curArg);
            //如果当前语句为define，则需要更新根环境，并将更新后的赋给下一条语句的运算
            evaluate(curArg);
            if(curArg.nodeList.get(0).val.equals("define")){
                root.env.map.putAll(root.nodeList.get(i).env.map);
            }
        }

        //print
        for (int i = 0; i < root.nodeList.size(); i++) {
            treeNode child=root.nodeList.get(i);
            if(!child.val.equals("")){
                System.out.println(child.val);
            }
        }
    }

    static void evaluate(treeNode node){
        if(node.nodeList.size()==0){
            return ;
        }
        //divide
        for(int i=1;i<node.nodeList.size();i++){
            evaluate(node.nodeList.get(i));
        }

        //conquer
        // String varPattern="^[a-zA-Z]+$";
        // String numPattern="^[0-9]+$";
        switch (node.nodeList.get(0).val){ //get(0) is operator, the rest are operands
            case "+":
                long val=0;
                for(int i=1;i<node.nodeList.size();i++){
                    String operand=getOperand(node, i);                    
                    if(operand!=null){
                        val += Long.parseLong(operand);
                    }
                    else{
                        val += 0;
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
            
            case "define": 
                //定义变量
                String key=node.nodeList.get(1).val; //variable name(key)
                String val4=getOperand(node, 2);//varibale value
                if(val4!=null){
                    node.env.map.put(key,val4);
                }
                else{
                    node.env.map.put(key, "null value");
                }
                break;
            case "lambda":
                
           

        }


    }

    static String getOperand(treeNode node, int i){
        String varPattern = "^[a-zA-Z0-9]+$";
        String numPattern = "^[0-9]+$";
        String operand = node.nodeList.get(i).val;
        if (operand.matches(numPattern)) {
            return node.nodeList.get(i).val;
        }
        else if (operand.matches(varPattern)) { // 如果为用户定义的变量
            ///// 变量不一定存在于当前的环境中，应当一级一级往上找
            String searchResult = node.env.getValue(operand, node.env);
            if (searchResult.equals("Doesn't exist.")) {
                System.out.println(searchResult);
            } else {
                return searchResult;
            }
        }
        return null; 
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
