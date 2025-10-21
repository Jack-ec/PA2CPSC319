// ====================================================================
// CPSC 319, F25, PA-2: Searching & Sorting (12%)
// Efficient Anagram Extraction & Grouping
//
// NAME: Jack Chidlaw
// UCID: 30187692
//
// ====================================================================

//Comments without a "Jack Chidlaw:" infront of them were provided by the skeleton code JAVA file.
// AI resources used: predictive code feature provided by IntelliJ IDE
// All code was written by me with the help of independent research, Sources: Stack Overflow, docs.oracle.com and class notes


import java.io.*;
import java.util.*;

// ============================
// MAIN CLASS (PA2)
// ============================

public class PA2 {

	public static void main(String[] args) {
		try {
            // Prompt the user to enter an input file name, read the filename from standard input,
            //          and print a confirmation message before proceeding with file operations.

			//Jack Chidlaw: user will have to input the FULL file name including the ".txt"
			//input files will need to be in the same location as the project and the output files will be saved to the same location as the project

            Scanner sc;
            sc = new Scanner(System.in);
            System.out.println("Please Enter a File name: ");
            String inputFileName;
            inputFileName = sc.next();

            // Debugging: Print filename before reading
            System.out.println();
            System.out.println("=============================================");
            System.out.println(" 📂  Reading input file: " + inputFileName);

            // Read the input file and store its contents as a string.
            // If the file is empty or cannot be read, print an error message and terminate execution.

            String fileContents = readFile(inputFileName);

            // Process input text to extract words into a 1-D array.
            // Trim input, split words by whitespace, and store words in an array.

            String[] words = fileContents.split(" ");

            // Debugging: Print words before sorting
            System.out.println("---------------------------------------------");
            System.out.println(" ⊗  Words before sorting: " + Arrays.toString(words));
            System.out.println("---------------------------------------------");

            // Call MergeSort to sort words alphabetically.
			MergeSort.mergeSort(words, 0, words.length-1);

            // Debugging: Print words after sorting
            System.out.println("*********************************************");
            System.out.println(" ✓ Words after sorting: " + Arrays.toString(words));

            // Call AnagramGrouper.groupAnagrams() to group words into singly linked lists (anagram groups).
            Map<String, SinglyLinkedList> anagramGroups = AnagramGrouper.groupAnagrams(words);

            // Debugging: Print grouped anagrams before final output
            System.out.println("\n =============================================");
            System.out.println(" {} Grouped Anagrams: " + anagramGroups);

            // Call printFinalOutput() to print and save the final formatted output (anagram groups).

            printFinalOutput(anagramGroups, inputFileName);

        } catch (Exception e) {  // Catch exceptions and handle errors.
			e.printStackTrace(); // Print stack trace for debugging.
		}
	}

	// ================================
	// FUNCTION TO READ FILE CONTENT
	// ================================

	private static String readFile(String fileName) throws IOException {

		// Create a File object for the given file name.
		File file = new File(fileName);

		// Jack Chidlaw: Check if the file exists before attempting to read and print message if it doesn't.
		if (!file.exists()) {
			System.err.println("The file could not be found or does not exist.");
			return "";

		}

		StringBuilder fileContents = new StringBuilder();

		// Use BufferedReader to read the file line by line.
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		// variable to store each line read from the file.
		String line;

		// Jack Chidlaw: Iterate through the file and read it line by line. append the line to StringBuilder than append a space char.
		while ((line = reader.readLine()) != null) {
			fileContents.append(line).append(" ");
		}

		// Close BufferedReader.
		reader.close();

		// Return the final string containing the file content.
		return fileContents.toString();
	}

	// ==========================================
	// FUNCTION TO PRINT AND SAVE FINAL OUTPUT
	// ==========================================

	public static void printFinalOutput(Map<String, SinglyLinkedList> groups, String inputFileName) {

		// Print a header for the final grouped anagrams output.
		System.out.println("Groups of words that are anagrams:");

		// Initialize a counter to number the anagram groups in the output.
		int i = 0;

		// Create a StringBuilder to store the formatted output before saving to a file.
		StringBuilder stringBuilder = new StringBuilder();

		// Iterate over the grouped anagrams (values of the map).
		for (SinglyLinkedList group : groups.values()) {

			// Ensure the group is not null and contains words before printing.
			if (group != null) {

				// Format the group as a numbered entry and remove any extra spaces.
				String formattedGroup = String.valueOf(i) + ":" + String.valueOf(group);

				// Print the formatted group to the console.
				System.out.println(formattedGroup);

				// Append the formatted group to the output content for file saving.
				stringBuilder.append(System.lineSeparator());
				stringBuilder.append(formattedGroup);

				// Increment the counter for the next group.
				i++;
			}
		}

		// ==========================
		// FILE OUTPUT HANDLING
		// ==========================

		// Save the final grouped anagrams to a text file with a modified filename based on the input file.
		File saveFile = new File(inputFileName + "sorted");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
			// Write the final grouped anagrams output to the specified file.
			writer.write(String.valueOf(stringBuilder));

			// Print a confirmation message indicating successful file saving.
			System.out.println("Save Successful");

			// Handle any IOException that may occur during file writing.
		} catch (IOException e) {

			// Print an error message to standard error (stderr) if file writing fails.
			System.err.println("Failed to Save File");
		}
	}
}

