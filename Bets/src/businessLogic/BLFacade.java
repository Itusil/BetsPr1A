package businessLogic;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Apuestas;
import domain.Categoria;
import domain.Event;
import domain.Mensaje;
import domain.Pronostico;
//import domain.Booking;
import domain.Question;
import domain.Usuario;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.FechaPasada;
import exceptions.NoExistCategory;
import exceptions.NumPreguntaNegativo;
import exceptions.QuestionAlreadyExist;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	
	/**
	 * Obtiene en Usuario a traves del nombre de usuario
	 * @param nombreUsuario
	 * @return
	 */
	public Usuario getUserByNombreUsuario(String nombreUsuario);
	
	
	/**
	 * Dados los datos de un pronostico, lo crea y lo guarda en la base de datos
	 * @param pronostico
	 * @param cuota
	 * @param preguntaPronostico
	 */
	public void anadirPronostico(String pronostico, double cuota, Question preguntaPronostico);
	
	
	/**
	 * Dado un nombre de usuario mira si existe el usuario en la BD
	 * @param nombreUsuario
	 * @return TRUE si existe, FALSE si no existe
	 */
    public boolean existeUsuario(String nombreUsuario);
    
    
    /**
     * Dado un nombre de usuario lo convierte en administrador
     * @param s
     */
    public void hacerAdmin(String s);
    
    
    /**
     * Dados los datos de un evento, lo crea y lo almacena en la BD
     * @param description
     * @param eventDate
     * @param ca
     * @return El evento creado
     * @throws FechaPasada
     * @throws EventAlreadyExist
     */
    public Event createEvent(String description, Date eventDate, Categoria ca) throws FechaPasada, EventAlreadyExist;
    
    
    /**
     * Dado los datos de un nuevo usuario lo crea y lo almacena en la BD
     * @param nombre
     * @param apellido
     * @param fechaNacimiento
     * @param contraseña
     * @param dNI
     * @param nombreUsuario
     */
	public void ingresarUsuario(String nombre, String apellido, Date fechaNacimiento, String contrasena, String dNI,
			String nombreUsuario);
	
	
	/**
	 * Dado un usuario y una cantidad de dinero, le añade ese dinero al usario
	 * NOTA: Puede usarse para restar dinero si la cantidad de dinero es negativa
	 * @param u
	 * @param f
	 */
	public void addFounds(Usuario u, double f);
	
	
	/**
	 * Dado un numero de pregunta, obtiene sus pronosticos
	 * @param j
	 * @return
	 * @throws NumPreguntaNegativo 
	 */
	public Vector<domain.Pronostico> obtenerPronostico(int j) throws NumPreguntaNegativo;
	
	
	/**
	 * Dado un usuario obtiene el dinero que tiene en la cartera
	 * @param usu
	 * @return
	 */
	public Double getCartera(Usuario usu);
	
	
	/**
	 * Dado un string con la descripcion de un evento, lo busca y lo devuelve
	 * @param usu descripcion del evento
	 * @return
	 */
	public Event getEvent(String usu);
	
	
	/**
	 * Dado un string con la descripcion de una pregunta, la busca y la devuelve
	 * @param usu descripcion de la pregunta
	 * @return
	 */
	public Question getQuestion(String usu);
	
	
	/**
	 * Dado el numero de la pregunta, la busca y la devuelve
	 * @param nu numero de la pregunta
	 * @return
	 */
	public Question getQuestionByNumber(int nu);
	
	
	/**
	 * Dado el numero del pronostico, lo busca y lo devuelve
	 * @param pronosticoNumber numero del pronostico
	 * @return
	 */
	public Pronostico getPronosticoByNumber(int pronosticoNumber);
	
	
	/**
	 * Dado un pronostico, una cantidad y un usuario, crea la apuesta, la guarda y la asigna a usuario
	 * @param pronostico
	 * @param cantidad
	 * @param user
	 */
	public void anadirApuestaUsuario(Pronostico pronostico, double cantidad, Usuario user);
	
	
	/**
	 * Dado un usuario y un Mensaje, le añade el mensaje al usuario
	 * @param mensaje
	 * @param user
	 */
	public void anadirMensajeUsuario(Mensaje mensaje, Usuario user);
	
	
	/**
	 * Dado un pronostico, lo valida, da el dinero a las apuestas que han ganado 
	 * y borra la pregunta, los pronosticos y las apuestas asociadas a esa pregunta
	 * @param pronostico
	 */
	public void validarPronostico(Pronostico pronostico);

	
	/**
	 * Dada una apuesta la elimina
	 * @param ap
	 */
	public void eliminarApuesta(Apuestas ap);
	
	
	/**
	 * Dado un vector de apuestas, se las asigna al usuario
	 * @param aps
	 * @param usu
	 */
	public void actualizarApuestas(Vector<Apuestas> aps, Usuario usu);
	
	
	/**
	 * Dado un usuario y una string, le actualiza la contraeña a ese string
	 * @param u
	 * @param contraseña
	 */
	public void cambiarContrasena(Usuario u, String contrasena);
	
	
	/**
	 * Dada la pregunta la elimina
	 * @param q
	 * @return 1 si la ha eliminado, 0 si tenia apuestas y no se ha podido eliminar, -1 else
	 */
	public int eliminarPregunta(Question q);
	
	
	/**
	 * Devuelve un vector con las distintas categorias existentes 
	 * @return
	 */
	public Vector<Categoria> getCategories();
	
	
	/**
	 * Devuelve una lista con los distintos mensajes existentes 
	 * @return
	 */
	public List<Mensaje> getMensajes();
	
	
	/**
	 * Dado un pronostico lo elimina
	 * @param q
	 * @return 1 si lo ha eliminado, 0 si tenia apuestas y no se ha podido eliminar
	 */
	public int eliminarPronotico(Pronostico p);
	
	
	/**
	 * Dado un evento lo trata de eliminar
	 * @param p
	 * @return 1 si se ha eliminado, 0 si tenia apuestas asociadas
	 */
	public int eliminarEvento(Event ev);
	
	
	/**
	 * Dado un usuario y un string añade, crea el mensaje y lo almacena 
	 * @param user
	 * @param s
	 */
	public void addMensaje(Usuario user, String s);
	
	
	/**
	 * Dada una descripcion, obtiene la categoria cuya descripcion coincida con el string dado
	 * @param description
	 * @return
	 */
	public Categoria obtenerCategoriaPorDescripcion(String description);
	
	
	/**
	 * Devuelve las fechas en las que hay eventos de una categor�a en especial
	 * @param date con esta fecha le indicamos el mes en el que tiene que buscar eventos
	 * @param cat los eventos a buscar tienen que ser de esta categor�a
	 * @return las fechas en las que hay eventos de una categor�a en especial
	 * @throws NoExistCategory si la categor�a no existe o es null
	 */	
	public Vector<Date> getEventsCategoryMonth(Date date, Categoria cat)throws NoExistCategory;

}
	
	
