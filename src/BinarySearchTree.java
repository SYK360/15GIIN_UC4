import java.util.*;

//Clase BinarySearchTree
//
//
//******************METODOS PUBLICOS*********************
//void insert( x )       --> Inserta x
//void remove( x )       --> Elimina x
//void removeMin( )      --> Elimina el objeto con menor valor (clave)
//Comparable find( x )   --> Devuelve el objeto del arbol igual a x
//Comparable findMin( )  --> devuelve el objeto menor
//Comparable findMax( )  --> devuelve el objeto mayor
//boolean isEmpty( )     --> devuelve TRUE sii el arbol es vacio
//void makeEmpty( )      --> elimina todos los nodos del arbol

/**
 * Implementa Arbol Binario de BÃēsqueda
 * Como comparador por default es compareTo
 * Pero podemos pasarle un Comparator al momento
 * de crear el arbol
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    private BinaryNode<AnyType> root;
    private Comparator<? super AnyType> cmp;	// para poder tener
    // otra forma de comparar claves
    // que no sea la dada por el
    // metodo compareTo de AnyType
    // que hereda de la interfaz Comparable
    BinarySearchTree( )
    { this( null ); }// llamar al siguiente constructor
    // (el siguiente) con comparador null

    BinarySearchTree( Comparator<? super AnyType> c )
    // en este constructor podemos pasar un Comparador (Comparator)
    { root = null; cmp = c; }

    static class BinaryNode<AnyType>
    {
        private AnyType             element;
        private BinaryNode<AnyType> left;
        private BinaryNode<AnyType> right;

        public BinaryNode( )
        {
            this( null, null, null );
        }

        public BinaryNode(AnyType x )
        {
            this( x, null, null );
        }

        public BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element = theElement;
            left    = lt;
            right   = rt;
        }

        public AnyType getElement( )
        {
            return element;
        }

        public BinaryNode<AnyType> getLeft( )
        {
            return left;
        }

        public BinaryNode<AnyType> getRight( )
        {
            return right;
        }

        public void setElement( AnyType x )
        {
            element = x;
        }

        public void setLeft( BinaryNode<AnyType> t )
        {
            left = t;
        }

        public void setRight( BinaryNode<AnyType> t )
        {
            right = t;
        }

    }
    private int myCompare( AnyType lhs, AnyType rhs )
    // Siempre que comparemos elementos utilizamos este
    // metodo de comparacion de elementos. Si existe
    // un Comparator se usa este para comparar, si no
    // se usa compareTo de la interfaz Comparable
    // de esta forma podemos comparar por nombre, DNI, etc
    // el default es DNI que es por donde se compara
    // en el metodo compareTo de la interfaz Comparable
    {
        if( cmp != null )
            return cmp.compare( lhs, rhs );
        else
            return lhs.compareTo( rhs );
    }

    /**
     * Insertar (o agregar) el objeto x al arbol
     * @param x el elemento a insertar.
     */
    public void insert( AnyType x )
    {  // metodo DRIVER que llama a uno recursivo privado
        root = insert( x, root );
    }

    /**
     * Eliminar un objeto del arbol..
     * @param x el objeto a eliminar
     */
    public void remove( AnyType x )
    {   // metodo DRIVER que llama al recursivo privado
        root = remove( x, root );
    }


    /**
     * convertir el arbol en vacio
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Preguntar si el arbol es vacio
     * @return devuelve TRUE sii es vacio.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws DuplicateItemException if x is already present.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        int valor;
        if( t == null )
            t = new BinaryNode<AnyType>( x );
        else if ( (valor = myCompare(x, t.element)) < 0 )
            t.left = insert( x, t.left );
        else if ( valor > 0 )
            t.right = insert( x, t.right );
        else; // no inserta nada porque el elemento ya existe;
        // se ha podido quetar este else pero lo dejo para indicar que en ese punto
        // consigue que el elemento ya esta en el arbol
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws ItemNotFoundException if x is not found.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null );
        // no haga nada porque el elemento no existe;
        if( myCompare(x, t.element ) < 0 )
            t.left = remove( x, t.left );
        else if( myCompare(x, t.element ) > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = removeMin( t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    // La clase BinaryNode para crear nodos
    // Note que por no ser ni publica ni privada
    // solo es accesible en el paquete

    private BinaryNode<AnyType> getRoot(){
        // obtener la referencia al nodo raÃ­z
        return root;
    }
    /**
     * Eliminar el menor elemento
     */
    public void removeMin( )
    {
        root = removeMin( root );
    }

    /**
     * Metodo interno para obtener el elemento en un nodo.
     * @param t es el ndo.
     * @return devuelve el elemento o null si el nodo es null.
     */
    private AnyType elementAt( BinaryNode<AnyType> t )
    {
        return t == null ? null : t.element;
    }

    /**
     * Encontrar el menor elemento
     * @return devuelve el menor elemento o null si esta vacio.
     */
    public AnyType findMin( )
    {
        return elementAt( findMin( root ) );
    }
    /**
     * Internal method to remove minimum item from a subtree.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws ItemNotFoundException if t is empty.
     */
    private BinaryNode<AnyType> removeMin( BinaryNode<AnyType> t )
    {
        if( t == null ) return t;
            // no haga nada porque el elemento no existe;
        else if( t.left != null )
        {
            t.left = removeMin( t.left );
            return t;
        }
        else
            return t.right;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item/usr/include/node/common.gypi
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.left != null )
                t = t.left;

        return t;
    }

    // Estos mÃŠtodos deben estar en la clase BinaryTree
    // si la Clase BinaryNode es interna a BinaryTree y privada
    // porque hacen uso de metodos y campos de la clase BinaryNode

}