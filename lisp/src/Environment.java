import java.util.HashMap;

public class Environment {
    Environment father;
    HashMap<String,String> map;
    HashMap<String,Function> funcMap;

    public Environment(){
        //father=new Environment();
        map=new HashMap();
        funcMap=new HashMap<>();
    }

    public Environment(Environment father,HashMap<String,String> map){
        this.father=father;
        this.map=map;
    }


    String getValue(String key,Environment env){
        String result="";
        while(env!=null){
            if(env.map.containsKey(key)){
                result = env.map.get(key);
                break;
            }
            else{
                env = env.father;
            }
        }
        return result;
        // if(env.map.containsKey(key)){
        //     result = env.map.get(key);
        //     return result;
        // }
        // else if(env.father==null){
        //     return "Doesn't exist.";
        // }
        // else{
        //     getValue(key,env.father,result);
        // }
        // return result;

    }

    Function getFunction(String name, Environment env){
        if(env.funcMap.containsKey(name)){
            return env.funcMap.get(name);
        }
        else if(env.father==null){
            return null;
        }
        else{
            getFunction(name, env.father);
        }
        return null;
    }
}
