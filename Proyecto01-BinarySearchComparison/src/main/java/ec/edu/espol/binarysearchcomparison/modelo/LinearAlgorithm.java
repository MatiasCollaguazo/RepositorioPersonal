package ec.edu.espol.binarysearchcomparison.modelo;

/**
 * 
 * @author Group #13
 */
public class LinearAlgorithm implements SearchAlgorithm {

    /**
     * Busca un elemento en un array utilizando una búsqueda lineal.
     *
     * @param array  El array en el que se busca el elemento.
     * @param target El elemento que se desea buscar en el array.
     * @return El índice del elemento si se encuentra, o -1 si no se encuentra.
     */
    @Override
    public int search(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i; // Retorna el indice del elemento encontrado
            }
        }

        // Si no lo encuentra retorna -1
        return -1;
    }
}
