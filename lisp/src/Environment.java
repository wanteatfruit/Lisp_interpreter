import java.util.HashMap;

public class Environment {
    Environment father;
    HashMap<String,String> map;
    HashMap<String,Function> funcMap;

    public Environment(){
        //father=new Environment();
        map=new HashMap<String,String>();
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
    }

    Function getFunction(String key, Environment env){
        Function result=null;
        while(env!=null){
            if(env.funcMap.containsKey(key)){
                result = env.funcMap.get(key);
                break;
            }
            else{
                env = env.father;
            }
        }
        return result;
        
    }
}
