package exceptions;
public class NoExistCategory extends Exception {
	private static final long serialVersionUID = 1L;

	public NoExistCategory()
	{
		super();
	}
	/**This exception is triggered if the question already exists 
	 *@param s String of the exception
	 */
	public NoExistCategory(String s)
	{
		super(s);
	}
}