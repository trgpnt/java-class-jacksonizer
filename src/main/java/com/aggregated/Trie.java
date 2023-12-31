package com.aggregated;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Trie's implementation
 * works only on lower-cased letters and digits.
 */
public class Trie {
    private static final Trie INSTANCE = new Trie();
    /**
     * 26 letters : a - z
     * 10 digits : 0 - 9
     */
    private static final int SIZE = 36;
    private static final int DIGIT_REBOUNCE = 22;
    private TrieNode root;
    private Trie() {
        root = new TrieNode();
    }
    public static Trie current() {
        return INSTANCE;
    }
    public void reset() {
        root = new TrieNode();
    }
    public void addWord(List<String> stringList) {
        for (String each : stringList) {
            addWord(each.toLowerCase());
        }
    }
    public void addWord(String inp) {
        try {
            inp = inp.toLowerCase();
            TrieNode current = root;
            for (Character each : inp.toCharArray()) {
                if (!Character.isLetterOrDigit(each)) {
                    continue;
                }
                int idx = StringUtils.asciiValueOf(each, Boolean.TRUE);
                if (idx >= 26) {
                    idx -= DIGIT_REBOUNCE;
                }
                if (Objects.isNull(current.trieNodes[idx])) {
                    current.trieNodes[idx] = new TrieNode();
                }
                current = current.trieNodes[idx];
            }
            current.count++;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public boolean containsData() {
        return containsData(root, new AtomicBoolean(false));
    }
    public boolean containsData(TrieNode node, AtomicBoolean res) {
        if (CollectionUtils.isNotEmpty(Arrays.asList(node.trieNodes))) {
            return true;
        }
        for (TrieNode eachNode : node.trieNodes) {
            res.set(containsData(eachNode, res));
        }
        return res.get();
    }
    public int searchWord(String inp) {
        TrieNode current = root;
        for (Character each : inp.toCharArray()) {
            if (!Character.isLetterOrDigit(each)) {
                continue;
            }
            int idx = StringUtils.asciiValueOf(each, Boolean.TRUE);
            if (idx >= 26) {
                idx -= DIGIT_REBOUNCE;
            }
            if (Objects.isNull(current.trieNodes[idx])) {
                break;
            }
            current = current.trieNodes[idx];
        }
        return current.count;
    }
    static class TrieNode {
        TrieNode[] trieNodes;
        int count;
        private TrieNode() {
            count = 0;
            trieNodes = new TrieNode[SIZE];
            for (int i = 0; i < SIZE; i++) {
                trieNodes[i] = null;
            }
        }
    }
}
