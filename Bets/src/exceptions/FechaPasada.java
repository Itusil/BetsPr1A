package exceptions;
public class FechaPasada extends Exception {
 private static final long serialVersionUID = 1L;
 
 public FechaPasada()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public FechaPasada(String s)
  {
    super(s);
  }
}