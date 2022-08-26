public enum OperationType {

    IF, COND, AND, OR,
    OTHER; // no need lazy

    static void setNodeType(String val, treeNode node) {
        switch (val) {
            case "if":
                node.type = IF;
                break;
            case "and":
                node.type = AND;
                break;
            case "or":
                node.type = OR;
                break;
            case "cond":
                node.type = COND;
                break;
            default:
                node.type = OTHER;
        }
    }

    static void setLazyObjType(String val, LazyEvaluation node){
        switch (val) {
            case "if":
                node.type = IF;
                break;
            case "and":
                node.type = AND;
                break;
            case "or":
                node.type = OR;
                break;
            case "cond":
                node.type = COND;
                break;
            default:
                node.type = OTHER;
        }

    }
}
