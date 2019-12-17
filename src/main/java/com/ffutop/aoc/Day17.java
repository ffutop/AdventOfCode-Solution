package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day17 {

    private static int[][] direction = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}};
    private static char[] facing = new char[] {'^', '>', 'v', '<'};

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part1. sum of the alignment parameters: %s", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part2. embedded eight-digit message: %s", val2));
    }

    private static class Part1 {
        int[][] board = new int[60][60];
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day17.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            intCode.solve(input, output);

            int row = 0, col = 0;
            int maxRow = 0, maxCol = 0;
            while (output.size() != 0) {
                int val = Math.toIntExact(output.poll());
                if (val == 10) {
                    row ++;
                    col = 0;
                } else {
                    board[row][col++] = val;
                }
                maxRow = Math.max(maxRow, row);
                maxCol = Math.max(maxCol, col);
            }
            row = maxRow;
            col = maxCol;
            int sum = 0;
            for (int i=0;i<row;i++) {
                for (int j=0;j<col;j++) {
                    System.out.print(board[i][j] == 35 ? '#' : '.');
                }
                System.out.println();
            }
            for (int i=1;i<row-1;i++) {
                for (int j=1;j<col-1;j++) {
                    if (board[i][j] == 35 && isIntersection(i, j)) {
                        sum += i * j;
                    }
                }
            }
            return sum;
        }

        private boolean isIntersection(int x, int y) {
            for (int i=0;i<4;i++) {
                int nx = x + direction[i][0];
                int ny = y + direction[i][1];
                if (board[nx][ny] != 35) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class Part2 {
        int[][] board = new int[60][60];
        int row = 57, col = 58;

        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day17.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            intCode.getMemorys().put(0, 2L);
            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();

            while (intCode.solve(input, output) == false) {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        int code = Math.toIntExact(output.poll());
                        board[i][j] = code;
                        System.out.print((char) code);
                    }
                }
                String routine = generateRoutine();
                System.out.println(routine);

                String main = "A,B,A,B,C,C,B,A,C,A";
                String A = "L,10,R,8,R,6,R,10";
                String B = "L,12,R,8,L,12";
                String C = "L,10,R,8,R,8";
                for (int i=0;i<main.length();i++) {
                    input.add((long) main.charAt(i));
                }
                input.add(10L);
                intCode.solve(input, output);
                for (int i=0;i<A.length();i++) {
                    input.add((long) A.charAt(i));
                }
                input.add(10L);
                intCode.solve(input, output);
                for (int i=0;i<B.length();i++) {
                    input.add((long) B.charAt(i));
                }
                input.add(10L);
                intCode.solve(input, output);
                for (int i=0;i<C.length();i++) {
                    input.add((long) C.charAt(i));
                }
                input.add(10L);
                input.add((long) 'n');
                input.add(10L);
                intCode.solve(input, output);
                while (output.size() != 0) {
                    long val = output.poll();
                    if (val < 128) {
                        System.out.print((char) val);
                    } else {
                        return val;
                    }
                }
            }
            return 0;
        }

        private String generateRoutine() {
            int x = 0, y = 0, nx, ny;
            int face = 0;
            int counter = 0;
            for (int i=0;i<row;i++) {
                for (int j=0;j<col;j++) {
                    for (int k=0;k<4;k++) {
                        if (board[i][j] == facing[k]) {
                            x = i;
                            y = j;
                            face = k;
                        }
                    }
                }
            }

            StringBuilder builder = new StringBuilder();
            while (true) {
                nx = x + direction[face][0];
                ny = y + direction[face][1];
                if (nx<0 || nx>=row || ny<0 || ny>=col || board[nx][ny] != '#') {
                    if (counter != 0) {
                        builder.append(counter).append(',');
                    }
                    for (int i=-1;i<=1;i+=2) {
                        nx = x + direction[(face+i+4)%4][0];
                        ny = y + direction[(face+i+4)%4][1];
                        if (nx>=0 && nx<row && ny>=0 && ny<col && board[nx][ny] == '#') {
                            builder.append(i == -1 ? "L," : "R,");
                            face = (face+i+4) % 4;
                            counter = 0;
                            break;
                        }
                    }
                    if (counter != 0) {
                        break;
                    }
                } else {
                    counter++;
                    x = nx;
                    y = ny;
                }
            }

            System.out.println(builder.toString());
            return builder.toString();
        }
    }
}
