import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class treeNode{
    List<treeNode> nodeList;

    String val;

    Environment env;

    public treeNode(String val){
        this.val=val;
        nodeList=new ArrayList<>();
        env=new Environment();
    }

    public treeNode(){
        val="";
        nodeList=new ArrayList<>();
    }


}
