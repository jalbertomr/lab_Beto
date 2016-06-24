/*
 * Copyright (c) 1999-2015 Luciad NV All Rights Reserved.
 *
 * Luciad grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Luciad.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. LUCIAD AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL LUCIAD OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF LUCIAD HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 */
package framework;

import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.projection.TLcdEquidistantCylindrical;
import com.luciad.reference.TLcdGeocentricReference;
import com.luciad.reference.TLcdGridReference;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.util.TLspViewTransformationUtil;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import java.awt.event.ActionEvent;

class ToolBar extends JToolBar {

  private final ILspView fView;

  public ToolBar( ILspView aView ) {
    fView = aView;

    addReferenceButtons( aView );
  }

  private void addReferenceButtons( ILspView aView ) {
    JRadioButton radioButton2D = new JRadioButton( new SwitchTo2DAction() );
    add( radioButton2D );

    JRadioButton radioButton3D = new JRadioButton( new SwitchTo3DAction() );
    add( radioButton3D );

    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add( radioButton2D );
    buttonGroup.add( radioButton3D );

    JRadioButton selectedRadioButton = is2D( aView ) ? radioButton2D : radioButton3D;
    selectedRadioButton.setSelected( true );
  }

  private static boolean is2D( ILspView aView ) {
    return aView.getViewType() == ILspView.ViewType.VIEW_2D;
  }

  private void switchTo2D( ILspView aView ) {
    //TODO switch to 2d
    TLcdGridReference worldReference = new TLcdGridReference( new TLcdGeodeticDatum(), new TLcdEquidistantCylindrical());
    TLspViewTransformationUtil.setup2DView( aView, worldReference, true);
  }

  private void switchTo3D( ILspView aView ) {
    //TODO switch to 3d
    TLcdGeocentricReference worldReference = new TLcdGeocentricReference();
    TLspViewTransformationUtil.setup3DView( aView, worldReference, true);
  }

  private final class SwitchTo2DAction extends AbstractAction {
    private SwitchTo2DAction() {
      super( "2D" );
    }

    @Override public void actionPerformed( ActionEvent aEvent ) {
      switchTo2D( fView );
    }
  }

  private final class SwitchTo3DAction extends AbstractAction {
    private SwitchTo3DAction() {
      super( "3D" );
    }

    @Override public void actionPerformed( ActionEvent aEvent ) {
      switchTo3D( fView );
    }
  }
}
