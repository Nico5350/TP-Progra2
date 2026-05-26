# TP-Progra2
Trabajo practico programacion 2
Programación II - Trabajo Práctico Integrador
1er. Cuatrimestre 2026
SEGUNDA PARTE
Fecha de presentación: 14/05/2026(subido al Campus)
Fecha límite de entrega: 29/05/2026 (por el Campus)
En esta segunda parte deben entregar la implementación, el diagrama de clases actualizado, el análisis de
complejidad en donde se pida, y el IREP para cada tipo de datos modelado en su solución. Para poder
empezar con la segunda parte deben tener aprobado el diseño presentado en la primera parte.
Requerimientos técnicos:
i- Grupos: El mismo grupo de la primera parte del TP. Si hay alguna modificación debe ser aprobada por
sus docentes de la comisión.
ii- Se deben utilizar donde sea conveniente las herramientas de Tecnologías Java que se vieron en la
materia. Al menos una vez deben usarse:
● Stringbuilder, cuyo uso debe basarse en la necesidad de modificar el string.
● Iteradores y Foreach para recorrer las colecciones de Java
iii- Se deberá utilizar en el desarrollo del trabajo herencia y polimorfismo, y al menos 2 de estos
conceptos: sobreescritura, sobrecarga e interfaces. Como también, en los casos que corresponda, se
deberá implementar clases/métodos abstractos.
iv- En el informe (documento) se debe explicar donde utilizaron estos conceptos.
v- Escribir el IREP de la representación elegida para la implementación de cada TAD. Debe ser parte
de la documentación.
Por otro lado, desde la materia se proveerá
a) Una Interfaz para que se utilice como base para la implementación de la clase principal Billetera, con la
explicación de cada método. NO SE DEBE MODIFICAR.
b) Un código cliente con datos para crear los objetos, NO SE DEBE MODIFICAR.
c) Una clase de testeo (junit). Será condición necesaria para aprobar esta parte del trabajo que tanto el
código cliente como el test se ejecuten sin errores.
d) La clase Utilitarios que debe usar para consultar la fecha actual y la cotización actual de los activos.
- Además de pasar el test de junit suministrado junto con el TP, en la corrección se testean los ejercicios con
otro junit adicional, por lo que se recomienda que el grupo arme un conjunto propio de testeo acorde a su
implementación, antes de entregar el TP. Puede entregarlo también si lo desea.
La entrega se realiza subiendo al Campus el proyecto de Java con su implementación
(Seleccionar solamente los archivos .java) Se debe subir la documentación con los puntos
pedidos previamente por escrito en un archivo Word en lo posible o PDF, junto con el proyecto.
Consideraciones importantes para la implementación y la documentación del trabajo:
La implementación de los TADs debe responder a su diseño presentado en la Primera Parte teniendo en
cuenta las correcciones que se indicaron/indicarán a cada grupo. Además, será condición necesaria
para aprobar que se cumpla con:
● Deberá correr satisfactoriamente con el código cliente entregado
● Deberá pasar satisfactoriamente el test junit proporcionado.
● Deberá aprovechar correctamente las estructuras de datos elegidas.
● El código deberá tener implementado el método toString del TAD principal, lo que implica que se
deban implementar los toString de los TADs relacionados.
● Se deberá usar herencia, polimorfismo y abstracción.
Algunas definiciones, modificaciones y aclaraciones al enunciado de la primera parte.
Definiciones:
● Los usuarios se registran con su DNI. nombre, teléfono y email.
● Las inversiones en Fondo de liquidez empresarial es una inversión sujeta a un activo particular
llamado ‘FLE’ con una tasa del 8%.
● Al crear una nueva Inversión, se devuelve el id único de dicha inversión. Este Id se usa para poder
precancelar la inversión siempre que sea posible.
● Para el cálculo de los intereses de las inversiones, se debe la cotización actual del activo que se
consulta a la clase Utilitarios.
Modificaciones y aclaraciones al enunciado de la primera parte:
● Punto 3 y 10. Al consultar la lista de cuentas de un usuario, estas se deben devolver como lista de
Strings con el siguiente formato:
○ [Tipo]: [Alias] ([CVU]).
○ Ej1: Premium: carlos.premium (0000003100000000000002)
○ Ej2: Regular: ana.ruda.mantra (0000003100000000000005)
● Punto 8. Al consultar las actividades globales, del usuario o de una cuenta, se deben respetar los
siguientes formatos:
Las actividades se deben mostrar con el siguiente formato:
○ Ransferencia:
■ fecha: [fecha]
origen: [dni] ([cvu])
destino: [dni] ([cvu])
monto: [monto]
[Aprobado/Rechazado]
○ Inversion:
■ fecha: [fecha]
origen: [dni] ([cvu])
desc: [tipo inversion]
monto: [monto]
plazo: [plazo]
[Aprobado/Rechazado]
Se agregan las siguientes funcionalidades:
● 11. Registrar una empresa. Para que los usuarios puedan abrir cuentas corporativas asociadas a dicha
empresa.
● 12. Agregar persona autorizada a usar el CUIT de una empresa previamente registrada. Las personas
autorizadas pueden no estar registradas en el sistema por eso, solo se registra el DNI de las personas
autorizadas por la empresa.
● 13. Precancelar una inversión.
● 14. Consultar el CVU asociado a un alias.
● 15. [Bonus Track] Procesar Inversiones que finalizan hoy. Es una funcionalidad opcional Si terminaron
los demás requerimientos y quieren intentarlo, pueden descomentar el método en la interfaz e
intentar implementarlo.
Con las observaciones anteriores, se deberá implementar el TP, teniendo en cuenta las siguientes
condiciones:
- Se debe poder imprimir “Billetera” mostrando sus datos en formato adecuado para poder comprenderlo.
Se espera visualizar el estado interno del sistema de una forma legible.
También se deberá entregar en el documento el siguiente análisis de la complejidad:
(Pegar el código de las funciones intervinientes en el proceso de transferencia y analizar sobre eso en el
mismo informe. Esta es la única porción de código que debe haber en el informe)
Explicar la complejidad lograda y justificar por medio de Álgebra de Órdenes para el punto:
- 5. Realizar transferencia entre cuentas.
Test (JUnit):
Se habilitará el archivo de test en el Moodle, junto a este enunciado, de donde deberán descargarlo.
Se avisará en cuanto esté disponible.
Código Cliente:
Ya está habilitado junto con este enunciado, en el espacio del Moodle para el TP, de donde deberán
descargarlo.
Interfaz:
Ya está habilitado junto con este enunciado. Se utilizará como Interfaz para crear la clase Billetera
