import java.util.*

fun main(){
    println("Hola mundo")

    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //  val > var
    // Duck Typing
    var ejemploVariable = " Adrian Eguez "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true


    // Clases Java
    val fechaNacimiento: Date = Date()


    //Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    //void -> Unit
    imprimirNombre("Santi");

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1,1)
    val sumaUno = Suma(null,1)
    val sumaUno = Suma(1,null)
}

fun imprimirNombre(nombre: String): Unit{
    println("Nombre : ${nombre}") // template strings
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (Defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable, puede tener elvalor de null en algun momento
): Double{
    // Int -> Int? (Nullable)

    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }

}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}
abstract class Numeros(
    // Ejemplo:
    // uno: Int, (Parametro (sin modificador de acceso))
    // private var uno: Int, //Propiedad Publica Clase numeros.uno
    // var uno: Int, //Propiedad de la clase (Por defecto es PUBLIC)
    //public var uno: Int,
    protected val numeroUno: Int, // Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, // Propiedad de la clase protected numeros.numeroDos


){
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos;
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Incializando")
    }
}

class Suma( //Contructor prmario suma
    unoParametro: Int, //Parametro
    dosParametro: Int, //Parametro
): Numeros(unoParametro, dosParametro){ //Extendiendo y mandando los parametros (super)
    init { //Bloque codigo constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( //Segundo constructor
        uno: Int?, //Parametros
        dos: Int //Parametros
    ):this(
        if(uno == null) 0 else uno,
        dos
    )
    constructor(
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos,
    )
}