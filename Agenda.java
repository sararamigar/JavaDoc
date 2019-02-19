package arraydinamico;

import java.util.Scanner;

/**
 * Codigo fuente para manipular una agenda de contactos
 * utilizando un array dinamico.
 * <a href="https://github.com/sararamigar/JavaDoc.git">Repositorio en GitHub</a>
 * @version
 */
public class Agenda 
{
	public int numContactos;
	public arrayContacto array[];
	
	/////////////////////////////////////
	/**
	 * <h2>Constructor</h2>
	 * @author sarar
	 */
	public Agenda() 
	{
		numContactos=0;
		array= new arrayContacto[numContactos];
	}
	
	/////////////////////////////////////////
	public void main (String[] args)
	{
		arrancarAplicacion();
	}
	
	//////////////////////////////////////
	public void arrancarAplicacion()
	{
		/**
		 * <p>Este método arranca la aplicación. De él derivan todos los métodos de la propia clase
		 * como de las clases derivadas.</p>
		 */
		int opcion;
		do {
			opcion=verMenu();
			if(opcion==6)
				System.out.println("Saliendo...");
			else
			{
				switch(opcion)
				{
					case 1: 
						nuevoContacto();
						break;
					case 2:
						consultar();
						break;
					case 3:
						eliminar();
						break;
					case 4:
						modificar();
						break;
					case 5:
						mostrarContactos();
						break;
				}
			}
		}while(opcion !=6);

	}
	
	///////////////////////////////////////
	public int verMenu()
	{
		/**
		 * Este método le <em>muestra las opciones</em> que el usuario puede ejecutar con el 
		 * programa
		 */
		Scanner entrada= new Scanner(System.in);
		int opcion=0;
		System.out.println("Elige una opción del menú:");
		System.out.println("1- Nuevo Contacto");
		System.out.println("2- Consultar ");
		System.out.println("3- Eliminar"); 
		System.out.println("4- Modificar");
		System.out.println("5- Mostrar todos los contactos");
		System.out.println("6- Salir");
		boolean opcionValida= (opcion>=1 || opcion<=6);
		do {
			opcion=entrada.nextInt();
			if(!opcionValida)
				System.out.println("La opción no es válida. Introduzca otra:");
		}while(!opcionValida);
		return opcion;
	}
	
	///////////////////////////////////////
	/**
	 * Este método añade un nuevo contacto a la agenda
	 */
	private void nuevoContacto() 
	{
		arrayContacto contacto= introDatos();
		//Comprobamos si existe
		int posicion= busqueda(contacto);
		boolean existe= (posicion !=-1);
		if (existe)
			System.out.println("Este contacto ya existe");
		else
			anadir(contacto);
	}
	
	////////////////////////////////
	private arrayContacto introDatos()
	{
		/**
		 * Este método lee los datos que se introducen por teclado
		 */
		//Introducimos los datos
		Scanner entrada=new Scanner(System.in);
		System.out.println("Introduce el nombre del contacto:");
		String nombre=entrada.nextLine();
		System.out.println("Introduce el teléfono:");
		String telf=entrada.nextLine();
		
		//Intanciamos un nuevo contacto
		return new arrayContacto(nombre, telf);
	}
	
	//////////////////////////////////////
	public void consultar()
	{
		/**
		 * Este método sirve para comprobar si los datos metidos por teclado se encuentran dentro
		 * del array.
		 */
		//Comprobamos si está vacía
		if(agendaVacia())
			System.out.println("La agenda está vacía");
		else
		{
			arrayContacto contacto=introDatos();
			int posicion=busqueda(contacto);
			boolean existe= (posicion !=-1);
			//Enviar a pantalla la información de ese contacto
			if(existe)
			{
				contacto.toString();
				contacto.mostrarDatos();
			}
			else
				System.out.println("Este contacto no existe");
		}
	}
	
	/////////////////////////////////
	public boolean agendaVacia()
	{
		/**
		 * Nos dice si el array está vacío
		 */
		return (numContactos==0);
	}
	
