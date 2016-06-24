package framework;

/**
 * Created by Decsef Sistemas on 22/06/2016.
 */

import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.projection.TLcdEquidistantCylindrical;
import com.luciad.reference.TLcdGeocentricReference;
import com.luciad.reference.TLcdGridReference;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.util.TLspViewTransformationUtil;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import java.awt.event.ActionEvent;

public class ScrollBar extends JScrollBar {

    private final ILspView fView;

    public ScrollBar( ILspView aView ) {
        fView = aView;
    }
}
