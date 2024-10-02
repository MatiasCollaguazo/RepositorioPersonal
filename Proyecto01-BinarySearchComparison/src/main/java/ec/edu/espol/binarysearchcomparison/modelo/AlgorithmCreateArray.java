package ec.edu.espol.binarysearchcomparison.modelo;

/**
 * 
 * @author Grupo #13
 */
public class AlgorithmCreateArray {

    /**
     * Genera un arreglo de enteros secuenciales de tamaño dado, comenzando en 1.
     * 
     * @param arrSize El tamaño del arreglo.
     * @return Un arreglo de enteros secuenciales comenzando en 1.
     */
    public int[] generateSequentialArray(int arrSize) {
        int[] array = new int[arrSize];
        for (int i = 0; i < arrSize; i++) {
            array[i] = i + 1;
        }
        return array;
    }
}
