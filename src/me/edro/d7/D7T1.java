package me.edro.d7;

import java.util.Scanner;
import java.util.Stack;

public class D7T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Stack<Integer> sizes = new Stack<>();
        int sum = 0;

        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equals("$")){
                if (parts[1].equals("cd")) {
                    if (parts[2].equals("..")){
                        Integer dirSize = sizes.pop();
                        if (dirSize < 100000){
                            sum += dirSize;
                        }
                        sizes.add(sizes.pop() + dirSize);
                    } else {
                        sizes.add(0);
                    }
                }
            } else if (!parts[0].equals("dir")) {
                int fileSize = Integer.parseInt(parts[0]);
                sizes.add(sizes.pop() + fileSize);
            }
        }

        // root
        while (sizes.size() > 1) {
            Integer dirSize = sizes.pop();
            if (dirSize < 100000) {
                sum += dirSize;
            }
            sizes.add(sizes.pop() + dirSize);
        }

        System.out.println(sum);
    }

    /*
    at most 100000

$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
     */
}
