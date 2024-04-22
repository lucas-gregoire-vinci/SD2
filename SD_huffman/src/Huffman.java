import java.util.*;


public class Huffman {

	private static class Node  implements Comparable<Node>{
		private char ch;
		private int freq;
		private final Node left, right;

		public Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int compareTo(Node o) {
			return this.freq - o.freq ;
		}
	}
	
	// renvoie une map qui a comme cl� les lettres de la chaine de 
	// caract�re donn�e en param�tre et comme valeur la fr�quence de 
	// ces lettres 
	public static Map<Character, Integer> computeFreq(String s) {
		Map<Character, Integer> letters = new HashMap<>();
		for(char c : s.toCharArray()){
			if(letters.containsKey(c)) {
				// Si la lettre est d�j� pr�sente, incr�mentez sa valeur de 1
				letters.put(c, letters.get(c) + 1);
			} else {
				// Sinon, ajoutez-la � la carte avec une valeur de 1
				letters.put(c, 1);
			}
		}
		return letters;
	}	
	
	// renvoie l'arbre de Huffman obtenu � partir de la map des fr�quences des lettres 
	public static Node buildTree(Map<Character, Integer> freq) {
		Queue<Node> file = new PriorityQueue<>();
		for (Character c : freq.keySet()) {
			Node node = new Node(c,freq.get(c),null,null);
			file.add(node);
		}
		while(file.size() > 1){
			Node node1 = file.poll();
			Node node2 = file.poll();
			Node node = new Node('\0', node1.freq + node2.freq, node1, node2);
			file.add(node);
		}
		return file.poll();
	}

	Map<Character, String> tableau = new HashMap<>();
	
	// renvoie une map qui associe chaque lettre � son code (suite de 0 et de 1). 
	// Ce code est obtenu en parcourant l'arbre de Huffman donn� en param�tre
	public static Map<Character, String> buildCode(Node root) {
		Map<Character, String> tableau = new HashMap<>();
		return buildCode(tableau, root, "");
	}


	public static Map<Character, String> buildCode(Map<Character, String> tableau, Node node, String code){
		if(!node.isLeaf()) {
			buildCode(tableau, node.right, code + "1");
			buildCode(tableau, node.left, code + "0");
		}
		else{
			tableau.put(node.ch, code);
		}
		return tableau;
	}

	
	// encode la chaine de caract�re prise en param�tre en une chaine de 
	// bit (0 et 1) en utilisant la map des codes codeMap
	public static String compress(String s, Map<Character, String> codeMap) {
		String newCode = "";
		StringBuilder sBuilder = new StringBuilder(newCode);
		for (Character character : s.toCharArray()) {
			sBuilder.append(codeMap.get(character));
		}
		newCode = sBuilder.toString();
		return newCode;
	}
	
	// Cette m�thode d�code une chaine de 0 et de 1 cod� � l'aide de l'algorithme de Huffman
	// En param�tre, en plus de la chaine � d�coder, est sp�cifi� la racine de l'arbre de 
	// Huffman 
	public static String expand(Node root, String t) {
		
		return null;
	}


	public static void main(String[] args) {
		String s="Bonjour! Au revoir!";
		Map<Character, Integer> freq = computeFreq(s);
		Node root = buildTree(freq);
		Map<Character, String> code= buildCode(root);
		String compress = compress(s, code);
		System.out.println(compress);
	}
	
}
