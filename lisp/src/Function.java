import java.util.ArrayList;
import java.util.List;

public class Function { //(define (<函数名> <参数>) (<函数体>))
    treeNode root; //函数体的表达式树 （函数本身的环境是define括号内部的环境，也存在于这颗树里）
    List<String> args; //函数的参数
    Environment father; //函数的父环境 define这个括号外面的环境

    public Function(treeNode root,List<String> args,Environment father){
        this.root=root;
        this.args=args;
        this.father=father;
    }

    public Function(){
        this.root=new treeNode();
        this.args=new ArrayList<>();
        this.father=new Environment();
    }

}
