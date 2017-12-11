package game;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import errors.AltInstrumentError;
import errors.AttInstrumentError;
import errors.ComputerOverloadedError1201;
import errors.ComputerOverloadedError1202;
import errors.FuelLeakError;
import errors.FullThrottleError;
import errors.LeftThrusterError;
import errors.RightThrusterError;
import errors.VwInstrumentError;
import errors.VxInstrumentError;
import errors.VyInstrumentError;

@SuppressWarnings("serial")
public class ErrorButtonPanel extends JPanel {

    private final JButton FullThrottle;

    private final JButton stuckLeftThruster;

    private final JButton stuckRightThruster;

    private final JButton error1201;

    private final JButton error1202;

    private final JButton fuel;

    private final JButton vxIns;

    private final JButton vyIns;

    private final JButton vwIns;

    private final JButton attIns;

    private final JButton altIns;

    ErrorButtonPanel(GameState gs) {

        super();
        // Set layout
        setLayout(new GridLayout(6, 2));

        FullThrottle = new JButton();
        FullThrottle.setText("Full Throttle: " + gs.getErrorFreq(new FullThrottleError()));
        FullThrottle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new FullThrottleError());
                FullThrottle.setText("Full Throttle: " + gs.getErrorFreq(new FullThrottleError()));

            }
            //
        });
        this.add(FullThrottle);
        stuckLeftThruster = new JButton();
        stuckLeftThruster
                .setText("Stuck Left Thruster: " + gs.getErrorFreq(new LeftThrusterError()));
        stuckLeftThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new LeftThrusterError());
                stuckLeftThruster.setText(
                        "Stuck Left Thruster: " + gs.getErrorFreq(new LeftThrusterError()));

            }

        });
        this.add(stuckLeftThruster);
        stuckRightThruster = new JButton();
        stuckRightThruster
                .setText("Stuck Right Thruster: " + gs.getErrorFreq(new RightThrusterError()));
        stuckRightThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new RightThrusterError());
                stuckRightThruster.setText(
                        "Stuck Right Thruster: " + gs.getErrorFreq(new RightThrusterError()));

                // TODO Auto-generated method stub

            }

        });

        // TODO:reset errors, not game
        this.add(stuckRightThruster);
        error1201 = new JButton();
        error1201.setText("Error 1201: " + gs.getErrorFreq(new ComputerOverloadedError1201()));
        error1201.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new ComputerOverloadedError1201());
                error1201.setText(
                        "Error 1201: " + gs.getErrorFreq(new ComputerOverloadedError1201()));

            }

        });
        this.add(error1201);

        error1202 = new JButton();
        error1202.setText("Error 1202: " + gs.getErrorFreq(new ComputerOverloadedError1202()));
        error1202.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new ComputerOverloadedError1202());
                error1202.setText(
                        "Error 1202: " + gs.getErrorFreq(new ComputerOverloadedError1202()));

                // TODO Auto-generated method stub

            }

        });
        this.add(error1202);

        fuel = new JButton();
        fuel.setText("Fuel Leak: " + gs.getErrorFreq(new FuelLeakError()));
        fuel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new FuelLeakError());
                fuel.setText("Fuel Leak: " + gs.getErrorFreq(new FuelLeakError()));

                // TODO Auto-generated method stub

            }

        });
        this.add(fuel);

        vxIns = new JButton();
        vxIns.setText("Horizontal Velocity Failure: " + gs.getErrorFreq(new VxInstrumentError()));
        vxIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VxInstrumentError());
                vxIns.setText(
                        "Horizontal Velocity Failure: " + gs.getErrorFreq(new VxInstrumentError()));

                // TODO Auto-generated method stub

            }

        });
        this.add(vxIns);

        vyIns = new JButton();
        vyIns.setText("Vertical Velocity Failure: " + gs.getErrorFreq(new VyInstrumentError()));
        vyIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VyInstrumentError());
                vyIns.setText(
                        "Vertical Velocity Failure: " + gs.getErrorFreq(new VyInstrumentError()));

                // TODO Auto-generated method stub

            }

        });
        this.add(vyIns);

        vwIns = new JButton();
        vwIns.setText("Rotational Velocity Failure: " + gs.getErrorFreq(new VwInstrumentError()));
        vwIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VwInstrumentError());
                vwIns.setText(
                        "Rotational Velocity Failure: " + gs.getErrorFreq(new VwInstrumentError()));

                // TODO Auto-generated method stub

            }

        });
        this.add(vwIns);

        attIns = new JButton();
        attIns.setText("Attitude Indicator Failure: " + gs.getErrorFreq(new AttInstrumentError()));
        attIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new AttInstrumentError());
                attIns.setText(
                        "Attitude Indicator Failure: " + gs.getErrorFreq(new AttInstrumentError()));

                // TODO Auto-generated method stub

            }

        });
        this.add(attIns);

        altIns = new JButton();
        altIns.setText("Altitude Indicator Failure: " + gs.getErrorFreq(new AltInstrumentError()));
        altIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new AltInstrumentError());
                altIns.setText(
                        "Altitude Indicator Failure: " + gs.getErrorFreq(new AltInstrumentError()));

                // TODO Auto-generated method stub

            }

        });
        this.add(altIns);

    }

    @Override
    public Dimension getPreferredSize() {
        // TODO:FIx to make correct
        return new Dimension(700, 400);
    }

}
