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

import com.luciad.util.TLcdNoBoundsException;
import com.luciad.util.TLcdOutOfBoundsException;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.util.TLspViewNavigationUtil;
import com.luciad.view.swing.TLcdLayerTree;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

/**
 * Panel displaying the layer control of a {@code ILspView}.
 */
public class LayerControlPanel extends JPanel {

  /**
   * Creates a new layer control for the given view.
   *
   * @param aView the view
   */
  public LayerControlPanel( ILspView aView ) {
    add( createLayerTree( aView ) );
  }

  private static TLcdLayerTree createLayerTree( ILspView aView ) {
    // A panel that displays all the layers in the view.
    TLcdLayerTree layerTree = new TLcdLayerTree( aView );
    layerTree.setRootVisible( false );
    layerTree.setShowsRootHandles( true );
    layerTree.setBackground( UIManager.getColor( "Panel.background" ) );

    // Animated fit on the layer upon double click.
    layerTree.addMouseListener( new AnimatedFitOnDoubleClick( aView ) );

    return layerTree;
  }

  /**
   * Mouse listener that performs an animated fit when double clicking on a layer.
   */
  private static final class AnimatedFitOnDoubleClick extends MouseAdapter {
    private final ILspView fView;

    /**
     * Creates a new {@code AnimatedFitOnDoubleClick} mouse listener.
     *
     * @param aView the view of the layers to fit on
     */
    public AnimatedFitOnDoubleClick( ILspView aView ) {
      fView = aView;
    }

    @Override
    public void mouseClicked( MouseEvent aEvent ) {
      // Only fit on double click action.
      if ( aEvent.getClickCount() == 2 ) {
        // Cast is safe if we assign this listener only to the layer tree.
        TLcdLayerTree layerTree = ( TLcdLayerTree ) aEvent.getSource();
        TreePath selectionPath = layerTree.getSelectionPath();

        // Make sure we selected a layer.
        if ( selectionPath != null ) {
          ILspLayer layer = ( ILspLayer ) selectionPath.getLastPathComponent();

          try {
            new TLspViewNavigationUtil( fView ).animatedFit( Collections.singletonList( layer ) );
          }
          catch ( TLcdNoBoundsException | TLcdOutOfBoundsException e ) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
