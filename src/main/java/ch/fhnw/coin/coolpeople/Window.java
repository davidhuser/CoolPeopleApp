package ch.fhnw.coin.coolpeople;


import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by igorbosnjak on 27.05.14.
 */
public class Window extends JFrame {
    SortedSet<Object> model;
    private JPanel panel = new JPanel();
    private JLabel deletedNames = new JLabel("deleted names");
    private JLabel integratedNames = new JLabel("integrated names");
    private JList deletedNamesArea = new JList<String>();
    private JList integratedNamesArea = new JList<String>();
    private JButton addName = new JButton( "add name" );
    private JButton deleteName = new JButton( "delete name" );

    public Window() {
        //model = new TreeSet<Object>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new BorderLayout());
        panel.setSize(450,300);
        panel.setName("CoolPeople");
        //JFrame myFrame = new JFrame("CoolPeople");
        panel.add(getEastPanel(), BorderLayout.EAST);
        panel.add(getWestPanel(), BorderLayout.WEST);
        panel.add(getCenterPanel(), BorderLayout.CENTER);

        this.getContentPane().add(panel);
        pack();
    }

    protected JComponent getCenterPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 10, 0));
        inner.add(deleteName);
        inner.add(addName);
        return inner;
    }

    protected JComponent getWestPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 40, 0));
        inner.add(deletedNames);
        inner.add(deletedNamesArea);
        return inner;
    }

    protected JComponent getEastPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 40, 0));
        inner.add(integratedNames);
        inner.add(integratedNamesArea);
        return inner;
    }

    /*
    public int getSize() {
        return model.size();
    }

    public Object getElementAt(int index) {
        return model.toArray()[index];
    }

    public void add(Object element) {
        if (model.add(element)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(Object elements[]) {
        Collection<Object> c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(Object element) {
        return model.contains(element);
    }

    public Object firstElement() {
        return model.first();
    }

    public Iterator iterator() {
        return model.iterator();
    }

    public Object lastElement() {
        return model.last();
    }

    public boolean removeElement(Object element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }*/
}