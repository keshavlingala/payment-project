package tech.keshav.payment.paymentproject.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Permutations {

    private final List<String> allCombinations=new ArrayList<>();

    void permute(List<String> words, int k){
        for(int i = k; i < words.size(); i++){
            Collections.swap(words, i, k);
            permute(words, k+1);
            Collections.swap(words, k, i);
        }
        if (k == words.size() -1){
            allCombinations.add(String.join(" ",words));

        }
    }

    public Permutations(List<String> words){
        this.permute(words,0);
    }

    public List<String> getAllCombinations() {
        return allCombinations;
    }
}
