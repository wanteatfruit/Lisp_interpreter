import java.util.ArrayList;
import java.util.List;

public class Function {
    treeNode tree;
    List<String> args;
    Environment father;

    public Function(treeNode root,List<String> args,Environment father){
        this.tree=root;
        this.args=args;
        this.father=father;
    }

    public Function(){
        this.tree=new treeNode();
        this.args=new ArrayList<>();
        this.father=new Environment();
    }

}
