package AVL_Tree;

import java.util.*;

class AVLTree {

    private Node root;

    private class Node {

        private int element;
        private int height;

        private Node left;
        private Node right;

        private Node(int element) {
            this.element = element;
            this.height = 1;
        }
    }

    /* Insert a Node into the tree */
    public void insert(int element) {

        root = insert(root, element);
    }

    private Node insert(Node node, int element) {

        if (node == null) {
            return (new Node(element));
        }

        if (element < node.element) {
            node.left = insert(node.left, element);
        } else if (element > node.element) {
            node.right = insert(node.right, element);
        } else {
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (element < node.left.element) {
                return rightRotate(node);
            } else if (element > node.left.element) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        if (balanceFactor < -1) {
            if (element > node.right.element) {
                return leftRotate(node);
            } else if (element < node.right.element) {
                node.left = rightRotate(node.left);
                return leftRotate(node);
            }
        }

        return node;
    }

    /* Delete an element from the tree */
    public void delete(int element) {
        delete(root, element);
    }

    private Node delete(Node root, int element) {

        if (root == null) {
            return root;
        }

        if (element < root.element) {
            root.left = delete(root.left, element);
        } else if (element > root.element) {
            root.right = delete(root.right, element);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = nodeWithMinValue(root.right);

                root.element = temp.element;

                root.right = delete(root.right, temp.element);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        int balanceFactor = getBalanceFactor(root);

        if (balanceFactor > 1) {
            if (getBalanceFactor(root.left) >= 0) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(root.right) <= 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }
        return root;
    }

    /* pretty print the tree */
    public void print() {
        System.out.println("\nPre-Order Traversal:");
        print(root, "", false);
    }

    private void print(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            System.out.println(node.element);

            print(node.left, indent, false);
            print(node.right, indent, true);
        }
    }
    private Node rightRotate(Node q) {
        Node p = q.left;
        Node aux = p.right;

        p.right = q;
        q.left = aux;

        q.height = max(height(q.left), height(q.right)) + 1;
        p.height = max(height(p.left), height(p.right)) + 1;

        return p;
    }

    private Node leftRotate(Node p) {
        Node q = p.right;
        Node aux = q.left;

        q.left = p;
        p.right = aux;

        p.height = max(height(p.left), height(p.right)) + 1;
        q.height = max(height(q.left), height(q.right)) + 1;

        return q;
    }

    private int getBalanceFactor(Node node) {
        
        if (node == null) {
            return 0;
        }
        
        return height(node.left) - height(node.right);
    }
    
    private int height(Node node) {
        
        if (node == null) {
            return 0;
        }
        
        return node.height;
    }

    private Node nodeWithMinValue(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }
    
    private int max(int ele1, int ele2) {
        return (ele1 > ele2) ? ele1 : ele2;
    }      


    public class Main {

        public static void main(String[] args) {

            AVLTree bst = new AVLTree();
            
            bst.insert(11);
            bst.insert(21);
            bst.insert(3);
            bst.insert(34);
            bst.insert(5);
            bst.insert(64);
            bst.insert(7);
            bst.insert(58);
        
            bst.print();
            
            System.out.println("\nDelete 5 and 11: \n");
            bst.delete(5);
            bst.delete(11);
            
            bst.print();
        }
    }
}