// ============================
// MERGE SORT CLASS
// ============================
class MergeSort {

	public static void mergeSort(String[] array, int left, int right) {

		// left represents the starting index of the current subarray 'array'.
		// right represents the ending index of the current subarray 'array'.

		// Jack Chidlaw: if the subarray has a smaller starting index than ending index than split it
		if (left < right)  {

			// Calculate the middle index to divide the array into two halves.
			int mid = (left + right) / 2;

			// Debugging: Print subarray being sorted
			System.out.println(Arrays.toString(array));

			// Recursively sort the left half of the array.
			mergeSort(array, left, mid);

			// Recursively sort the right half of the array.
			mergeSort(array, mid + 1, right);

			// Merge the sorted left and right halves.
			merge(array, left, mid, right);

		}
	}

	// ==================================================
	// MERGE FUNCTION to combine two sorted subarrays.
	// ==================================================

	private static void merge(String[] array, int left, int mid, int right) {


		// Compute the sizes of the two subarrays to be merged.
		int leftSize = mid - left + 1;
		int rightSize = right - mid;

		// Create temporary arrays to store elements from the left and right subarrays.
		String[] leftArray = new String[leftSize];
		String[] rightArray = new String[rightSize];


		// Copy data from the original array into the left and right subarrays.
		manualCopy(array, left, leftArray, leftSize, leftSize);
		manualCopy(array, mid + 1, rightArray, rightSize, rightSize);


		// Debugging: Print subarrays before merging
		System.out.println("( ................... )");
		System.out.println(" ∪ Merging: " + Arrays.toString(leftArray) + " and " + Arrays.toString(rightArray));

		// Merge the two subarrays by comparing their elements.

		//Jack Chidlaw: Initialise counters to iterate through subarrays
		int i =0;
		int j = 0;

		int k = left;

		//Jack Chidlaw: compare front of subarrays and append accordingly to array
		while (i < leftSize && j < rightSize) {
			if (leftArray[i].compareTo(rightArray[j]) < 0) {
				array[k] = leftArray[i];
				i++;
			}
			else {
				array[k] = rightArray[j];
				j++;
			}
			k++;
		}

		// Copy any remaining elements from `leftArray` to `array`.
		while (i < leftSize) {
			array[k] = leftArray[i];
			i++;
			k++;
		}

		// Copy any remaining elements from `rightArray` to `array`.
		while (j < rightSize) {
			array[k] = rightArray[j];
			j++;
			k++;
		}

		// Debugging: Print merged array
		System.out.println(" ≡ After Merge: " + Arrays.toString(Arrays.copyOfRange(array, left, right + 1)));
	}


	// =============================
	// MANUAL ARRAY COPY FUNCTION
	// =============================
	private static void manualCopy(String[] source, int sourceStart, String[] destination, int destStart, int length) {
		// Iterate over the given range and copy elements
		for (int i =0; i < length; i++) {
			// Copy each element from source to destination at the correct index
			destination[i] = source[sourceStart + i];
		}
	}
}

// ====================================
// SINGLY LINKED LIST CLASS
// ====================================

class SinglyLinkedList {

	// Declare a head node representing the start of the linked list
	private Node head;

	// Define the node structure for a singly linked list.
	private static class Node {
		String data;
		Node next;
		Node(String data) {
			this.data = data;
			this.next = null;
		}
	}

	// =====================================================
	// ADD SORTED METHOD to insert a word in sorted order
	// =====================================================
	public void addSorted(String word) {

		// Allocate new Node
		Node node = new Node(word);


		// Handle insertion at the beginning of the list:
		// If the list is empty (i.e., head == NULL) OR
		// If word comes before head.data alphabetically, THEN we insert at the beginning.
		// Return immediately after inserting at the head to avoid unnecessary traversal.

		if (head == null) {
			head = node;
			return;
		}

		// Position 'current' at the beginning of the singly linked list
		Node current = head;

		// Traverse to find correct insertion point
		while (current.next != null) {
			current = current.next;
		}

		// Insert the new node at the correct position
		current.next = node;
	}


