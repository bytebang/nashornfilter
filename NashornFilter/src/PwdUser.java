
/**
 * A simple class that represents a line within a `/etc/passwd` file.
 * 
 * @author bytebang
 *
 */
public class PwdUser 
{
	public String userid;
	public String password;
	public Integer uid;
	public Integer gid;
	public String fullname;
	public String homedir;	
	public String shell;
	
	/**
	 * ctor which creates an object and fills all the (shame on me) public properties.
	 * @param pwdline
	 */
	public PwdUser(String pwdline)
	{
		String[] values = pwdline.split(":");
		
		if(values.length != 7)
		{
			throw new IllegalArgumentException("Line must have 7 fields");
		}
		
		this.userid = values[0];
		this.password = values[1];
		this.uid= Integer.parseInt(values[2]);
		this.gid= Integer.parseInt(values[3]);
		this.fullname = values[4];
		this.homedir = values[5];
		this.shell = values[6];
	}

	/**
	 * Prints the Object and its properties
	 */
    @Override
    public String toString()
    {
        return "PwdUser [userid=" + userid + ", password=" + password + ", uid=" + uid + ", gid=" + gid + ", fullname="
                + fullname + ", homedir=" + homedir + ", shell=" + shell + "]";
    }
}
