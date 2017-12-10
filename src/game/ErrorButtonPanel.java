package game;
import java.awt.Dimension;
import java.awt.Graphics;
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
public class ErrorButtonPanel extends JPanel{
    
    private GameState gs;
    
    private JButton FullThrottle;

    private JButton stuckLeftThruster;

    private JButton stuckRightThruster;

    private JButton error1201;

    private JButton error1202;

    private JButton fuel;

    private JButton vxIns;

    private JButton vyIns;

    private JButton vwIns;

    private JButton attIns;

    private JButton altIns;
    ErrorButtonPanel (GameState gs){
        
        super();
        this.gs = gs;
//Set layout
        setLayout(new GridLayout(5,2));
        
        FullThrottle = new JButton();
        FullThrottle.setText("Full Throttle: " + gs.getErrorFreq(new FullThrottleError()));
        FullThrottle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new FullThrottleError());
                // TODO Auto-generated method stub
                //System.out.println(gs.getErrorFreq(new FullThrottleError()));
                FullThrottle.setText("Full Throttle: " + gs.getErrorFreq(new FullThrottleError()));

                
            }
//            
        });
        this.add(FullThrottle);
        stuckLeftThruster = new JButton();
        stuckLeftThruster.setText("Stuck Left Thruster: "+ gs.getErrorFreq(new LeftThrusterError()));
        stuckLeftThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new LeftThrusterError());
                stuckLeftThruster.setText("Stuck Left Thruster: "+ gs.getErrorFreq(new LeftThrusterError()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(stuckLeftThruster);
    stuckRightThruster = new JButton();
        stuckRightThruster.setText("Stuck Right Thruster: "+ gs.getErrorFreq(new RightThrusterError()));
        stuckRightThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new RightThrusterError());
                stuckRightThruster.setText("Stuck Right Thruster: "+ gs.getErrorFreq(new RightThrusterError()));

                // TODO Auto-generated method stub
                
            }
            
        });
        
        //TODO:reset errors, not game
        this.add(stuckRightThruster);
    error1201 = new JButton();
        error1201.setText("Error 1201: "+ gs.getErrorFreq(new ComputerOverloadedError1201()));
        error1201.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new ComputerOverloadedError1201());
                error1201.setText("Error 1201: "+ gs.getErrorFreq(new ComputerOverloadedError1201()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(error1201);
        
        
        error1202 = new JButton();
        error1202.setText("Error 1202: "+ gs.getErrorFreq(new ComputerOverloadedError1202()));
        error1202.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new ComputerOverloadedError1202());
                error1202.setText("Error 1202: "+ gs.getErrorFreq(new ComputerOverloadedError1202()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(error1202);
        
        fuel = new JButton();
        fuel.setText("Fuel Leak: "+ gs.getErrorFreq(new FuelLeakError()));
        fuel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new FuelLeakError());
                fuel.setText("Fuel Leak: "+ gs.getErrorFreq(new FuelLeakError()));

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
                vxIns.setText("Horizontal Velocity Failure: " + gs.getErrorFreq(new VxInstrumentError()));

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
                vyIns.setText("Vertical Velocity Failure: " + gs.getErrorFreq(new VyInstrumentError()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(vyIns);

        vwIns = new JButton();
        vwIns.setText("Rotational Velocity Failure: "+ gs.getErrorFreq(new VwInstrumentError()));
        vwIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VwInstrumentError());
                vwIns.setText("Rotational Velocity Failure: "+ gs.getErrorFreq(new VwInstrumentError()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(vwIns);
        
        attIns = new JButton();
        attIns.setText("Attitude Indicator Failure: "+ gs.getErrorFreq(new AttInstrumentError()));
        attIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new AttInstrumentError());
                attIns.setText("Attitude Indicator Failure: "+ gs.getErrorFreq(new AttInstrumentError()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(attIns);
        
        altIns = new JButton();
        altIns.setText("Altitude Indicator Failure: "+gs.getErrorFreq(new AltInstrumentError()));
        altIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new AltInstrumentError());
                altIns.setText("Altitude Indicator Failure: "+gs.getErrorFreq(new AltInstrumentError()));

                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(altIns);
        
        



        
        
        
    }    
    
    public void updateErrorButtonPanel() {
     
        FullThrottle.setText("Full Throttle: " + gs.getErrorFreq(new FullThrottleError()));
        stuckLeftThruster.setText("Stuck Left Thruster: "+ gs.getErrorFreq(new LeftThrusterError()));
        stuckRightThruster.setText("Stuck Right Thruster: "+ gs.getErrorFreq(new RightThrusterError()));
        error1202.setText("Error 1201: "+ gs.getErrorFreq(new ComputerOverloadedError1202()));
        fuel.setText("Fuel Leak: "+ gs.getErrorFreq(new FuelLeakError()));
        vxIns.setText("Horizontal Velocity Failure: " + gs.getErrorFreq(new VxInstrumentError()));
        vyIns.setText("Vertical Velocity Failure: " + gs.getErrorFreq(new VyInstrumentError()));
        vwIns.setText("Rotational Velocity Failure: "+ gs.getErrorFreq(new VwInstrumentError()));
        attIns.setText("Attitude Indicator Failure: "+ gs.getErrorFreq(new AttInstrumentError()));
        altIns.setText("Altitude Indicator Failure: "+ gs.getErrorFreq(new AltInstrumentError()));


    }
    
    public void paintComponent(Graphics gc) {
       // display the light bulb here
        //TODO:set the distances to constants that I can change?....
        //System.out.println(Game.gameState.getVw());
        

//        if (gs.getErrorFreq(new FullThrottleError()) != null) {
//            FullThrottle.setText("Full Throttle"+ gs.getErrorFreq(new FullThrottleError()));
//
//        }

    }
    
    @Override
    public Dimension getPreferredSize() {
        //TODO:FIx to make correct
        return new Dimension(500,400);
    }



}
