package ec.edu.espol.binarysearchcomparison.modelo;

/**
 * 
 * @author Grupo#13
 */
public class BinarySearchAlgorithmRecursive implements SearchAlgorithm {

    @Override
    public int search(int[] array, int target) {
        return binarySearchRecursive(array, target, 0, array.length - 1);
    }

    /**
     * Método privado que realiza la búsqueda binaria de manera recursiva.
     * 
     * @param array El arreglo en el cual se realizará la búsqueda.
     * @param target El elemento que se desea buscar.
     * @param left El índice izquierdo del subarreglo actual.
     * @param right El índice derecho del subarreglo actual.
     * @return El índice del elemento si se encuentra, de lo contrario -1.
     */
    private int binarySearchRecursive(int[] array, int target, int left, int right) {
        // Verificar si los límites son válidos para continuar la búsqueda
        if (left <= right) {
            // Calcular el índice medio de la sección actual del array
            int mid = (right + left) / 2;

            // Comprobar si el elemento objetivo se encuentra en el índice medio
            if (array[mid] == target) {
                return mid; // Elemento encontrado, devolver el índice
            }

            // Si el objetivo es menor que el valor en el índice medio
            if (target < array[mid]) {
                // Buscar en la submatriz izquierda (elementos menores al medio)
                return binarySearchRecursive(array, target, left, mid - 1);
            } else {
                // Si el objetivo es mayor que el valor en el índice medio
                // Buscar en la submatriz derecha (elementos mayores al medio)
                return binarySearchRecursive(array, target, mid + 1, right);
            }
        }

        // Si no se encuentra el objetivo, devolver -1
        return -1;
    }

}
