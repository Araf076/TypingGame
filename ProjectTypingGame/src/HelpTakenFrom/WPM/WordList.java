package HelpTakenFrom.WPM;
/*****
 * @Copyright (C) 2016 Ernesto D Martinez - All Rights Reserved
 *****/

import java.util.HashMap;
import java.util.Random;

public class WordList{
	HashMap wordList;
	int size = 0, index;
	Random rng;
	String word;
	
	public WordList(){
		
		rng = new Random();
		
		wordList = new HashMap<Integer, String>();
		
		wordList.put(0, "the");
		wordList.put(1, "and");
		wordList.put(2, "true");
		wordList.put(3, "after");
		wordList.put(4, "but");
		wordList.put(5, "mercy");
		wordList.put(6, "build");
		wordList.put(7, "house");
		wordList.put(8, "animal");
		wordList.put(9, "chair");
		wordList.put(10, "senate");
		wordList.put(11, "government");
		wordList.put(12, "historic");
		wordList.put(13, "monumental");
		wordList.put(14, "congress");
		wordList.put(15, "holiday");
		wordList.put(16, "baby");
		wordList.put(17, "human");
		wordList.put(18, "figure");
		wordList.put(19, "act");
		wordList.put(20, "move");
		wordList.put(21, "ready");
		wordList.put(22, "set");
		wordList.put(23, "go");
		wordList.put(24, "country");
		wordList.put(25, "dog");
		wordList.put(26, "party");
		wordList.put(27, "restaurant");
		wordList.put(28, "sofa");
		wordList.put(29, "desk");
		wordList.put(30, "kitchen");
		wordList.put(31, "dining");
		wordList.put(32, "diner");
		wordList.put(33, "dinner");
		wordList.put(34, "food");
		wordList.put(35, "kite");
		wordList.put(36, "kitten");
		wordList.put(37, "read");
		wordList.put(38, "astounding");
		wordList.put(39, "zoo");
		wordList.put(40, "phone");
		wordList.put(41, "car");
		wordList.put(42, "telephone");
		wordList.put(43, "lemon");
		wordList.put(44, "union");
		wordList.put(45, "increment");
		wordList.put(46, "couple");
		wordList.put(47, "internet");
		wordList.put(48, "service");
		wordList.put(49, "browser");
		wordList.put(50, "mouse");
		wordList.put(51, "laptop");
		wordList.put(52, "break");
		wordList.put(53, "work");
		wordList.put(54, "world");
		wordList.put(55, "inspire");
		wordList.put(56, "weekend");
		wordList.put(57, "festive");
		wordList.put(58, "creative");
		wordList.put(59, "program");
		wordList.put(60, "meteor");
		wordList.put(61, "shower");
		wordList.put(62, "music");
		wordList.put(63, "notes");
		wordList.put(64, "top");
		wordList.put(65, "meter");
		wordList.put(66, "prince");
		wordList.put(67, "audience");
		wordList.put(68, "duck");
		wordList.put(69, "plus");
		wordList.put(70, "divide");
		wordList.put(71, "subtract");
		wordList.put(72, "multiply");
		wordList.put(73, "donkey");
		wordList.put(74, "cream");
		wordList.put(75, "fish");
		wordList.put(76, "bake");
		wordList.put(77, "beach");
		wordList.put(78, "sand");
		wordList.put(79, "wave");
		wordList.put(80, "mat");
		wordList.put(81, "castle");
		wordList.put(82, "war");
		wordList.put(83, "windy");
		wordList.put(84, "warm");
		wordList.put(85, "truck");
		wordList.put(86, "swim");
		wordList.put(87, "shark");
		wordList.put(88, "study");
		wordList.put(89, "to");
		wordList.put(90, "be");
		wordList.put(91, "campus");
		wordList.put(92, "pizza");
		wordList.put(93, "swear");
		wordList.put(94, "partner");
		wordList.put(95, "professor");
		wordList.put(96, "alumni");
		wordList.put(97, "major");
		wordList.put(98, "curve");
		wordList.put(99, "history");
		wordList.put(100, "graduate");
		wordList.put(101, "shirt");
		wordList.put(102, "pants");
		wordList.put(103, "do");
		wordList.put(104, "commit");
		wordList.put(105, "face");
		wordList.put(106, "city");
		wordList.put(107, "bus");
		wordList.put(108, "bike");
		wordList.put(109, "head");
		wordList.put(110, "run");
		wordList.put(111, "hide");
		wordList.put(112, "ski");
		wordList.put(113, "shot");
		wordList.put(114, "jump");
		wordList.put(115, "fill");
		wordList.put(116, "bit");
		wordList.put(117, "hit");
		wordList.put(118, "pick");
		wordList.put(119, "stick");
		wordList.put(120, "lip");
		wordList.put(121, "jab");
		wordList.put(122, "cab");
		wordList.put(123, "name");
		wordList.put(124, "dock");
		wordList.put(125, "slope");
		wordList.put(126, "shop");
		wordList.put(127, "cop");
		wordList.put(128, "knock");
		wordList.put(129, "way");
		wordList.put(130, "hay");
		wordList.put(131, "hat");
		wordList.put(132, "fat");
		wordList.put(133, "sat");
		wordList.put(134, "bat");
		wordList.put(135, "get");
		wordList.put(136, "jaw");
		wordList.put(137, "lift");
		wordList.put(138, "mind");
		wordList.put(139, "mist");
		wordList.put(140, "gist");
		wordList.put(141, "wind");
		wordList.put(142, "eyes");
		wordList.put(143, "mail");
		wordList.put(144, "helmet");
		wordList.put(145, "velvet");
		wordList.put(146, "nonce");
		wordList.put(147, "ounce");
		wordList.put(148, "gallon");
		wordList.put(149, "back");
		wordList.put(150, "hammer");

		size = wordList.size();
	}
	
	public String getRandomWord(){
		index = rng.nextInt(size);
		word = (String)wordList.get(index);
		return word;
	}
	
	public int getSize(){
		return size;
	}

	/*public static void main(String[] args){
		new WordList();
		//addWord();
	}
	
	public static void addWord(){

	}*/

}
