/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AnagramDictionary {
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 4;
    private Random random = new Random();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, String> words=new HashMap<>();
    private HashSet<String> wordSet=new HashSet<>();

    AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            addWordsToHMap(word);
        }
    }

    private  void addWordsToHMap(String word) {
        wordSet.add(word);
        words.put(words.size(),word);
        String sortLetters=sortLetters(word);
        if(lettersToWord.containsKey(sortLetters)){
            Objects.requireNonNull(lettersToWord.get(sortLetters)).add(word);
        }
        else{
            ArrayList<String> tempList=new ArrayList<>();
            tempList.add(word);
            lettersToWord.put(sortLetters,tempList);
        }
    }
    boolean isGoodWord(String word, String base) {
        if(!wordSet.contains(word))
            return false;
        if(word.contains(base))
            return false;
        return true;
    }

    List<String> getAnagrams(String targetWord) {
        if(targetWord==null || targetWord.equals(""))
            return null;
        String letters=sortLetters(targetWord);
        if(lettersToWord.containsKey(letters))
            return lettersToWord.get(letters);
        return null;
    }

    static String sortLetters(String input) {
        char[] letters=input.toCharArray();
        Arrays.sort(letters);
        return String.valueOf(letters);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char ch='a';ch<='z';ch++){
            List<String> temp=getAnagrams(word+ch);
            if(temp!=null){
                result.addAll(temp);
            }
        }
        return result;
    }

    String pickGoodStarterWord() {
        int n=random.nextInt(words.size());
        String w=words.get(n);
        if(w.length()>DEFAULT_WORD_LENGTH && w.length()<MAX_WORD_LENGTH)
            return w;
        else
            return pickGoodStarterWord();
    }
}