package com.s185020.Galgeleg_2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void orderCheck() {
        /*5 Order Check Write a function inorder that takes three integers a, b and c as arguments and returns true if the
values are in strictly increasing or strictly decreasing order (ie. a < b < c or a > b > c) and otherwise false.*/
        System.out.println(inorder(1, 2, 3));
        System.out.println(inorder(3, 2, 1));
        System.out.println(inorder(2, 1, 3));
        System.out.println(inorder(3, 1, 2));
    }

    public boolean inorder(int a, int b, int c) {
        boolean inorderBool = false;

        if (a >= b && b >= c || c >= b && b >= a) {
            inorderBool = true;
        }
        return inorderBool;
    }

    //6 Exchange of Elements
    /*6.1 Write a function swap that given an array A and two indexes i and j exchanges the elements on index i and j in A.
    Output the elements of A before and after the exchange.*/

    @Test
    public void swapCheck() {
        int[] A = {1, 2, 3, 4, 5};

        swap(A, 1, 3);
    }

    void swap(int[] A, int i, int j) {
        if (A.length < i || A.length < j) {
            System.out.println("error - length");
            return;
        }

        for (int index : A) {
            System.out.println(index);
        }

        int placeHolderForI = A[i];

        A[i] = A[j];
        A[j] = placeHolderForI;

        for (int index : A) {
            System.out.println(index);
        }
    }

    /*6.2 Write a function reverse that given an array A reverses the order of the elements in A.*/

    @Test
    public void reverseCheck() {
        int[] A = {1, 2, 3, 4, 5};
        A = reverse(A);
        for (int index : A) {
            System.out.println(index);
        }
    }

    int[] reverse(int[] A) {
        int[] reversedA = new int[A.length];

        for (int i = 0; i < A.length; i++) {
            reversedA[A.length - (i + 1)] = A[i];
        }
        return reversedA;
    }

    /*7 Distance Write a function dist that given two numbers x and y returns the (euclidean) distance between the
    origin (0, 0) and (x, y) in the plane*/
    @Test
    public void distCheck() {
        System.out.println(dist(5, 2));
    }

    double dist(double x, double y) {
        return Math.sqrt((Math.pow(x - 0, 2) + Math.pow(y - 0, 2)));
    }

    /*8 Three Sort
    Write a function sort that given three integers a, b and c outputs the integers in order. Use Math.min()
    and Math.max() (or equivalent in the language you use).*/

    @Test
    public void sortCheck() {
        sort2(2, 7, 4);
    }

    void sort2(int a, int b, int c) {
        int[] A = {a, b, c};

        if (Math.max(c, Math.min(a, b)) < Math.max(c, Math.max(a, b))) {
            System.out.println(Math.min(c, Math.min(a, b)));
            System.out.println(Math.max(c, Math.min(a, b)));
            System.out.println(Math.max(c, Math.max(a, b)));
        } else {
            System.out.println(Math.min(c, Math.min(a, b)));
            System.out.println(Math.max(b, Math.min(a, c)));
            System.out.println(Math.max(c, Math.max(a, b)));
        }
    }


    /*9 Factorial
    Write a function fact that given an integer x computes x! = x · (x − 1)··· 1. Your solution must be
    recursive, ie. fact must compute x! by calling itself.*/
    @Test
    public void fectTest() {
        System.out.println(factorial(5));
    }

    private static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }

/*10 Heads or Tails Write a function flip that given an integer n and a probability p, simulates n coin throws with a
probability of p for flipping heads. Output the sequence of outcomes.*/


    /*11 Binary Integers Write a function bin that given a positive integer x outputs the binary representation of x.*/


/*12 Matrix Multiplication Write a function matrixMul that given two n×n matrices A and B as 2-dimensional arrays,
computes the matrix product AB.*/


/*13 Longest Plateau Write a function plateau that given an array A of integers finds the longest consecutive sequence
of equal values where the value just before and the value just after are smaller.*/


}