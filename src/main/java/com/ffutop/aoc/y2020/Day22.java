package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-22
 */
public class Day22 extends BasicDay {

    public static void main(String[] args) {
        Day22 day22 = new Day22();
        List<String> list = day22.readLists();
        list.add("");
        int half = list.size() / 2;
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        for (int i=1;i<half-1;i++) {
            player1.cards.add(Integer.parseInt(list.get(i)));
            player2.cards.add(Integer.parseInt(list.get(half+i)));
        }

        System.out.println(day22.solve1(player1, player2));

        player1 = new Player(1);
        player2 = new Player(2);
        for (int i=1;i<half-1;i++) {
            player1.cards.add(Integer.parseInt(list.get(i)));
            player2.cards.add(Integer.parseInt(list.get(half+i)));
        }
        Player winner = day22.combat(player1, player2);
        long result = 0L;
        Queue<Integer> cards = winner.cards;
        while (!cards.isEmpty()) {
            result += cards.size() * cards.poll();
        }
        System.out.println(result);
    }

    private long solve1(Player p1, Player p2) {
        while (!p1.cards.isEmpty() && !p2.cards.isEmpty()) {
            int d1 = p1.cards.poll();
            int d2 = p2.cards.poll();
            if (d1 > d2) {
                p1.cards.add(d1);
                p1.cards.add(d2);
            } else {
                p2.cards.add(d2);
                p2.cards.add(d1);
            }
        }

        long result = 0L;
        Queue<Integer> cards = p1.cards.isEmpty() ? p2.cards : p1.cards;
        while (!cards.isEmpty()) {
            result += cards.size() * cards.poll();
        }
        return result;
    }

    private Player combat(Player p1, Player p2) {
        System.out.println(1);
        Set<Snapshot> sameRound = new HashSet<>();
        while (!p1.cards.isEmpty() && !p2.cards.isEmpty()) {
            Snapshot snapshot = new Snapshot(p1, p2);
            if (sameRound.contains(snapshot)) {
                return p1;
            }
            sameRound.add(snapshot);
            int d1 = p1.cards.poll();
            int d2 = p2.cards.poll();
            if (p1.cards.size() >= d1 && p2.cards.size() >= d2) {
                Player winner = combat(new Player(p1, d1), new Player(p2, d2));
                if (winner.id == 1) {
                    p1.cards.addAll(Arrays.asList(d1, d2));
                } else {
                    p2.cards.addAll(Arrays.asList(d2, d1));
                }
            } else if (d1 > d2) {
                p1.cards.addAll(Arrays.asList(d1, d2));
            } else {
                p2.cards.addAll(Arrays.asList(d2, d1));
            }
        }
        return p1.cards.isEmpty() ? p2 : p1;
    }

    private static class Player {
        int id;
        Queue<Integer> cards;

        public Player(int id) {
            this.id = id;
            this.cards = new LinkedList<>();
        }

        public Player(Player player, int truncate) {
            this.id = player.id;
            LinkedList linkedList = new LinkedList(player.cards);
            while (linkedList.size() > truncate) {
                linkedList.removeLast();
            }
            this.cards = linkedList;
        }
    }

    private static class Snapshot {
        int c1, c2;
        List<Integer> list;

        public Snapshot(Player p1, Player p2) {
            this.c1 = p1.cards.size();
            this.c2 = p2.cards.size();
            list = new ArrayList<>();
            list.addAll(p1.cards);
            list.addAll(p2.cards);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Snapshot snapshot = (Snapshot) o;
            if (c1 != snapshot.c1 || c2 != snapshot.c2) {
                return false;
            }
            for (int i=0;i<list.size();i++) {
                if (list.get(i) != snapshot.list.get(i)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(c1, c2, list);
        }
    }
}
