import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by mtumilowicz on 2018-10-01.
 */
public class IteratorsTest {
    
    @Test(expected = ConcurrentModificationException.class)
    public void failFast() {
        List<String> list = new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5"));
        Iterator<String> iterator = list.iterator();
        int counter = 0;
        while (iterator.hasNext()) {
            if (counter == 3) {
                list.add("6");
            }
            counter++;
            iterator.next();
        }
    }
    
    @Test
    public void failSafe() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        Iterator<String> iterator = list.iterator();
        int counter = 0;
        ArrayList<String> iteratedNodes = new ArrayList<>();
        while (iterator.hasNext()) {
            if (counter == 3) {
                list.add("6");
            }
            counter++;
            iteratedNodes.add(iterator.next());
        }
        
        assertThat(iteratedNodes, hasSize(list.size() - 1));
    }
    
    @Test
    public void weaklyConsistent() {
        ConcurrentSkipListSet<String> list = new ConcurrentSkipListSet<>(Arrays.asList("1", "2", "3", "4", "5"));
        Iterator<String> iterator = list.iterator();
        int counter = 0;
        ArrayList<String> iteratedNodes = new ArrayList<>();
        while (iterator.hasNext()) {
            if (counter == 3) {
                list.add("6");
            }
            counter++;
            iteratedNodes.add(iterator.next());
        }

        assertThat(iteratedNodes, hasSize(list.size()));
    }
}
