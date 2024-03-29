
import java.util.Stack;
public class ConstructTree {

    public static treeNode construct(String s, Environment outerEnv){
        String [] strings=new String[s.length()];
        Stack<treeNode> nodeStack=new Stack<>();
        treeNode root=new treeNode();

        //tokenizer
        for(int i=0;i<s.length();i++){

            //处理负操作数
            if (s.charAt(i) == '-' && isValid(s.charAt(i + 1))) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s.charAt(i));
                while (isValid(s.charAt(i + 1))) {
                    stringBuilder.append(s.charAt(i + 1));
                    i++;
                }
                strings[i] = stringBuilder.toString();
                
            }
            // 英文操作数或操作数
            else if (isValid(s.charAt(i))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s.charAt(i));
                while (isValid(s.charAt(i + 1))) {
                    stringBuilder.append(s.charAt(i + 1));
                    i++;
                }
                strings[i] = stringBuilder.toString();
            }
            //单字符操作符和括号
            else if(s.charAt(i)=='(' || s.charAt(i)==')' || s.charAt(i)=='+' 
            || s.charAt(i)=='*' || s.charAt(i)=='-' || s.charAt(i)=='/'
            || s.charAt(i)=='<' || s.charAt(i) == '>' || s.charAt(i) == '='){
                strings[i]= String.valueOf(s.charAt(i));
            }
            

        }

        //construct tree
        for(int i=0;i< strings.length;i++){
            if(strings[i]==null){
                continue;
            }
            //遇到左括号就新建一个节点，父节点为栈顶 新节点放到栈顶
            else if(strings[i].equals("(")){
                treeNode node;
                if(nodeStack.isEmpty()){
                    node=new treeNode();
                    root=node;
                    node.env.father=outerEnv;
                }
                else{
                    node=new treeNode(nodeStack.peek());
                    nodeStack.peek().nodeList.add(node);
                }
                nodeStack.push(node);
            }
            else if(strings[i].equals(")")){
                nodeStack.pop();
            }
            //提前判定lambda
            else if(strings[i].equals("lambda")){
                nodeStack.peek().nodeList.add(new treeNode(""));
                nodeStack.peek().val="lambda";
            }
            //判定是否惰性求值
            else if(strings[i].equals("if") || strings[i].equals("and") || strings[i].equals("or") ){
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
                setLazy(nodeStack.peek());
            }
            
            else{
                nodeStack.peek().nodeList.add(new treeNode(strings[i]));
            }
        }
        return root;

    }

    static void setLazy(treeNode node){
        node.lazy=true; //root node
        OperationType.setNodeType(node.nodeList.get(0).val, node); //set type from operator node
    }

    static boolean isValid(char c){
        if(c>='a' && c<='z' || c >= 'A' && c<='Z' || c>='0' && c<='9'){
            return true;
        }
        return false;
    }

}

