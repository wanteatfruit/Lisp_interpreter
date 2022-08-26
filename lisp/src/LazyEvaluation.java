public class LazyEvaluation {
    treeNode root;
    OperationType type;

    public LazyEvaluation(treeNode root) {
        this.root = root;
    }

    public LazyEvaluation(treeNode root, OperationType type) {
        this.root = root;
        this.type = type;
    }

    void evaluateLazy() {
        if (type == OperationType.IF) {
            evaluateIf();
        }
    }

    void evaluateIf() {
        //eval condition first
        treeNode cond = root.nodeList.get(1);
        Main.evaluate(cond);

        if (cond.val.equals("true")) {
            // evaluate consequent
            treeNode csq = root.nodeList.get(2);
            if (csq.val == "") {
                Main.evaluate(csq);
            }
            root.val = csq.val;
        } else {
            treeNode alt = root.nodeList.get(3);
            if (alt.val == "") {
                Main.evaluate(alt);
            }
            root.val = alt.val;
        }
    }
}
