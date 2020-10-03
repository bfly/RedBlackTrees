package redBlackTrees;

import edu.dcccd.Card;
import org.junit.jupiter.api.Test;

class RedBlackTreeTest {

    @Test
    void testRedBlackTree1() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        Integer[] ints = new Integer[] { 50, 30, 10, 70, 60 };
        for (Integer i : ints) tree.insert(new RedBlackTree.Node<>(i));
        tree.printTree();
        System.out.println();
        tree.levelOrder();
    }

    @Test
    void testRedBlackTree2() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        String[] strings = "this is a list of strings".split(" ");
        for (String s : strings) tree.insert(new RedBlackTree.Node<>(s));
        tree.printTree();
        System.out.println();
        tree.levelOrder();
    }

    @Test
    void testRedBlackTree3() {
        RedBlackTree<Card> tree = new RedBlackTree<>();
        for ( Card.Rank rank : Card.Rank.values()) {
            for ( Card.Suit suit : Card.Suit.values()) {
                Card card = new Card(rank, suit);
                tree.insert(new RedBlackTree.Node<>(card));
            }
        }
        tree.printTree();
        System.out.println();
        tree.levelOrder();
    }
}