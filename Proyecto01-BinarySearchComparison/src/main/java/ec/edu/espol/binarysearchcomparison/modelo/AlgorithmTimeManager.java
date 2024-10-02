package ec.edu.espol.binarysearchcomparison.modelo;

/**
 * Clase para gestionar el tiempo de ejecución de diferentes algoritmos de búsqueda.
 * 
 * @author Group #13
 */
public class AlgorithmTimeManager {

    /**
     * Método para medir el tiempo de ejecución de un algoritmo de búsqueda.
     * 
     * @param algorithm El algoritmo de búsqueda que se desea medir.
     * @param array El arreglo en el cual se realizará la búsqueda.
     * @param target El elemento que se desea buscar.
     * @return El tiempo de ejecución en nanosegundos.
     */
    public static long measureTime(SearchAlgorithm algorithm, int[] array, int target) {
        long startTime = System.nanoTime();
        algorithm.search(array, target);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * Método para convertir el tiempo de nanosegundos a microsegundos.
     * 
     * @param nanoseconds El tiempo en nanosegundos.
     * @return El tiempo en microsegundos.
     */
    public static double toMicroseconds(long nanoseconds) {
        return nanoseconds / 1000.0;
    }

    /**
     * Método para convertir el tiempo de nanosegundos a milisegundos.
     * 
     * @param nanoseconds El tiempo en nanosegundos.
     * @return El tiempo en milisegundos.
     */
    public static double toMilliseconds(long nanoseconds) {
        return nanoseconds / 1_000_000.0;
    }
}
