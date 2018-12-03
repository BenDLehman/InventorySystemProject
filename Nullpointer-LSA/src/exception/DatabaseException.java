package exception;

public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message)
	{
		System.out.println(message);
	}

}
