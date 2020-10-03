package redBlackTrees;

import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T extends Comparable<T>> {

    private static final int RED = 0;
    private static final int BLACK = 1;
    private static final String[] colors = new String[] {"Red", "Black"};

    static class Node<T> {
        T key;
        int color = BLACK;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        Node(T key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "Node{" +
                "key=" + key +
                ", color=" + color +
                ", left=" + left +
                ", right=" + right +
                ", parent=" + parent +
                '}';
        }
    }

    private Node<T> root = null;

    void printTree() {
        printTree(root);
    }
    private void printTree(Node<T> node) {
        if (node == null) return;
        printTree(node.left);
        String color = (node.color==RED) ? "Red" : "Black";
        String parent = (node.parent == null) ? "null" : node.parent.key + "";
        System.out.printf("Color: %-6s Key: %-20s Parent: %s\n", color, node.key, parent);
        printTree(node.right);
    }
    public void levelOrder() { levelOrder(root); }

    public void levelOrder( Node<T> root) {
        if (root == null) return;
        Queue<Node<T>> q = new LinkedList<>();
        int level = 0;
        q.add(root);
        q.add(null);
        while (!q.isEmpty()) {
            Node<T> curr = q.poll();
            if (curr == null) {
                if (!q.isEmpty()) {
                    q.add(null);
                    System.out.println();
                    level++;
                }
            } else {
                String left = "Null";
                String right = "Null";
                String parent = "Null";
                if (curr.left != null) {
                    left = String.valueOf(curr.left.key);
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    right = String.valueOf(curr.right.key);
                    q.add(curr.right);
                }
                if (curr.parent != null) {
                    parent = String.valueOf(curr.parent.key);
                }

                System.out.println("Key: " + curr.key + ", Color: " + colors[curr.color] +
                    ", Parent: " + parent + ", Left: " + left + ", Right: " + right + ", Level: " + level);
//                System.out.println(curr + " Level: " + level);
            }
        }
    }

    Node<T> findNode(Node<T> findNode) {
        return findNode(findNode, root);
    }
    private Node<T> findNode(Node<T> findNode, Node<T> node) {
        if (node == null || findNode == null) {
            return null;
        }

        if (findNode.key.compareTo(node.key) < 0) {
            if (node.left != null) {
                return findNode(findNode, node.left);
            }
        } else if (findNode.key.compareTo(node.key) > 0) {
            if (node.right != null) {
                return findNode(findNode, node.right);
            }
        } else if (findNode.key == node.key) {
            return node;
        }
        return null;
    }

    void insert( Node<T> node ) {
        Node<T> temp = root;
        if (root == null) {
            root = node;
            node.color = BLACK;
            node.parent = null;
        } else {
            node.color = RED;
            while (temp != null) {
                if (node.key.compareTo(temp.key) < 0) {
                    if (temp.left == null) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.key.compareTo(temp.key) >= 0) {
                    if (temp.right == null) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    //Takes as argument the newly inserted node
    private void fixTree(Node<T> node) {
        if (node != null) {
            while (node.parent != null && node.parent.color == RED) {
                Node<T> uncle;
                if ( node.parent == node.parent.parent.left ) {
                    uncle = node.parent.parent.right;

                    if ( uncle != null && uncle.color == RED ) {
                        node.parent.color = BLACK;
                        uncle.color = BLACK;
                        node.parent.parent.color = RED;
                        node = node.parent.parent;
                        continue;
                    }
                    if ( node == node.parent.right ) {
                        //Double rotation needed
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    //if the "else if" code hasn't executed, this
                    //is a case where we only need a single rotation
                    rotateRight(node.parent.parent);
                } else {
                    uncle = node.parent.parent.left;
                    if ( uncle != null && uncle.color == RED ) {
                        node.parent.color = BLACK;
                        uncle.color = BLACK;
                        node.parent.parent.color = RED;
                        node = node.parent.parent;
                        continue;
                    }
                    if ( node == node.parent.left ) {
                        //Double rotation needed
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    //if the "else if" code hasn't executed, this
                    //is a case where we only need a single rotation
                    rotateLeft(node.parent.parent);
                }
            }
            root.color = BLACK;
        }
    }

    void rotateLeft(Node<T> node) {
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != null) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {        //Need to rotate root
            Node<T> right = root.right;
            root.right = right.left;
            if (right.left != null)
                right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = null;
            root = right;
        }
    }

    void rotateRight(Node<T> node) {
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != null) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else { //Need to rotate root
            Node<T> left = root.left;
            if (root.left != null) {
                root.left = root.left.right;
                if (root.right != null)
                    left.right.parent = root;
            }
            root.parent = left;
            left.right = root;
            left.parent = null;
            root = left;
        }
    }
    //Deletes whole tree
    void deleteTree(){
        root = null;
    }

    //Deletion Code .

    //This operation doesn't care about the new Node's connections
    //with previous node's left and right. The caller has to take care
    //of that.
    void transplant(Node<T> target, Node<T> with){
        if(target.parent == null){
            root = with;
        }else if(target == target.parent.left){
            target.parent.left = with;
        }else
            target.parent.right = with;
        with.parent = target.parent;
    }

    boolean delete(Node<T> z){
        if((z = findNode(z, root))==null)return false;
        Node<T> x;
        Node<T> y = z; // temporary reference y
        int y_original_color = y.color;

        if(z.left == null){
            x = z.right;
            transplant(z, z.right);
        }else if(z.right == null){
            x = z.left;
            transplant(z, z.left);
        }else{
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if(y_original_color==BLACK)
            deleteFixup(x);
        return true;
    }

    void deleteFixup(Node<T> x){
        while(x!=root && x.color == BLACK){
            if(x == x.parent.left){
                Node<T> w = x.parent.right;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if(w.right.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            }else{
                Node<T> w = x.parent.left;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if(w.left.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    Node<T> treeMinimum(Node<T> subTreeRoot){
        while(subTreeRoot.left != null){
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }

}
