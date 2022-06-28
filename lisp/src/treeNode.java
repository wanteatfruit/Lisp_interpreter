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
        env=new Environment();
    }

    public treeNode(treeNode father){
        if(father==null){
            val = "";
            nodeList = new ArrayList<>();
            env = new Environment();
        }
        else{
            val = "";
            nodeList = new ArrayList<>();
            // env=father.env;
            // 指向同一个环境还是深复制一个？
            env = new Environment();
            env.map.putAll(father.env.map);
        }

    }


}
