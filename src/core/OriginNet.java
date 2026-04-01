package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * idea of this class come from Wordnet. assume this works.
 * This class has great flaw that the int[2] to indicate origin coordinates is never checked.
 * Passing in a weird int[] would be catastrophic
 */
public class OriginNet {
    public ArrayList<Origin> list = new ArrayList<>();
    public HashMap<Origin, HashSet<Origin>> map = new HashMap<>();

    public OriginNet() {
        // do nothing
    }

    public void addNode(Origin node) {
        list.add(node);
    }

    public void mapChild(Origin node, Origin child) {
        if (map.containsKey(node)) {
            HashSet<Origin> set = map.get(node);
            set.add(child);
            // this is actually equivalent to the line below
        } else {
            map.put(node, new HashSet<>());
            map.get(node).add(child);
        }
    }

    public ArrayList<Origin> origins() {
        return list;
    }



}
