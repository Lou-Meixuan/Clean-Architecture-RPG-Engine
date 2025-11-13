package Battle_System.View;

import Battle_System.Interface_Adapter.Battle.Battle_Controller;
import Battle_System.Interface_Adapter.Battle.Battle_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Battle_View extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Battle";
    private final Battle_ViewModel viewModel;

    private final JLabel userHpLabel = new JLabel("N/A");
    private final JLabel monsterHpLabel  = new JLabel("N/A");
    private final JButton fightBtn = new JButton("Fight");

    private Battle_Controller battleController = null;

    public Battle_View(Battle_ViewModel battleViewModel) {
        this.viewModel = battleViewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(12, 12));
        JPanel top = new JPanel(new GridLayout(1, 2, 8, 8));
        top.add(userHpLabel);
        top.add(monsterHpLabel);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(fightBtn);
        bottom.add(healBtn);
        add(bottom, BorderLayout.SOUTH);
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
