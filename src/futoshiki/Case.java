package futoshiki;
import java.awt.Component;
import javax.swing.JTextField;

public class Case extends JTextField 
{
	private static final long serialVersionUID = 1L;
	private int type;
	
	public void setType(int type) //setter pour type
	{
		this.type = type;
	}
	public int getType()//getter pour type
	{
		return type;
	}
	public Case(int type)//constructeur pour type
	{
		super();
		this.type = type;
	}
	
	public String printType()
	{
		switch(this.type)
		{
		case NUMBER : 				return "Number";
		case HORIZONTAL_SYMBOL : 	return ">"; 
		case VERTICAL_SYMBOL : 		return "^";
		case EMPTY : 				return "NaN"; 
		default : 					return "Err";
		}
	}
	
	//variable static pour les types de cases
	final static public int NUMBER = 0;
	final static public int HORIZONTAL_SYMBOL = 1;
	final static public int VERTICAL_SYMBOL = 2;
	final static public int EMPTY = -1;
}

