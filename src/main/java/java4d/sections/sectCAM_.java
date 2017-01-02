/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.CAM_data;

/**
 *
 * @author Pierantonio
 */
public class sectCAM_ extends sectMESH{

    public sectCAM_(sect parent)
    {
        super(parent);
        sdata=new CAM_data(this);
        sdatacasted=(CAM_data)sdata;
    }
}
