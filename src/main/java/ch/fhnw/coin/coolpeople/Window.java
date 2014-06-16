package ch.fhnw.coin.coolpeople;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class Window extends JFrame {
    private final JLabel deletedNames = new JLabel("deleted names");
    private final JLabel integratedNames = new JLabel("integrated names");
    private DefaultListModel<Person> deletedModel = new DefaultListModel<Person>();
    private final JList deletedNamesArea = new JList(deletedModel);
    private DefaultListModel<Person> integratedModel = new DefaultListModel<Person>();
    private final JList integratedNamesArea = new JList(integratedModel);
    private final JButton addName = new JButton( "->" );
    private final JButton deleteName = new JButton( "<-" );
    private JButton createNetwork = new JButton("save");

    public Window() {
        super("CoolPeople");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.setSize(1000, 600);

        panel.setName("CoolPeople");
        panel.add(getEastPanel(), BorderLayout.EAST);
        panel.add(getWestPanel(), BorderLayout.WEST);
        panel.add(getCenterPanel(), BorderLayout.CENTER);
        panel.add(getSouthPanel(), BorderLayout.SOUTH);

        this.getContentPane().add(panel);
        setLocationRelativeTo(null);
        pack();
    }

    /**
     *
     * GUI Layoutdefinition
     *
     * @return
     */

    JComponent getCenterPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(2, 1, 10, 0));
        inner.add(deleteName);
        inner.add(addName);
        deleteName.addActionListener(deleteListener);
        addName.addActionListener(integrateListener);
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
        inner.add(new JScrollPane(integratedNamesArea));
        return inner;
    }

    protected JComponent getSouthPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(1, 4, 40, 0));
        inner.add(createNetwork);
        createNetwork.addActionListener(saveListener);
        return inner;
    }

    /**
     *
     * List for fill the integrate persons
     *
     * @param list
     */

    public void integratedpersonList(ArrayList<Person> list){
        for(int i = 0; i<list.size(); i++){
            integratedModel.addElement(list.get(i));
            System.out.println(list.get(i));
            integratedNamesArea.updateUI();
        }
        integratedNamesArea.setModel(integratedModel);
    }

    public void deletedpersonList(ArrayList<Person> list) {
        for(int i= 0; i<list.size(); i++) {
            deletedModel.addElement(list.get(i));

        }
    }

    /**
     * ActionListener for the buttons
     */

    public ActionListener deleteListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(integratedNamesArea.getSelectedIndex() < 0) return;
            Person p = integratedModel.getElementAt(integratedNamesArea.getSelectedIndex());
            //deletedModel.setElementAt(p,0);
            deletedModel.addElement(p);
            deletedNamesArea.updateUI();
            integratedModel.remove(integratedNamesArea.getSelectedIndex());
        }
    };

    public ActionListener integrateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(deletedNamesArea.getSelectedIndex() < 0) return;
            Person p = deletedModel.getElementAt(deletedNamesArea.getSelectedIndex());
            integratedModel.addElement(p);
            deletedModel.remove(deletedNamesArea.getSelectedIndex());
        }
    };

    public ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(integratedNamesArea.getSelectedIndex() < 0) return;
            for(int i = 0; i<integratedModel.size(); i++) {
                integratedModel.getElementAt(i);
            }
            Person p = integratedModel.getElementAt(integratedNamesArea.getSelectedIndex());

            integratedModel.remove(integratedNamesArea.getSelectedIndex());
        }
    };
}