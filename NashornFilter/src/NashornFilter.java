
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Filter object which can be used in streams to filter objects by a 
 * simple javascript expression.
 * 
 * @author bytebang
 *
 */
public class NashornFilter
{
    ScriptEngine engine;
    String script;
    Boolean defaultValue = null;
    String scriptVariableName = "o";
    
    /**
     * ctor
     * @param script The script which checks the object properties
     * @see https://docs.oracle.com/javase/9/nashorn/JSNUG.pdf
     */
    public NashornFilter(String script)
    {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
        this.script = script;
    }
    
    /**
     * Determines what to do if there occurs an error during the check of the script.
     * 
     * @param defaultValue
     * @return this object - used for fluent API
     */
    public NashornFilter onErrorDefaultTo(Boolean defaultValue)
    {
        this.defaultValue = defaultValue;
        return this;
    }


    /**
     * Sets the name of the variable inside the script that references the java object
     * 
     * @param scriptVariableName Name of the variable inside the javascript which refereces the object that should be checked
     * @return this object - used for fluent API
     */
    public NashornFilter withScriptVariableName(String scriptVariableName)
    {
        this.scriptVariableName = scriptVariableName;
        return this;
    }


    /**
     * Executes the given javascript and injects the current object into the scope of the script.
     * 
     * @param obj The object which should be checked against the given script
     * @return result of the script execution or in case of an error the default value
     */
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
