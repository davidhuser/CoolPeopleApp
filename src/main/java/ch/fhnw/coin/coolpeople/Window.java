package ch.fhnw.coin.coolpeople;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class Window extends JFrame {
    ArrayList<Person> nameList = Main.templist;
    private final JLabel deletedNames = new JLabel("deleted names");
    private final JLabel integratedNames = new JLabel("integrated names");
    //Java 7 has no generics for JList (no new JList<String>();)
    private DefaultListModel<Person> deletedModel = new DefaultListModel<Person>();
    private final JList deletedNamesArea = new JList();
    private DefaultListModel<Person> integratedModel = new DefaultListModel<Person>();
    private final JList integratedNamesArea = new JList(integratedModel);
    private final JButton addName = new JButton( "->" );
    private final JButton deleteName = new JButton( "<-" );
    private JButton createNetwork = new JButton("save");
    //Person[] personsInList = nameList.toArray(new Person[0]);

    public Window() {
        super("CoolPeople");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


        //for(int i =0; i<personsInList.length; i++) {
        //String[] personNameList = personsInList.toString();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setSize(1000, 600);
        panel.setName("CoolPeople");
        //JFrame myFrame = new JFrame("CoolPeople");
        panel.add(getEastPanel(), BorderLayout.EAST);
        panel.add(getWestPanel(), BorderLayout.WEST);
        panel.add(getCenterPanel(), BorderLayout.CENTER);
        panel.add(getSouthPanel(), BorderLayout.SOUTH);

        this.getContentPane().add(panel);
        pack();
    }

    JComponent getCenterPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 10, 0));
        inner.add(deleteName);
        inner.add(addName);
        deleteName.addActionListener(deleteListener);
        return inner;
    }

    JComponent getWestPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 40, 0));
        inner.add(deletedNames);
        inner.add(deletedNamesArea);
        return inner;
    }

    JComponent getEastPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 40, 0));
        inner.add(integratedNames);
        inner.add(integratedNamesArea);
        return inner;
    }

    protected JComponent getSouthPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(1, 4, 40, 0));
        inner.add(createNetwork);
        return inner;
    }

    public void integratedpersonList(ArrayList<Person> list){
        for(int i = 0; i<list.size(); i++){
            integratedModel.addElement(list.get(i));
        }
    }

    public void deletedpersonList(ArrayList<Person> list) {
        for(int i= 0; i<list.size(); i++) {
            Person p = integratedModel.getElementAt(integratedNamesArea.getSelectedIndex());
        }
    }

    public ActionListener deleteListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(integratedNamesArea.getSelectedIndex() < 0) return;
            Person p = integratedModel.getElementAt(integratedNamesArea.getSelectedIndex());
            deletedModel.setElementAt(p,0);
            integratedModel.remove(integratedNamesArea.getSelectedIndex());
        }
    };

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