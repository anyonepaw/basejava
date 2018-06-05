package ru.javawebinar.basejava;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		final String[] arr = {"meertrt", "meee", "me", "mee", "abcdrc"};
		sort(arr);
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
	}

	public static void sort(String array[]) {
		for (int i = 1; i < array.length; i++) {
			String x = array[i];
			// Find location to insert using binary search
			int j = Arrays.binarySearch(array, 0, i, x);
			j = Math.abs(j + 1);
			//Shifting array to one location right
			System.arraycopy(array, j, array, j + 1, i - j);
			//Placing element at its correct location
			array[j] = x;
		}
	}

}