	// Implement method to check if the list is empty
	public boolean isEmpty() {
		return head == null;
	}

	@Override

	// Convert the linked list to a formatted string representation
	public String toString() {

		// Initialize a StringBuilder to store the result

		StringBuilder sb = new StringBuilder();

		// Start from the head of the linked list

		Node current = head;

		// Traverse the entire linked list
		while (current != null) {

			// Append the current node's data to the string
			sb.append(current.data);

			// Add a space if there is another node after this
			if (current.next != null) {
				sb.append(" ");
			}

			// Move to the next node in the list
			current = current.next;
		}

		// Return the final formatted string
		return sb.toString();
	}
}


// =======================================================
// ANAGRAM GROUPER CLASS (with Insertion Sort)
//
// Groups words into anagram groups using a LinkedHashMap
// This method groups words that are anagrams of each other by mapping each word to a canonical form (a sorted version of its characters).
// Words that share the same canonical form belong to the same group and are stored in a singly linked list.
// Key Idea: Two words are anagrams if they have the same characters in the same frequency.
// =======================================================
class AnagramGrouper {

	public static Map<String, SinglyLinkedList> groupAnagrams(String[] words) {

		// Store anagram groups while preserving insertion order
		Map<String, SinglyLinkedList> map = new LinkedHashMap<>();

		// Iterate through each word in the words array and processing them to group anagrams together.
		for (int i =0; i < words.length; i++) {

			// Extract the current word from the words array
			String word = words[i];

			// Call computeCanonicalForm() to get sorted-character form of the word (anagram key)
			String canonicalForm = computeCanonicalForm(word);

			// Debugging: Print computed canonical form
			System.out.println("*********************************************");
			System.out.println(" ∴ Canonical form of '" + word + "' is '" + canonicalForm + "'");

			// Create a new linked list if this key does not exist
			map.putIfAbsent(canonicalForm, new SinglyLinkedList());

			// Add the word into the appropriate linked list (maintaining order)
			map.get(canonicalForm).addSorted(word);

			// Debugging: Print updated anagram group
			System.out.println(" + Adding '" + word + "' to group: " + canonicalForm);
		}

		// Return the map of anagram groups
		return map;
	}

	// ===================================================================================================
	// METHOD computeCanonicalForm()
	//
	// This method computes the canonical form of a word by sorting its characters using insertion sort.
	// It helps group anagrams by ensuring words with the same letters get the same representation.
	// Example:
	//      (1) Processing ----------------------------> "stop"
	//      (2) Convert "stop" into a character array -> ['s', 't', 'o', 'p']
	//      (3) Sort using insertion sort -------------> ['o', 'p', 's', 't']
	//      (4) Convert back to string ----------------> "opst"
	// ===================================================================================================

	private static String computeCanonicalForm(String word) {

		// Convert word into a character array
		char[] chars = word.toCharArray();

		// Sort characters in-place calling insertion sort
		insertionSort(chars);

		// Return the converted sorted character array back to a string
		return new String(chars);
	}

	// =================================
	// METHOD insertionSort()
	// Implement insertion sort for sorting characters in a word
	// =================================

	private static void insertionSort(char[] arr) {

		// Debugging: Initial array state
		System.out.println();
		System.out.println("=============================================");
		System.out.println(" ► Starting Insertion Sort on: " + Arrays.toString(arr)); 

		// Iterate over the array starting from index 1.
		for (int i = 1; i < arr.length; i++) {

			// Store the current element (`key`) to be inserted into the sorted section.
			char key = arr[i];

			// Initialize `j` to track the last element in the sorted portion of the array.
			int j = i - 1;

			// Debugging: Show current key being inserted
			System.out.println("-----------------------------");
			System.out.println(" ↳  Inserting '" + key + "' into sorted portion: " + Arrays.toString(Arrays.copyOfRange(arr, 0, key)));

			// Iterate backwards through the sorted portion of the array:
			//- Compare `key` with each element in the sorted section,
			//- Shift elements that are greater than `key` to the right.

			while (j >= 0 && arr[j] > key) {

				// Shift `arr[j]` one position to the right to create space for `key`.

				arr[j + 1] = arr[j];

				// Move `j` one step left to continue shifting process.

				j = j - 1;

				// Debugging: Show shifting process
				System.out.println(" ⟲  Shifting " + Arrays.toString(arr));
			}

			// Place `key` at its correct position after all shifts.
			arr[j + 1] = key;

			// Debugging: Show array state after inserting key
			System.out.println(" ✔  After inserting '" + key + "': " + Arrays.toString(arr));
		}
	}

}
