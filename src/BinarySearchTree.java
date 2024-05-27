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
 * Implementa Arbol Binario de Búsqueda
 * Como comparador por default es compareTo
 * Pero podemos pasarle un Comparator al momento
 * de crear el arbol
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    private BinaryNode<AnyType> root;
    private Comparator<? super AnyType> cmp;

    // Constructor por defecto
    BinarySearchTree() {
        this(null);
    }

    // Constructor con un Comparator
    BinarySearchTree(Comparator<? super AnyType> c) {
        root = null;
        cmp = c;
    }

    static class BinaryNode<AnyType> {
        private AnyType element;
        private BinaryNode<AnyType> left;
        private BinaryNode<AnyType> right;

        public BinaryNode() {
            this(null, null, null);
        }

        public BinaryNode(AnyType x) {
            this(x, null, null);
        }

        public BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        public AnyType getElement() {
            return element;
        }

        public BinaryNode<AnyType> getLeft() {
            return left;
        }

        public BinaryNode<AnyType> getRight() {
            return right;
        }

        public void setElement(AnyType x) {
            element = x;
        }

        public void setLeft(BinaryNode<AnyType> t) {
            left = t;
        }

        public void setRight(BinaryNode<AnyType> t) {
            right = t;
        }
    }

    private int myCompare(AnyType lhs, AnyType rhs) {
        if (cmp != null)
            return cmp.compare(lhs, rhs);
        else
            return lhs.compareTo(rhs);
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            t = new BinaryNode<>(x);
        else {
            int valor;
            if ((valor = myCompare(x, t.element)) < 0)
                t.left = insert(x, t.left);
            else if (valor > 0)
                t.right = insert(x, t.right);
            // else; // No inserta nada porque el elemento ya existe
        }
        return t;
    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return t; // No hace nada porque el elemento no existe

        if (myCompare(x, t.element) < 0)
            t.left = remove(x, t.left);
        else if (myCompare(x, t.element) > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) { // Dos hijos
            t.element = findMin(t.right).element;
            t.right = removeMin(t.right);
        } else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }

    private BinaryNode<AnyType> getRoot() {
        return root;
    }

    public void removeMin() {
        root = removeMin(root);
    }

    private AnyType elementAt(BinaryNode<AnyType> t) {
        return t == null ? null : t.element;
    }

    public AnyType findMin() {
        return elementAt(findMin(root));
    }

    private BinaryNode<AnyType> removeMin(BinaryNode<AnyType> t) {
        if (t == null)
            return t;
        else if (t.left != null) {
            t.left = removeMin(t.left);
            return t;
        } else
            return t.right;
    }

    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t != null)
            while (t.left != null)
                t = t.left;
        return t;
    }

    // Método principal para probar la funcionalidad del árbol
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        System.out.println("Min: " + bst.findMin());
        bst.removeMin();
        System.out.println("Min after removing min: " + bst.findMin());
        bst.remove(7);
        System.out.println("Is 7 present? " + (bst.findMin() == 7 ? "Yes" : "No"));
        System.out.println("Is the tree empty? " + bst.isEmpty());
    }
}
