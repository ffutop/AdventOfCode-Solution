package com.ffutop.aoc.y2020;

import com.ffutop.aoc.y2020.util.Node;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-23
 */
public class Day23 extends BasicDay {

    private static final Integer CUPS = 1000000;

    public static void main(String[] args) {
        Day23 day23 = new Day23();
        day23.solve1("739862541", 100);
        day23.solve2("739862541", 10000000);
    }

    private void solve1(String circle, int round) {
        int len = circle.length();
        int[] nums = new int[len];
        int cursor = 0;
        for (char c : circle.toCharArray()) {
            nums[cursor++] = c-'0';
        }
        for (int r=0;r<round;r++) {
            cursor = 0;
            int[] next = new int[len];
            int current = nums[0];
            int[] values = new int[] {nums[1], nums[2], nums[3]};
            int max = 0, lower = 0;
            for (int i=4;i<len;i++) {
                if (nums[i] < current)  lower = Math.max(lower, nums[i]);
                max = Math.max(max, nums[i]);
            }
            if (lower == 0) {
                lower = max;
            }
            for (int i=4;i<len;i++) {
                next[cursor++] = nums[i];
                if (nums[i] == lower) {
                    for (int value : values) {
                        next[cursor++] = value;
                    }
                }
            }
            next[len-1] = current;
            nums = next;
        }
        StringBuilder builder = new StringBuilder();
        cursor = 0;
        for (;nums[cursor] != 1;cursor++);
        for (int i=cursor+1;i<len;i++)  builder.append(nums[i]);
        for (int i=0;i<cursor;i++)  builder.append(nums[i]);
        System.out.println(builder.toString());
    }

    private void solve2(String startPart, int rounds) {
        HashMap<Integer, Node> nodeMap = new HashMap<>();
        Node head = new Node(startPart.charAt(0)-'0', null, null);
        nodeMap.put(head.getValue(), head);
        Node prev = head;
        Node curr;
        for (int i=1;i<CUPS;i++) {
            if (i < startPart.length()) {
                curr = new Node(startPart.charAt(i) - '0', prev, null);
            } else {
                curr = new Node(i+1, prev, null);
            }
            nodeMap.put(curr.getValue(), curr);
            prev.setNext(curr);
            prev = curr;
        }

        head.setPrev(prev);
        prev.setNext(head);

        curr = head;
        for (int round=0;round<rounds;round++) {
            Node partHead = curr.getNext();
            Node partTail = partHead.getNext().getNext();
            curr.setNext(partTail.getNext());
            partTail.setPrev(curr);
            List<Integer> escapeValues = Arrays.asList(partHead.getValue(), partHead.getNext().getValue(), partTail.getValue());
            int lowerValue = curr.getValue();
            do {
                lowerValue = lowerValue-1;
                if (lowerValue <= 0)    lowerValue = CUPS;
            } while (escapeValues.contains(lowerValue));

            Node lower = nodeMap.get(lowerValue);
            Node nextLower = lower.getNext();
            lower.setNext(partHead);
            partHead.setPrev(lower);
            partTail.setNext(nextLower);
            nextLower.setPrev(partTail);

            curr = curr.getNext();
        }

        Node cup1 = nodeMap.get(1);
        System.out.println(1L * cup1.getNext().getValue() * cup1.getNext().getNext().getValue());
    }
}
