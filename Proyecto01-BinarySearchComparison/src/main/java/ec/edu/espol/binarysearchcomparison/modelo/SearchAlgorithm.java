package ec.edu.espol.binarysearchcomparison.modelo;

/**
 * 
 * @author Grupo #13
 */
public interface SearchAlgorithm {
    
    /**
     * Método para buscar un elemento en un arreglo.
     * 
     * @param array El arreglo en el cual se realizará la búsqueda.
     * @param target El elemento que se desea buscar.
     * @return El índice del elemento si se encuentra, de lo contrario -1.
     */
    public int search(int[] array, int target);
}
