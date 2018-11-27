public class ScrabbleService {
	private static final Scanner scan = new Scanner(System.in);
	/**
	 * Returns a list of words that can be spelled from the given set of
	 * letters. It is sorted by its Scrabble point value.
	 */
	public static HashMap<String, Set<String>> dictionary(String filename) throws Exception {
		HashMap<String, Set<String>> dictmap = new HashMap<>();
		File file = new File(filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		String[] hostarr;
		while ((line = br.readLine()) != null) {
			hostarr = line.split("\\s+");
			String input_word = hostarr[0];
			char[] ip2 = input_word.toCharArray();
			Arrays.sort(ip2);
			String word = String.copyValueOf(ip2);

			if (!dictmap.containsKey(word)) {
				Set<String> newset = new HashSet<String>();
				newset.add(input_word);
				dictmap.put(word, newset);
			} else if (dictmap.containsKey(word)) {
				Set<String> storedset = dictmap.get(word);
				storedset.add(input_word);
				dictmap.put(word, storedset);
			}
		}

		br.close();
		return dictmap;
	}

	// The following function generates all combinations of a given input string
	// of letters
	public static ArrayList<String> combinations(char[] inp) {
		ArrayList<String> word_combinations = new ArrayList<String>();
		long pow_set_size = (long) Math.pow(2, inp.length);
		int counter, j;
		// Finding all combinations of the letters in the input word {at} ->
		// {a,at}
		for (counter = 0; counter < pow_set_size; counter++) {
			String x = "";
			for (j = 0; j < inp.length; j++) {
				if ((counter & (1 << j)) > 0) {
					x += inp[j];
				}
			}
			// Storing the input word combinations into the "word_combinations"
			// arraylist
			char[] ip2 = x.toCharArray();
			Arrays.sort(ip2);
			String ipword = String.copyValueOf(ip2);
			if (ipword.trim().length() != 0) {
				word_combinations.add(ipword.trim());
			}
		}
		return word_combinations;
	}

// Valid words generator using the combinations and dictionary words
	public static Set<String> validWords(ArrayList<String> word_combinations, HashMap<String, Set<String>> dictmap) {
		Set<String> nonordered_validwords = new HashSet<String>(); // Set containing valid words
		for (int l = 0; l < word_combinations.size(); l++) {
			if (dictmap.containsKey(word_combinations.get(l))) {
				Set<String> validperms = dictmap.get(word_combinations.get(l));
				nonordered_validwords.addAll(validperms);
			}
		}
		return nonordered_validwords;
	}

// Creating a hashmap for score reference
	public static HashMap<Character, Integer> scoreguide() {
		HashMap<Character, Integer> scoremap = new HashMap<Character, Integer>();
		char[] ones = { 'a', 'e', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u' };
		char[] twos = { 'g', 'd' };
		char[] threes = { 'b', 'c', 'm', 'p' };
		char[] fours = { 'f', 'h', 'v', 'w', 'y' };
		char[] fives = { 'k' };
		char[] eights = { 'j', 'x' };
		char[] tens = { 'q', 'z' };
		for (char e : ones) {
			scoremap.put(e, 1);
		}
		for (char e : twos) {
			scoremap.put(e, 2);
		}
		for (char e : threes) {
			scoremap.put(e, 3);
		}
		for (char e : fours) {
			scoremap.put(e, 4);
		}
		for (char e : fives) {
			scoremap.put(e, 5);
		}
		for (char e : eights) {
			scoremap.put(e, 8);
		}
		for (char e : tens) {
			scoremap.put(e, 10);
		}
		return scoremap;
	}

// Scorer function to calculate scores of the valid words
	public static ArrayList<String> scorer(HashMap<Character, Integer> scoremap, Set<String> nonordered_validwords) {

		// TreeMap used to map words to their scores
		TreeMap<Integer, ArrayList<String>> treemap_tosort = new TreeMap<>(Collections.reverseOrder());

		for (String e : nonordered_validwords) {// Calculating scores
			int score = 0;
			char[] chars = e.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				score += scoremap.get(chars[i]);
			}
			ArrayList<String> tempp = treemap_tosort.get(score);
			if (tempp == null) {
				ArrayList<String> newl = new ArrayList<String>();
				newl.add(e);
				treemap_tosort.put(score, newl);
			} else {
				tempp.add(e);
				treemap_tosort.put(score, tempp);
			}
		}
		// Order-wise storing the words in an ArrayList, and simultaneously
		// removing the same from the scored TreeMap to save space
		ArrayList<String> sorted_valid_words = new ArrayList<String>();
		for (ArrayList<String> e : treemap_tosort.values()) {
			for (int i = 0; i < e.size(); i++) {
				sorted_valid_words.add(e.get(i));
			}
		}
		return sorted_valid_words;
	}

	public ArrayList<String> getWords(String letters) throws Exception {
		HashMap<String, Set<String>> dictmap = dictionary("wordsEn.txt");
		HashMap<Character, Integer> scoremap = scoreguide();
		char[] arr = letters.toCharArray();
		ArrayList<String> word_combinations = combinations(arr);
		Set<String> nonordered_validwords = validWords(word_combinations, dictmap);
		ArrayList<String> sorted_valid_words = scorer(scoremap, nonordered_validwords);
		return sorted_valid_words;
	}
}