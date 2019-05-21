
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class NashornFilter
{
    ScriptEngine engine;
    String script;
    Boolean defaultValue = null;
    String scriptVariableName = "o";
    
    public NashornFilter(String script)
    {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
        this.script = script;
    }
    
    
    
    public NashornFilter onErrorDefaultTo(Boolean defaultValue)
    {
        this.defaultValue = defaultValue;
        return this;
    }



    public NashornFilter withScriptVariableName(String scriptVariableName)
    {
        this.scriptVariableName = scriptVariableName;
        return this;
    }



    public Boolean eval(Object obj)
    {
        try
        {   engine.put(this.scriptVariableName, obj);
            return (boolean) engine.eval(script);
        }
        catch(Exception e)
        {
            if(defaultValue == null)
            {
                throw new IllegalArgumentException(e);
            }
        }
        return defaultValue;
    }
    
    
}
