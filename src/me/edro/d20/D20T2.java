
package me.edro.d20;

import java.util.ArrayList;
import java.util.Scanner;

public class D20T2 {

    private static class Node {
        Long item;
        Node next;
        Node prev;

        Node(Node prev, Long element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Node> ring = new ArrayList<>();

        Node lastNode = null;
        int zeroPos = 0;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            long el = Long.parseLong(line);
            if (el == 0) {
                zeroPos = ring.size();
            }

            Node newNode = new Node(lastNode, el * 811589153, null);
            if (lastNode != null) {
                lastNode.next = newNode;
            }

            ring.add(newNode);

            lastNode = newNode;
        }

        // link
        if (lastNode != null) {
            lastNode.next = ring.get(0);
            ring.get(0).prev = lastNode;
        }

        // move
        int rl = ring.size() - 1;
        for (int a = 0; a < 10; a++) {
            for (Node n : ring) {
                long i = n.item % rl;
                if (i < 0) i += rl;

                if (i != 0) {
                    // extract
                    n.prev.next = n.next;
                    n.next.prev = n.prev;

                    Node target = n;
                    while (i > 0) {
                        target = target.next;
                        i--;
                    }

                    // push
                    target.next.prev = n;
                    n.next = target.next;
                    target.next = n;
                    n.prev = target;
                }
            }
        }

        System.out.println(get(ring, zeroPos, 1000) + get(ring, zeroPos, 2000) + get(ring, zeroPos, 3000));
    }

    private static long get(ArrayList<Node> ring, int zeroPos, int i) {
        Node t = ring.get(zeroPos);
        i = i % ring.size();
        while (i > 0) {
            t = t.next;
            i--;
        }
        return t.item;
    }


}
