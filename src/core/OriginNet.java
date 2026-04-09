package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * idea of this class come from Wordnet. assume this works.
 * This class has great flaw that the int[2] to indicate origin coordinates is never checked.
 * Passing in a weird int[] would be catastrophic
 */
public class OriginNet {
    public ArrayList<Origin> list = new ArrayList<>();
    public HashMap<Origin, HashSet<Origin>> net = new HashMap<>();
    public final Origin center;

    public OriginNet() {
        // do nothing
        center = new Origin(0, 0);
    }

    public OriginNet(int length, int width) {
        center = new Origin(length / 2, width / 2);
    }

    public void addNode(Origin node) {
        list.add(node);
    }

    public void buildNet() {
        for (Origin origin : list) {
//            Origin parent = findClosest(origin, list.getFirst(), distanceBetween(origin, list.getFirst()));
            Origin parent = findClosest(origin, list.getFirst(), list.getFirst());
            if (parent.equals(origin)) continue;
            if (distanceBetween(parent, origin) < 5) continue;

            insert(parent, origin);
        }
    }

//    public Origin findClosest(Origin node, Origin parent, double min) {
//        HashSet<Origin> targets = net.get(parent);
//        if (targets == null) return parent;
//        double minDistance = min;
//        Origin closest = parent;
//        double temp;
//        Iterator<Origin> iterator = targets.iterator();
//        while (iterator.hasNext()) {
//            Origin target = iterator.next();
//            temp = distanceBetween(node, target);
//            if (temp <= distanceBetween(target, parent)) {
//                iterator.remove();
//                insert(node, target);
//                continue;
//            }
//            if (temp < minDistance) {
//                closest = target;
//                minDistance = temp;
//            }
//        }
//        if (minDistance == min && closest.equals(parent)) return parent;
//        return findClosest(node, closest, minDistance);
//    }

    public Origin findClosest(Origin node, Origin parent, Origin closest) {
        System.out.println(node + " " + parent + " " + distanceBetween(node, parent));
        if (distanceBetween(parent, node) < distanceBetween(node, closest)) closest = parent;
        if (net.get(parent) == null) return closest;
        Iterator<Origin> iterator = net.get(parent).iterator();
        while (iterator.hasNext()) {
            Origin target = iterator.next();
            if (distanceBetween(node, target) <= distanceBetween(target, parent)) {
                iterator.remove();
//                Origin huh = findClosest(node, target, target);

                insert(node, target);
                continue;
            }
            Origin found = findClosest(node, target, closest);
            if (distanceBetween(node, found) < distanceBetween(node, closest)) closest = found;
        }
        return closest;
    }

    public void insert(Origin node) {
        double value = distanceBetween(node, center);
        treeInsert(node, tree.head());
    }

    public double distanceBetween(Origin p, Origin q) {
        return Math.sqrt(Math.pow(p.x() - q.x(), 2) + Math.pow(p.y() - q.y(), 2));
    }

    public void insert(Origin node, Origin child) {
        if (net.containsKey(node)) {
            HashSet<Origin> set = net.get(node);
            set.add(child);
            // this is actually equivalent to the line below
        } else {
            net.put(node, new HashSet<>());
            net.get(node).add(child);
        }
    }

    public ArrayList<Origin> origins() {
        return list;
    }

}
