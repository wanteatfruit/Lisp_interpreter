
public class LazyEvaluation {
    treeNode root;
    OperationType type;

    public LazyEvaluation(treeNode root) {
        this.root = root;
        OperationType.setLazyObjType(this.root.nodeList.get(0).val, this);
    }

    public LazyEvaluation(treeNode root, OperationType type) {
        this.root = root;
        this.type = type;
    }

    void evaluateLazy() {
        if (type == OperationType.IF) {
            evaluateIF();
        }
        else if(type==OperationType.AND){
            evaluateAND();
        }
        else if(type==OperationType.OR){
            evaluateOR();
        }
    }

    void evaluateIF() {
        //eval condition first
        treeNode cond = root.nodeList.get(1);
        Main.evaluate(cond);
        if (cond.val.equals("true")) {
            // eval consequent
            treeNode csq = root.nodeList.get(2);
            if (csq.val == "") {
                Main.evaluate(csq);
            }
            root.val = csq.val;
        } else {
            // eval alternate
            treeNode alt = root.nodeList.get(3);
            if (alt.val == "") {
                Main.evaluate(alt);
            }
            root.val = alt.val;
        }
    }

    //(and (> 1 0) (< 1 0))
    void evaluateAND(){
        //短路求值
        for (int i = 1; i < root.nodeList.size(); i++) {
            treeNode operand = root.nodeList.get(i);
            Main.evaluate(operand); //eval每个子操作
            if(operand.val.equals("false")){
                root.val="false";
                break;
            }
        }
    }

    void evaluateOR(){
        //短路求值
        for (int i = 1; i < root.nodeList.size(); i++) {
            treeNode operand = root.nodeList.get(i);
            Main.evaluate(operand);
            if(operand.val.equals("true")){
                root.val="true";
                break;
            }
        }
    }
}
