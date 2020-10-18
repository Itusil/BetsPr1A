package exceptions;

public class NumPreguntaNegativo extends Exception{

	public NumPreguntaNegativo()
	{
		super();
	}
	
	/**This exception is triggered if the question number is less than 0 
	 *@param s String of the exception
	 */
	public NumPreguntaNegativo(String s)
	{
		super(s);
	}
}
