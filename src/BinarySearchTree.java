import java.util.*;

// Clase BinarySearchTree

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

    // Método para contar las hojas del árbol binario de búsqueda
    public int contarHojas() {
        return contarHojas(root);
    }

    // Método recursivo privado para contar las hojas
    private int contarHojas(BinaryNode<AnyType> node) {
        // Si el nodo es null, no hay hojas en este subárbol
        if (node == null) {
            return 0;
        }
        // Si el nodo no tiene hijos, es una hoja
        if (node.left == null && node.right == null) {
            return 1;
        }
        // Suma las hojas de los subárboles izquierdo y derecho
        return contarHojas(node.left) + contarHojas(node.right);
    }

    // Método para contar el número de elementos menores estrictos que ELEMENTO
    public int menoresQue(AnyType ELEMENTO) {
        return menoresQue(ELEMENTO, root);
    }

    // Método recursivo privado para contar los elementos menores estrictos
    private int menoresQue(AnyType ELEMENTO, BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        int compareResult = myCompare(ELEMENTO, node.element);

        if (compareResult > 0) {
            // Si ELEMENTO es mayor que el valor del nodo, contamos este nodo y todos los de su subárbol izquierdo
            count = 1 + contarNodos(node.left) + menoresQue(ELEMENTO, node.right);
        } else {
            // Si ELEMENTO es menor o igual al valor del nodo, seguimos buscando en el subárbol izquierdo
            count = menoresQue(ELEMENTO, node.left);
        }

        return count;
    }

    // Método para contar todos los nodos en un subárbol
    private int contarNodos(BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }
        return 1 + contarNodos(node.left) + contarNodos(node.right);
    }

    // Método principal para probar la funcionalidad del árbol
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(6);
        bst.insert(2);
        bst.insert(8);
        bst.insert(1);
        bst.insert(4);
        bst.insert(3);

        System.out.println("Número de hojas: " + bst.contarHojas());
        bst.removeMin();
        System.out.println("Número de hojas después de eliminar el mínimo: " + bst.contarHojas());

        int ELEMENTO = 1;
        System.out.println("Número de elementos menores que " + ELEMENTO + ": " + bst.menoresQue(ELEMENTO));
    }
}
