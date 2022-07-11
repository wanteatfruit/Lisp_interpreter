import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static String varPattern = "^[a-zA-Z0-9]+$";
    static String numPattern = "^[0-9]+$";
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
            //curArg.env.father = root.env;
            //curArg.env.map.putAll(root.env.map);
            root.nodeList.add(curArg);
            //如果当前语句为define，则需要更新根环境，并将更新后的赋给下一条语句的运算
            evaluate(curArg);
            if(curArg.nodeList.get(0).val.equals("define")){
                root.env.map.putAll(root.nodeList.get(i).env.map);
                root.env.funcMap.putAll(root.nodeList.get(i).env.funcMap);
            }
            if(!curArg.val.equals("")){
                System.out.println(curArg.val);
            }
        }
    }

    static void evaluate(treeNode node){
        if(node.nodeList.size()==0){
            return ;
        }
        //divide
        //先检查操作符，如果是define则跳过不evaluate
        //如果是空，则认为是函数or lambda
        String op = node.nodeList.get(0).val;
        if(op.equals("define") && node.nodeList.get(1).val.equals("")){
            //定义函数
        }
        else if(op.equals("define") && node.nodeList.get(2).val.equals("lambda")){
            //把lambda看作变量

        }
        //lambda并且未evaluate过
        //从lambda外的节点看
        else if(op.equals("lambda") && node.nodeList.get(0).nodeList.size()!=0 
                && node.nodeList.get(0).nodeList.get(2).val.equals("")){
            //知道lambda是包在括号外面的，可以提前拿到参数列表传给list[0]，之后evaluate进入list[0]
            List<treeNode> lambdaList = new ArrayList<>();
            for (int i = 1; i < node.nodeList.size(); i++) {
                lambdaList.add(node.nodeList.get(i));
            }
            evaluateLambda(node.nodeList.get(0), lambdaList);
            //evaluate(node.nodeList.get(0));
        }
        //lambda并且evaluate过
        //进入了lambda节点
        else if(node.val.equals("lambda")
                && !node.nodeList.get(2).val.equals("")){
            //直接return
            return;
        }
        else{
            for(int i=1;i<node.nodeList.size();i++){
                int lambdaArgsCnt = 0;
                if(node.nodeList.get(i).val.equals("lambda")){
                    //evaluateLambda(lambda, args);
                    List<treeNode> lambdList = new ArrayList<>();
                    for (int j = i+1; j < node.nodeList.size(); j++) {
                        lambdList.add(node.nodeList.get(j));
                    }
                    lambdaArgsCnt = evaluateLambda(node.nodeList.get(i), lambdList);
                }
                else{
                    evaluate(node.nodeList.get(i));
                }
                //会导致重复eval
                //需跳过
                //evaluate(node.nodeList.get(i));
                i += lambdaArgsCnt;
                
            }
        }

        //conquer
        switch (op){ //get(0) is operator, the rest are operands
            case "+":
                long val=0;
                for(int i=1;i<node.nodeList.size();i++){
                    int skipCnt = skip(node, i);
                    String operand=getOperand(node, i);                    
                    if(operand!=null){
                        val += Long.parseLong(operand);
                    }
                    else{
                        val += 0;
                    } 
                    i+=skipCnt;
                }
                node.val=String.valueOf(val);
                break;
            case "-":
                long val1=0;
                String operandInit = getOperand(node, 1);
                val1 = Long.parseLong(operandInit);
                for (int i = 2; i < node.nodeList.size(); i++) {
                    String operand = getOperand(node, i);
                    if (operand != null) {
                        val1 -= Long.parseLong(operand);
                    } else {
                        val1 -= 0;
                    }
                }
                node.val=String.valueOf(val1);
                break;

            case "*":
                long val2=1;
                for (int i = 1; i < node.nodeList.size(); i++) {
                    String operand = getOperand(node, i);
                    if (operand != null) {
                        val2 *= Long.parseLong(operand);
                    } else {
                        val2 *= 1;
                    }
                }
                node.val=String.valueOf(val2);
                break;
            case "/":
                long val3 = 0;
                String operandInit1 = getOperand(node, 1);
                val3 = Long.parseLong(operandInit1);
                for (int i = 2; i < node.nodeList.size(); i++) {
                    String operand = getOperand(node, i);
                    if (operand != null) {
                        val3 /= Long.parseLong(operand);
                    } else {
                        val3 /= 1;
                    }
                }
                node.val=String.valueOf(val3);
                break;
            
            case "=":
                boolean equals = false;
                String operandEqual1 = getOperand(node, 1);
                String operandEqual2 = getOperand(node, 2);
                if(Long.parseLong(operandEqual1)==Long.parseLong(operandEqual2)){
                    equals = true;
                }
                node.val = String.valueOf(equals);
                break;
            case ">":
                boolean bigger = false;
                String operandBeq1 = getOperand(node, 1);
                String operandBeq2 = getOperand(node, 2);
                if(Long.parseLong(operandBeq1) > Long.parseLong(operandBeq2)){
                    bigger = true;
                }
                node.val = String.valueOf(bigger);
                break;
            case "<":
                boolean smaller = false;
                String operandLeq1 = getOperand(node, 1);
                String operandLeq2 = getOperand(node, 2);
                if(Long.parseLong(operandLeq1) < Long.parseLong(operandLeq2)){
                    smaller = true;
                }
                node.val = String.valueOf(smaller);
                break;
            case "and":
                boolean andResult = true;
                for (int i = 1; i < node.nodeList.size(); i++) {
                    treeNode operandAnd = node.nodeList.get(i);
                    if(operandAnd.val.equals("false")){
                        andResult = false;
                        break;
                        //短路求值
                    }
                }
                node.val = String.valueOf(andResult);
                break;
            
            case "or":
                boolean orResult = false;
                for (int i = 1; i < node.nodeList.size(); i++) {
                    treeNode operandOr = node.nodeList.get(i);
                    if(operandOr.val.equals("true")){
                        orResult = true;
                        break;
                        //短路求值
                    }
                }
                node.val = String.valueOf(orResult);
                break;

            case "cond":
            //没有实现惰性求值，会把每个括号中的值都先算一遍
                /*(define (abs x) (cond (> x 0) (x) 
                                        (= x 0) (0)
                                        (else (+ x 1)))
                                    */
                int actionIndex = 2; //even number for action
                //check each condition
                for (int i = 1; i < node.nodeList.size(); i+=2) {
                    treeNode conditionNode = node.nodeList.get(i);
                    if(conditionNode.val.equals("true")){
                        treeNode actionNode = node.nodeList.get(actionIndex);
                        node.val = actionNode.val;
                        break;
                    }
                    else if(conditionNode.val.equals("else")){
                        node.val = conditionNode.val;
                    }
                    else{
                        actionIndex+=2;
                        continue;
                    }
                }
                break;
            case "if":
                /*
                 * (if (< x 0) (-x)
                 *              x)
                 */
                treeNode conditionIf = node.nodeList.get(1);
                treeNode consequentNode = node.nodeList.get(2);
                treeNode alternateNode = node.nodeList.get(3);
                if(conditionIf.val.equals("true")){
                    node.val = consequentNode.val;
                }
                else {
                    node.val = alternateNode.val;
                }
                break;

            case "else":
                // treeNode actionElse = node.nodeList.get(1);
                // node.val = actionElse.val;
                 break;

            
            case "define": 
                int type; //0为定义变量，1为定义函数,2为lambda
                if(node.nodeList.get(1).val.equals("")){
                    type=1;
                }
                else if (node.nodeList.get(2).val.equals("lambda")){
                    type = 2;
                }
                else{
                    type = 0;
                }
                if(type==0){
                    String key=node.nodeList.get(1).val; //variable name(key)
                    String val4=getOperand(node, 2);//value
                    node.env.map.put(key,val4);
                }
                if (type == 2) {
                    // 转换成define普通函数的语句
                    // (define plus4 (lambda (x) (+ x 4))) => (define (plus4 x) (+ x 4))
                    //函数名：list[1]
                    //lambda表达式: list[2]
                    //参数列表：lambda.list[1]
                    //函数体：lambda.list[2]
                    String functionName = node.nodeList.get(1).val; 
                    treeNode lambdaFunction = node.nodeList.get(2);
                    treeNode lambdaArgument = lambdaFunction.nodeList.get(1);
                    treeNode lambdaBody = lambdaFunction.nodeList.get(2);
                    Function function = new Function();
                    for (int i = 0; i < lambdaArgument.nodeList.size(); i++) {
                        function.args.add(lambdaArgument.nodeList.get(i).val);
                    }
                    if (!lambdaBody.nodeList.get(0).val.equals("")) {
                        treeNode functionRoot = new treeNode(node);
                        function.root = functionRoot;
                        functionRoot.nodeList.add(lambdaBody);
                    } else {
                        function.root = lambdaBody; // 添加函数体给函数
                    }
                    function.father = node.env; // 添加父环境给函数
                    node.env.funcMap.put(functionName, function); // 给环境添加函数
                    function.root.env.father = node.env;
                    updateFather(function.root);
                }
                if(type==1){
                    //定义函数 (define (mul x y) (* x y))   (mul 1 3)
                    //list[1]是参数列表，不会被evaluate
                    //list[2:]是函数体
                    treeNode functionDef=node.nodeList.get(1);
                    treeNode functionBody = node.nodeList.get(2);
                    //list[1]的儿子：[0]函数名 [1:]参数列表
                    Function function=new Function();
                    String functionName = functionDef.nodeList.get(0).val;
                    //添加参数列表给函数
                    for (int i = 1; i < functionDef.nodeList.size(); i++) {
                        function.args.add(functionDef.nodeList.get(i).val);
                    }
                    /////////函数体建一个单独的root节点，这个节点存函数的环境////////////////
                    //函数体为单条语句，给他新建一个root
                    if(!functionBody.nodeList.get(0).val.equals("")){
                        treeNode functionRoot = new treeNode(node);
                        function.root = functionRoot;
                        functionRoot.nodeList.add(functionBody);
                    }
                    else{
                        function.root = functionBody; //添加函数体给函数
                    }
                    function.father = node.env; //添加父环境给函数
                    node.env.funcMap.put(functionName, function); //给环境添加函数
                    function.root.env.father=node.env;
                    updateFather(function.root);
                }

                break;
            case "lambda":
            //已经运算过的值

                node.val = node.nodeList.get(0).nodeList.get(2).val;
                break;

            
            default: 
                //非关键字时（第一个参数为用户定义）调用函数
                //另外的为参数
                String funName = node.nodeList.get(0).val;
                List<treeNode> args=new ArrayList<>();
                for (int i = 1; i < node.nodeList.size(); i++) {
                    args.add(node.nodeList.get(i));    
                }
                evaluateFunction(funName, args, node);
        }
    }

    static String getOperand(treeNode node, int i){

        String operand = node.nodeList.get(i).val;
        if (operand.matches(numPattern)) {
            return node.nodeList.get(i).val;
        }
        else if (operand.matches(varPattern)) { // 如果为用户定义的变量
            ///// 变量不一定存在于当前的环境中，应当一级一级往上找
            String searchResult = node.env.getValue(operand, node.env);
            if (searchResult.equals("")) {
                System.out.println(searchResult);
            } else {
                return searchResult;
            }
        }
        return null; 
    }

    static void evaluateFunction(String name, List<treeNode> args, treeNode caller){
        //从环境对应函数名拿到函数体的表达式树
        //代入参数evaluate函数体
        //Function function = caller.env.funcMap.get(name);
        Function function = caller.env.getFunction(name, caller.env);
        treeNode functionRoot = function.root;
        List<String> formalArg = function.args;
        for (int i = 0; i < args.size(); i++) {
            evaluate(args.get(i));
            String actualArg = args.get(i).val;
            if(!actualArg.matches(numPattern)){
                actualArg = getOperand(actualArg, caller.env);
            }
            functionRoot.env.map.put(formalArg.get(i), actualArg);
        }
       
        //从左到右eval函数中每条语句
        for (int i = 0; i < functionRoot.nodeList.size(); i++) {
            treeNode child = functionRoot.nodeList.get(i);
            evaluate(child);
            if(child.nodeList.get(0).val.equals("define")){
                functionRoot.env.map.putAll(child.env.map);
                functionRoot.env.funcMap.putAll(child.env.funcMap);
            }
            else{
                functionRoot.val = child.val;
            }
        }
        caller.val = functionRoot.val; //返回值
        
    }

    ////在这里evaluate lambda expression
    ////返回lambda参数个数
    static int evaluateLambda(treeNode lambdaNode, List<treeNode> actualArgs){
        Function lambda = new Function();
        List<String> lambdaArgs = new ArrayList<>();

        lambda.father = lambdaNode.env;
        treeNode lambdaArgsNode = lambdaNode.nodeList.get(1);
        for (int i = 0; i < lambdaArgsNode.nodeList.size(); i++) {
            lambdaArgs.add(lambdaArgsNode.nodeList.get(i).val);
        }
        lambda.args = lambdaArgs;
        treeNode lambdaBody = lambdaNode.nodeList.get(2);
        if (!lambdaBody.nodeList.get(0).val.equals("")) {
            treeNode lambdaRoot = new treeNode(lambdaNode);
            lambda.root = lambdaRoot;
            lambdaRoot.nodeList.add(lambdaBody);
        } else {
            lambda.root = lambdaBody;
        }
        updateFather(lambda.root);

        //代入参数
        for (int i = 0; i < lambdaArgs.size(); i++) {
            evaluate(actualArgs.get(i));
            String actualArg = actualArgs.get(i).val;
            if (!actualArg.matches(numPattern)) {
                actualArg = getOperand(actualArg, lambdaNode.env);
            }
            lambdaBody.env.map.put(lambdaArgs.get(i), actualArg);
        }
    
        //求值
        // 从左到右eval函数中每条语句
        for (int i = 0; i < lambda.root.nodeList.size(); i++) {
            treeNode child = lambda.root.nodeList.get(i);
            evaluate(child);
            if (child.nodeList.get(0).val.equals("define")) {
                lambda.root.env.map.putAll(child.env.map);
                lambda.root.env.funcMap.putAll(child.env.funcMap);
            } else {
                lambda.root.val = child.val;
            }
        }

        evaluate(lambdaBody);

        return lambdaArgs.size();
    }

    static int skip(treeNode node, int i){
        if(node.nodeList.get(i).nodeList.size()==0){
            return 0;
        }
        else if(node.nodeList.get(i).nodeList.get(0).val.equals("lambda")){
            return node.nodeList.get(i).nodeList.get(1).nodeList.size();
        }
        else return 0;
    }

    //当函数的参数是变量时，用此方法替换成数字
    static String getOperand(String actual, Environment env){
        String op = env.getValue(actual, env);
        return op;
    }

    

    //用bfs更新一遍每个节点的父环境
    static void updateFather(treeNode root){
        Queue<treeNode> nodes=new LinkedList<>();
        nodes.add(root);
        while(!nodes.isEmpty()){
            root = nodes.poll();
            for (int i = 0; i < root.nodeList.size(); i++) {
                treeNode child = root.nodeList.get(i);
                child.env.father = root.env;
                nodes.add(child);    
            }
        }
    }

}