	/////////////////////////////////
	private void eliminar()
	{
		/**
		 * Elimina un elemento del array
		 */
		if(agendaVacia())
			System.out.println("La agenda está vacía");
		else
		{
			arrayContacto contacto=introDatos();
			int posicion=busqueda(contacto);
			boolean existe= (posicion !=-1);
			//Enviar a pantalla la información de ese contacto
			if(existe)
			{
				//Sí existe, lo marcamos para eliminarlo
				marcar(posicion);
				System.out.println("El contacto ha sido eliminado");
			}
			else
				System.out.println("Este contacto no existe");
		}
	}
	
	////////////////////////////////
	private void modificar()
	{
		/**
		 * Edita los datos guardados en x posición.
		 */
		Scanner entrada=new Scanner(System.in);
		if(agendaVacia())
			System.out.println("La agenda está vacía");
		else
		{
			arrayContacto contacto=introDatos();
			int posicion=busqueda(contacto);
			boolean existe= (posicion !=-1);
			if(existe)
			{
				System.out.println("Introduce el nuevo teléfono:");
				String nuevoTelf= entrada.nextLine();
				array[posicion].setTelefono(nuevoTelf);
				System.out.println("El contacto ha sido modificado");
			}
			else
				System.out.println("Este contacto no existe");
		}
	}
	
	////////////////////////////////
	private void mostrarContactos()
	{
		/**
		 * Nos muestran los contactos almacenados en el array
		 */
		if(agendaVacia())
			System.out.println("La agenda está vacía");
		else {
			for(int i=0; i<numContactos; i++)
			{
				System.out.println(array[i]);
			}
		}
	}
	
	/////////////////////////////////////
	/**
	 * unElementoMas es un método que pide memoria para un elemento 
	 * más y copio en ese nuevo array y copio en ese nuevo array 
	 * los elementos que ya estaban en el original. Para hacer esto 
	 * no tenemos más remedio que utilizar un array auxiliar que 
	 * apunte a la misma dirección donde apunta array.
	 */
	private void unElementoMas(arrayContacto aux[])
	{
		//Pedir memoria para un elemento más
		array=new arrayContacto[numContactos+1];
		
		//Copiamos los contactos anteriores en array
		for(int i=0; i<numContactos; i++) 
		{
			array[i]= aux[i];
		}
		//Actualizamos el número de contactos
		numContactos++;
	}
	
	////////////////////////////////////
	/**
	 * Añade un elemento a la agenda
	 * @param contacto
	 */
	public void anadir(arrayContacto contacto)
	{
		unElementoMas(array);
		insertar(contacto, numContactos-1);
	}
	
	///////////////////////////////////
	/**
	 * Inserta un contacto en la posición válida indicada
	 */
	public void insertar(arrayContacto contacto, int posicion)
	{
		//Validamos la posición
		if (posicion >=0 && posicion<numContactos)
		{
			array[posicion]= contacto;
		}
		else
			System.out.println("La posición dada no existe");
	}
	
	//////////////////////////////
	/**
	 * Este método pide memoria para un elemento menos y copia 
	 * en el array los elementos que no estén marcados
	 */
	private void unElementoMenos(arrayContacto aux[])
	{
		//Pedir memoria para un elemento menos
		array=new arrayContacto[numContactos-1];
		/**
		 * Indice para posicionarme en el array destino
		 */
		int j=0;		
		//Copiamos los contactos anteriores en array
		for(int i=0; i<numContactos; i++) 
		{
			if(aux[i] != null)
			{
				array[j]=aux[i];
				j++;
			}
		}
		//Actualizamos el número de contactos
		numContactos--;
	}
	
	/////////////////////////////////////////
	/**
	 * Realiza una busqueda secuencial del contacto que le hemos pasado. 
	 * Devuelve la posición en caso de que la encuentre y -1 en caso contrario
	 * @param contacto
	 * @return
	 */
	private int busqueda(arrayContacto contacto)
	{
		for(int i=0; i<numContactos; i++)
		{
			if(contacto.equals(array[i]))
				return i;
		}
		return -1;
	}
	
	///////////////////////////////////////
	/**
	 * Recibe la posición donde está el elemento que quiero eliminar y lo marca asignandole un null.
	 * @return
	 */
	private boolean marcar(int posicion) 
	{
		array[posicion]=null;
		unElementoMenos(array);
		return true;
	}
	
}
	

