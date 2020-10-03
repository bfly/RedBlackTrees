package redBlackTrees;

import java.util.List;
import java.util.Scanner;

import static redBlackTrees.RedBlackTree.Node;

public class RedBlackTreeMain {

    RedBlackTree<String> tree;

    public void go() {
        tree = new RedBlackTree<>();
        loadTree();
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("""

                1.- Add items
                2.- Delete items
                3.- Check items
                4.- Print tree
                5.- Delete tree
                9.- Exit
                """);

            String item;
            Node<String> node;

            int choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    item = scan.next();
                    while (!item.equals("\\")) {
                        node = new RedBlackTree.Node<>(item);
                        tree.insert(node);
                        item = scan.next();
                    }
                    tree.printTree();
                }
                case 2 -> {
                    item = scan.next();
                    while (!item.equals("\\")) {
                        node = new RedBlackTree.Node<>(item);
                        System.out.print("\nDeleting item " + item);
                        if ( tree.delete(node) ) {
                            System.out.print(": deleted!");
                        } else {
                            System.out.print(": does not exist!");
                        }
                        item = scan.next();
                    }
                    System.out.println();
                    tree.printTree();
                }
                case 3 -> {
                    item = scan.next();
                    while (!item.equals("\\")) {
                        node = new RedBlackTree.Node<>(item);
                        System.out.println((tree.findNode(node) != null) ? "found" : "not found");
                        item = scan.next();
                    }
                }
                case 4 -> tree.printTree();
                case 5 -> {
                    tree.deleteTree();
                    System.out.println("Tree deleted!");
                }
                case 9 -> System.exit(0);
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
    }

    private void loadTree() {
        List.of("this", "is", "a", "list", "of", "strings")
            .forEach(s -> tree.insert(new Node(s)));
    }

    public static void main( String[] args ) {
        new RedBlackTreeMain().go();
    }
}
