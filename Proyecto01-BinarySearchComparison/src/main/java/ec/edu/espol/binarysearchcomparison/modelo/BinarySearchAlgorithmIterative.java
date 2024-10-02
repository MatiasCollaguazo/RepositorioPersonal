package ec.edu.espol.binarysearchcomparison.modelo;

/**
 * 
 * @author Group #13
 */
public class BinarySearchAlgorithmIterative implements SearchAlgorithm {
    /**
     * Realiza una búsqueda binaria iterativa en un array ordenado para encontrar el índice de un elemento dado.
     *
     * @param array  El array ordenado en el que se busca el elemento.
     * @param target El elemento que se desea buscar en el array.
     * @return El índice del elemento si se encuentra, o -1 si no se encuentra en el array.
     */
    @Override
    public int search(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            // Comprueba y retorna el índice del elemento si es igual al target
            if (array[mid] == target) {
                return mid;
            }

            // Si el target es menor al elemento, ignora la mitad derecha del array
            if (target < array[mid] ) {
                right = mid - 1;
            } 
            // Si el target es mayor al elemento, ignora la mitad izquierda del array
            else {
                left = mid + 1;
            }
        }

        // Si no lo encuentra retorna -1
        return -1;
    }

}
