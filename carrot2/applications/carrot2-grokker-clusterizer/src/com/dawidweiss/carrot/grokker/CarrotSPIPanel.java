
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2006, Dawid Weiss, Stanisław Osiński.
 * Portions (C) Contributors listed in "carrot2.CONTRIBUTORS" file.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package com.dawidweiss.carrot.grokker;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.groxis.support.plugins.AbstractPanel;

/**
 * Displays a nice Carrot2 logo
 * 
 * @author Dawid Weiss
 * @version $Revision$
 */
public class CarrotSPIPanel extends AbstractPanel {

    /**
     * At the moment no parameter support is provided.
     */
	public boolean updateMemento(boolean fromUI) {
		return true;
	}

	public void performLayout() {
		setLayout(new BorderLayout());
        
        ImageIcon img = new ImageIcon(
                this.getClass().getClassLoader().getResource("resources/Carrot2-horiz.png"));
        JLabel banner = new JLabel(img);
        this.add(banner, BorderLayout.CENTER);
        
        super.setPreferredSize(banner.getPreferredSize());
	}
    
    public static void main(String [] args) {
        JFrame frm = new JFrame();
        CarrotSPIPanel pnl = new CarrotSPIPanel();
        pnl.performLayout();
        frm.getContentPane().add(pnl);
        frm.pack();
        frm.setVisible(true);
    }
}

