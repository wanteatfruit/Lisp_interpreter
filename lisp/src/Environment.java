import java.util.HashMap;

public class Environment {
    Environment father;
    HashMap<String,String> map;

    public Environment(){
        //father=new Environment();
        map=new HashMap();
    }

    public Environment(Environment father,HashMap map){
        this.father=father;
        this.map=map;
    }


    static Object getValue(String key,Environment env){
        if(env.map.containsKey(key)){
            return env.map.get(key);
        }
        else if(env.father==null){
            return "Doesn't exist.";
        }
        else{
            getValue(key,env.father);
        }
        return null;
    }
}
