import java.util.ArrayList;

import java.util.List;

public class treeNode {
    List<treeNode> nodeList;
    String val;
    Environment env;
    boolean lazy = false;
    OperationType type;

    public treeNode(String val) {
        this.val = val;
        nodeList = new ArrayList<>();
        env = new Environment();
    }

    public treeNode(String val, boolean lazy) {
        this.val = val;
        nodeList = new ArrayList<>();
        env = new Environment();
        this.lazy = lazy;
    }

    public treeNode() {
        val = "";
        nodeList = new ArrayList<>();
        env = new Environment();
    }

    public treeNode(String val, OperationType type) {
        this.val = val;
        nodeList = new ArrayList<>();
        env = new Environment();
        this.type = type;
    }

    public treeNode(treeNode father) {
        if (father == null) {
            val = "";
            nodeList = new ArrayList<>();
            env = new Environment();
        } else {
            val = "";
            nodeList = new ArrayList<>();

            // 指向同一个环境还是深复制一个？
            env = new Environment();
            env.father = father.env;
            // env.map.putAll(father.env.map);
        }

    }

}
