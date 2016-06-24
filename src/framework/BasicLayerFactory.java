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

import com.luciad.earth.model.TLcdEarthModelDescriptor;
import com.luciad.format.shp.TLcdSHPModelDescriptor;
import com.luciad.model.ILcdModel;
import com.luciad.view.lightspeed.layer.ALspLayerBuilder;
import com.luciad.view.lightspeed.layer.ALspSingleLayerFactory;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.raster.TLspRasterLayerBuilder;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;

/**
 * Creates layers for models containing Luciad Earth repositories (raster) or SHP data (vector).
 */
public class BasicLayerFactory extends ALspSingleLayerFactory {

  @Override
  public boolean canCreateLayers( ILcdModel aModel ) {
    return isEarthModel( aModel ) || isSHPModel( aModel );
  }

  @Override
  public ILspLayer createLayer( ILcdModel aModel ) {
    ALspLayerBuilder layerBuilder;

    if ( isEarthModel( aModel ) ) {
      layerBuilder = TLspRasterLayerBuilder.newBuilder();
    }
    else if ( isSHPModel( aModel ) ) {
      layerBuilder = TLspShapeLayerBuilder.newBuilder();
    }
    else {
      throw new IllegalArgumentException( "Unable to create layer " +
                                          "(model was neither a Luciad Earth model or a SHP model)" );
    }

    return layerBuilder.model( aModel ).build();
  }

  private static boolean isEarthModel( ILcdModel aModel ) {
    return aModel.getModelDescriptor() instanceof TLcdEarthModelDescriptor;
  }

  private static boolean isSHPModel( ILcdModel aModel ) {
    return aModel.getModelDescriptor() instanceof TLcdSHPModelDescriptor;
  }
}
