package game;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

///TODO: Panel or component?
@SuppressWarnings("serial")
public class TelemetryPanel extends JPanel {

    private final JLabel fuelLabel = new JLabel("Fuel Remaining: ");
    private final JLabel VxLabel = new JLabel("Horizontal Velocity: ");
    private final JLabel VyLabel = new JLabel("Vertical Velocity: ");
    private final JLabel VwLabel = new JLabel("Rotational Velocity: ");
    private final JLabel altLabel = new JLabel("Altitude: ");
    private final JLabel attLabel = new JLabel("Attitude (Angle): ");
    private final JLabel errorLabel = new JLabel("Computer Error Code: ");
    private final JLabel contactLightLabel = new JLabel("CONTACT LIGHT: ");

    private final GameState gs;

    TelemetryPanel(GameState gs, boolean simsup) {
        this.gs = gs;

        if (simsup) {

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(fuelLabel);
            add(new JLabel(" "));
            add(VxLabel);
            add(new JLabel(""));

            add(VyLabel);
            add(new JLabel(""));

            add(VwLabel);
            add(new JLabel(" "));

            add(altLabel);
            add(new JLabel(""));

            add(attLabel);
            add(new JLabel(" "));

        } else {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(fuelLabel);
            add(new JLabel(" "));
            add(VxLabel);
            add(new JLabel(""));

            add(VyLabel);
            add(new JLabel(""));

            add(VwLabel);
            add(new JLabel(" "));

            add(altLabel);
            add(new JLabel(""));

            add(attLabel);
            add(new JLabel(" "));

            add(errorLabel);
            add(new JLabel(" "));

            add(contactLightLabel);

        }

    }

    // TODO:i could have a dfucntion that sets the labels and make the labels private. ugh
    // just see if what Im doing works, and then get to work on graphics/make playable

    // TODO:make correct...
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    // TODO: show message panel when computer error comes in

    public void updateTelemetryPanel() {

        fuelLabel.setText(
                "Fuel Remaining: " + String.format("%1$.2f", gs.getFuel()));

        VxLabel.setText(
                "Horizontal Velocity: " + String.format("%1$.2f", gs.getVx()));
        VyLabel.setText(
                "Vertical Velocity: " + String.format("%1$.2f", gs.getVy()));
        VwLabel.setText(
                "Angular Velocity: " + String.format("%1$.2f", gs.getVw()));
        altLabel.setText(
                "Altitude: " + String.format("%1$.2f", gs.getAltitude()));
        attLabel.setText(
                "Attitude (Angle): " + String.format("%1$.2f", gs.getAngle()));

        errorLabel.setText("Computer Error Code: " + gs.getComputerErrorCode());
        if (gs.getContactLight()) {
            contactLightLabel.setText("CONTACT LIGHT: " + "CONTACT");

        } else {
            contactLightLabel.setText("CONTACT LIGHT: ");

        }
    }

}
