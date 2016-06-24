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

import com.luciad.gui.swing.TLcdOverlayLayout;
import com.luciad.realtime.lightspeed.radarvideo.TLspRadarVideoLayerBuilder;
import com.luciad.view.lightspeed.ILspAWTView;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.TLspAWTView;
import com.luciad.view.lightspeed.TLspViewBuilder;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.painter.grid.TLspLonLatGridLayerBuilder;
import com.luciad.view.lightspeed.painter.grid.TLspXYGridLayerBuilder;
import com.luciad.view.lightspeed.swing.TLspScaleIndicator;

import javax.swing.*;
import java.awt.*;

public final class Exercise {

  public static void main( String[] aArguments ) {
    SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        new Exercise();
      }
    } );
  }

  private Exercise() {
    ILspAWTView view = createView();
    addLayers( view );

    JFrame frame = new JFrame();
    frame.setTitle("Exercise 3.1");
    frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
    frame.setLayout( new BorderLayout() );
    frame.add( view.getHostComponent(), BorderLayout.CENTER );

    Container aOverlayPanel = view.getOverlayComponent();
    TLcdOverlayLayout layout = (TLcdOverlayLayout) aOverlayPanel.getLayout();

    JLabel DecSefLogo = new JLabel("DecSef S.A. de C.V.");
    aOverlayPanel.add(DecSefLogo);
    layout.putConstraint(DecSefLogo, TLcdOverlayLayout.Location.SOUTH_WEST, TLcdOverlayLayout.ResolveClash.VERTICAL);

    aOverlayPanel.add( new LayerControlPanel( view ));
    aOverlayPanel.add( new ScrollBar( view ));

    JLabel scaleIndicator = new TLspScaleIndicator( view ).getLabel();
    aOverlayPanel.add( scaleIndicator);
    layout.putConstraint( scaleIndicator , TLcdOverlayLayout.Location.SOUTH_EAST, TLcdOverlayLayout.ResolveClash.VERTICAL);

    frame.add( new LayerControlPanel( view ), BorderLayout.EAST );
    frame.add( new ToolBar( view ), BorderLayout.NORTH );
    //frame.add( new ScrollBar( view ), BorderLayout.EAST);
    //frame.add( new TLspScaleIndicator( view ).getLabel(), BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible( true );
  }

  private ILspAWTView createView() {
    //TODO create the view.
    TLspAWTView view = TLspViewBuilder.newBuilder()
            .size(800, 600)
            .background(Color.lightGray)
            .buildAWTView();
    return view;
  }

  private void addLayers( ILspView aView ) {
    addGridLayer( aView );
    addXYGridLayer( aView );
  }

  private void addGridLayer( ILspView aView ) {
    aView.addLayer( TLspLonLatGridLayerBuilder.newBuilder().build() );
  }

  private void addXYGridLayer( ILspView aView ) {
    aView.addLayer(TLspXYGridLayerBuilder.newBuilder().build() );
  }
}
